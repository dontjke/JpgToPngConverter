package com.example.jpgtopngconverter.utils

import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

fun Disposable.disposeBy(bag: CompositeDisposable) {
    bag.add(this)
}

const val RESULT_REGISTRY_KEY = "pick_image"
const val PERMISSION_REGISTRY_KEY = "permission"