package com.example.demologinmvvm.custom.loading

import android.app.AlertDialog
import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.demologinmvvm.R

/**
 * Custom loading view with process bar and can add function to change loading message
 */
class LoadingView {
    companion object {
        private var dialog: AlertDialog? = null

        fun show(context: Context?) {
            if (dialog != null && dialog!!.isShowing)
                return

            if (context == null) {
                return
            }
            val ll = LinearLayout(context)
            ll.orientation = LinearLayout.HORIZONTAL
            ll.setPadding(25, 25, 25, 25)
            ll.gravity = Gravity.CENTER_VERTICAL
            var llParam = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            llParam.setMargins(30, 0, 30, 0)
            llParam.gravity = Gravity.START
            ll.layoutParams = llParam

            val progressBar = ProgressBar(context)
            progressBar.isIndeterminate = true
            progressBar.setPadding(0, 0, 5, 0)
            progressBar.layoutParams = llParam

            llParam = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            val tvText = TextView(context)
            tvText.text = context.getText(R.string.txt_loading)
            tvText.setTextColor(ContextCompat.getColor(context, R.color.gray_500))
            tvText.textSize = 15f
            tvText.layoutParams = llParam

            ll.addView(progressBar)
            ll.addView(tvText)

            val builder = AlertDialog.Builder(context)
            builder.setCancelable(true)
            builder.setView(ll)

            dialog = builder.create()
            dialog?.setCanceledOnTouchOutside(false)
            dialog?.show()
            val window = dialog?.window
            if (window != null) {
                val layoutParams = WindowManager.LayoutParams()
                layoutParams.copyFrom(dialog?.window?.attributes)
                layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
                layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
                dialog?.window?.attributes = layoutParams
            }
        }

        fun dismiss() {
            dialog?.dismiss()
            dialog = null
        }
    }
}