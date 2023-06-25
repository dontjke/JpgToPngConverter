package com.example.jpgtopngconverter.mvp.presenter


import com.example.jpgtopngconverter.mvp.view.MainView
import com.example.jpgtopngconverter.navigation.IScreens
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter


class MainPresenter(private val router: Router, private val screens: IScreens) :
    MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(screens.converter())
    }

    fun backClicked() {
        router.exit()
    }
}


