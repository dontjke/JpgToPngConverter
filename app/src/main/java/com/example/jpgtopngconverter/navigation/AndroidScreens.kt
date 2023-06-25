package com.example.jpgtopngconverter.navigation

import com.example.jpgtopngconverter.ui.ConverterFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

class AndroidScreens : IScreens {
    override fun converter() = FragmentScreen { ConverterFragment.newInstance() }
}
