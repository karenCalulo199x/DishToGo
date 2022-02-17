package com.appscals.dish2go.view.utils

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import com.appscals.dish2go.R
import com.appscals.dish2go.databinding.LayoutUploadDialogBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener


fun Activity.displayToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Activity.callAlertDialog() {
    AlertDialog.Builder(this)
        .setMessage(R.string.denied_permission)
        .setPositiveButton("Go to Settings") { _, _ ->
            try {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
            }
        }
        .setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }.show()
}

fun Activity.displayCustomDialog(header: String, message: String, callback: () -> (Unit)) {
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

fun Activity.setPermission(permission: String, onSuccess: () -> Unit, onDenied: () -> Unit) {
    Dexter.withContext(this)
        .withPermission(permission)
        .withListener(object : PermissionListener {
            override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                p0?.let {
                    onSuccess.invoke()
                }
            }

            override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                p0?.let {
                    onDenied.invoke()
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                p0: PermissionRequest?,
                p1: PermissionToken?
            ) {
                p1?.continuePermissionRequest()
            }
        }).onSameThread().check()
}