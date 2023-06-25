package com.example.jpgtopngconverter.mvp.presenter

import com.example.jpgtopngconverter.mvp.view.ConverterView
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter

class ConverterPresenter(private val router: Router) : MvpPresenter<ConverterView>() {

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}