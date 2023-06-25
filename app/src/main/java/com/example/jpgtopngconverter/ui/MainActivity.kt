package com.example.jpgtopngconverter.ui

import android.os.Bundle
import com.example.jpgtopngconverter.App
import com.example.jpgtopngconverter.R
import com.example.jpgtopngconverter.databinding.ActivityMainBinding
import com.example.jpgtopngconverter.mvp.presenter.MainPresenter
import com.example.jpgtopngconverter.mvp.view.MainView
import com.example.jpgtopngconverter.navigation.BackButtonListener
import com.github.terrakok.cicerone.androidx.AppNavigator
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

class MainActivity : MvpAppCompatActivity(), MainView {

    private val navigator = AppNavigator(this, R.id.container)
    private var binding: ActivityMainBinding? = null

    private val presenter by moxyPresenter {
        MainPresenter(
            App.instance.router,
            App.instance.androidScreens
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        App.instance.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        App.instance.navigatorHolder.removeNavigator()
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if (it is BackButtonListener && it.backPressed()) {
                return
            }
        }
        presenter.backClicked()
    }
}