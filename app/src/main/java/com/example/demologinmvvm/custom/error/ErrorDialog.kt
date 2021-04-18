package com.example.demologinmvvm.custom.error

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import com.example.demologinmvvm.R
import kotlinx.android.synthetic.main.dialog_error.*

/**
 * Custom dialog error that show message error
 */
class ErrorDialog(
    context: Context,
    private val title: String,
    private val msg: String? = null,
    private val textBtn: String? = null
) : AlertDialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_error)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        tvTitle.text = title
        if (textBtn != null) {
            btnYes.text = textBtn
        }
        if (msg != null) {
            tvMessage.visibility = View.VISIBLE
            tvMessage.text = msg
        } else
            tvMessage.visibility = View.GONE
        setCancelable(false)
        btnYes.setOnClickListener {
            dismiss()
        }
    }
}