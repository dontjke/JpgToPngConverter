package com.example.jpgtopngconverter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jpgtopngconverter.App
import com.example.jpgtopngconverter.databinding.FragmentConverterBinding
import com.example.jpgtopngconverter.mvp.presenter.ConverterPresenter
import com.example.jpgtopngconverter.mvp.view.ConverterView
import com.example.jpgtopngconverter.navigation.BackButtonListener
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter


class ConverterFragment : MvpAppCompatFragment(), ConverterView, BackButtonListener {

    private var _binding: FragmentConverterBinding? = null
    private val binding get() = _binding!!
    private val presenter by moxyPresenter {
        ConverterPresenter(App.instance.router)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConverterBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance() = ConverterFragment()
    }

    override fun backPressed() = presenter.backPressed()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}