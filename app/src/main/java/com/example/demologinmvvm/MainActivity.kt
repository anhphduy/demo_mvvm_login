package com.example.demologinmvvm

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.demologinmvvm.data.model.User
import com.example.demologinmvvm.ui.authentication.AuthenActivity
import com.example.demologinmvvm.utils.LOGIN_USER_KEY
import com.example.demologinmvvm.utils.PreferenceHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentUser = PreferenceHelper.getObject(LOGIN_USER_KEY, User::class.java)
        if (currentUser != null)
            txtUserName.text = getString(R.string.hello_user, currentUser.fullName)

        btnLogout.setOnClickListener {
            val dialogClickListener = DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        dialog.dismiss()
                        val intent = Intent(this, AuthenActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        PreferenceHelper.clear()
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                        dialog.dismiss()
                    }
                }
            }

            val builder = AlertDialog.Builder(this)
            builder.setMessage(getString(R.string.log_out_message)).setPositiveButton(getString(R.string.yes), dialogClickListener)
                .setNegativeButton(getString(R.string.no), dialogClickListener).show()
        }
    }
}