package com.example.jpgtopngconverter.ui

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import coil.load
import com.example.jpgtopngconverter.App
import com.example.jpgtopngconverter.R
import com.example.jpgtopngconverter.databinding.FragmentConverterBinding
import com.example.jpgtopngconverter.mvp.model.ConvertAndSaveImpl
import com.example.jpgtopngconverter.mvp.presenter.ConverterPresenter
import com.example.jpgtopngconverter.mvp.view.ConverterView
import com.example.jpgtopngconverter.navigation.BackButtonListener
import com.example.jpgtopngconverter.utils.PERMISSION_REGISTRY_KEY
import com.example.jpgtopngconverter.utils.RESULT_REGISTRY_KEY
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter


class ConverterFragment : MvpAppCompatFragment(), ConverterView, BackButtonListener {

    private var _binding: FragmentConverterBinding? = null
    private val binding get() = _binding!!

    private val presenter: ConverterPresenter by moxyPresenter {
        ConverterPresenter(
            App.instance.router,
            ConvertAndSaveImpl(App.instance.applicationContext),
            AndroidSchedulers.mainThread()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConverterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.pickImageButton.setOnClickListener {
            requestPermission()
        }

        binding.convertButton.setOnClickListener {
            presenter.convertAndSave()
        }

        binding.cancelButton.setOnClickListener {
            presenter.cancelConverting()
        }
    }

    private fun pickImage(registry: ActivityResultRegistry) {

        val getContent: ActivityResultLauncher<String> =
            registry.register(
                RESULT_REGISTRY_KEY,
                ActivityResultContracts.GetContent()
            ) { imageUri ->
                binding.image.load(imageUri)
                presenter.uri = imageUri.toString()
            }

        Log.d("@@@", Thread.currentThread().name)
        getContent.launch("image/*")
    }

    private fun requestPermission() {
        val registry = requireActivity().activityResultRegistry

        val requestPermissionLauncher: ActivityResultLauncher<String> =
            registry.register(
                PERMISSION_REGISTRY_KEY,
                ActivityResultContracts.RequestPermission()
            ) { granted ->
                when {
                    granted -> {
                        pickImage(registry)
                    }

                    shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                        showDialogForRequestPermission()
                    }

                    else -> {
                        showDialogForClosedPermission()
                    }
                }
            }

        requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    override fun showDialogForRequestPermission() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.title_permission))
            .setMessage(
                getString(R.string.ask_for_permission)
            )
            .setPositiveButton(android.R.string.ok) { _, _ ->
                requestPermission()
            }
            .setNegativeButton(android.R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun showDialogForClosedPermission() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.title_for_closed_permission))
            .setMessage(getString(R.string.closed_permission))
            .setPositiveButton(android.R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    override fun makeToastSuccess() {
        Toast.makeText(context, getString(R.string.png_save), Toast.LENGTH_SHORT).show()
    }

    override fun makeToastError(error: Throwable) {
        Toast.makeText(context, getString(R.string.error) + error, Toast.LENGTH_SHORT).show()
    }

    override fun makeToastGallery() {
        Toast.makeText(context, getString(R.string.choose_picture), Toast.LENGTH_SHORT).show()
    }

    override fun makeToastCancel() {
        Toast.makeText(context, getString(R.string.cancel), Toast.LENGTH_SHORT).show()
    }

    override fun showCancelBtn() {
        binding.cancelButton.visibility = View.VISIBLE
    }

    override fun hideCancelBtn() {
        binding.cancelButton.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = ConverterFragment()
    }

    override fun backPressed() = presenter.backPressed()


}