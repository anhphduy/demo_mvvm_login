package com.example.demologinmvvm.utils

import android.content.Context
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun FragmentActivity.addFragment(fragment: Fragment, frameId: Int){
    val transaction = supportFragmentManager.beginTransaction()
    transaction.add( frameId, fragment)
    transaction.addToBackStack(fragment.javaClass.toString())
    transaction.commit()
}

fun FragmentActivity.replaceFragment(fragment: Fragment, frameId: Int, addToBackStack: Boolean) {
    val fragmentTransaction = supportFragmentManager.beginTransaction()
    fragmentTransaction.replace(frameId,fragment)
    if(addToBackStack) fragmentTransaction.addToBackStack(fragment.javaClass.simpleName)
    fragmentTransaction.commit()

}

fun FragmentActivity.setTransparentStatusBar() {
    window.setFlags(
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
}

fun FragmentActivity.hideKeyboard() {
    val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val view = currentFocus
    if (view != null) {
        inputManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}

fun Fragment.hideKeyboard() {
    activity?.let {
        val inputManager = it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = it.currentFocus
        if (view != null) {
            inputManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }
}