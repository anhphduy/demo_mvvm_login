package com.example.demologinmvvm.utils

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