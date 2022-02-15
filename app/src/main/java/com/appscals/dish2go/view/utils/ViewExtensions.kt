package com.appscals.dish2go.view.utils

import android.app.Activity
import android.app.Dialog
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.appscals.dish2go.R
import com.appscals.dish2go.databinding.LayoutCustomDialogBinding
import com.appscals.dish2go.databinding.LayoutUploadDialogBinding
import timber.log.Timber

fun Activity.displayToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Activity.displayDialog(header: String, message: String, callback: () -> (Unit)) {
    val dialog = Dialog(this)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.layout_custom_dialog)

    val body = dialog.findViewById(R.id.body) as TextView
    val title = dialog.findViewById(R.id.title) as TextView
    title.text = header
    body.text = message

    val yesBtn = dialog.findViewById(R.id.yesBtn) as TextView
    val noBtn = dialog.findViewById(R.id.noBtn) as TextView
    yesBtn.setOnClickListener {
        callback.invoke() // Call that function
        dialog.dismiss()
    }
    noBtn.setOnClickListener { dialog.dismiss() }
    dialog.show()
}

fun Activity.showUploadDialog(callback1: () -> (Unit), callback2: () -> (Unit)) {
    val dialog = Dialog(this)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(false)

    val binding = LayoutUploadDialogBinding.inflate(layoutInflater)
    dialog.setContentView(binding.root)

    binding.apply {
        cameraBtn.setOnClickListener {
            callback1.invoke() // Call that function
            dialog.dismiss()
        }

        pictureBtn.setOnClickListener {
            callback2.invoke() // Call that function
            dialog.dismiss()
        }

        closeBtn.setOnClickListener {
            dialog.dismiss()
        }
    }
    dialog.show()
}