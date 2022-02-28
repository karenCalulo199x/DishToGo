package com.appscals.dish2go.view.utils.extensions

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.Settings
import android.view.Window
import android.widget.*
import com.appscals.dish2go.R
import com.appscals.dish2go.databinding.LayoutUploadDialogBinding
import com.appscals.dish2go.view.utils.Constants.IMAGE_DIRECTORY
import com.bumptech.glide.Glide
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*


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

fun Activity.getPermissions(
    permissions: List<String?>?,
    onSuccess: () -> Unit,
    onDenied: () -> Unit
) {
    Dexter.withContext(this)
        .withPermissions(permissions)
        .withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                report.let {
                    if (report.areAllPermissionsGranted()) {
                        println("Permissions Granted")
                        onSuccess.invoke()
                    } else {
                        println("Please Grant Permissions to use the app")
                        onDenied.invoke()
                    }
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                permissions: List<PermissionRequest?>?,
                token: PermissionToken?
            ) {
                token?.continuePermissionRequest()
                println(permissions)
                println(token)
            }
        }).withErrorListener {
            Toast.makeText(this, it.name, Toast.LENGTH_SHORT).show()
        }
        .onSameThread().check()
}

fun Activity.setImage(image: Any, imgBinding: ImageView) {
    Glide
        .with(this)
        .load(image)
        .fitCenter()
        .into(imgBinding)
}

fun Activity.saveImageToInternalStorage(bitmap: Bitmap): String {
    val wrapper = ContextWrapper(applicationContext)

    var file = wrapper.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE)
    file = File(file, "${UUID.randomUUID()}.jpg")

    try {
        val stream: OutputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        stream.flush()
        stream.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }

    return file.absolutePath
}

fun Activity.setDropDownAdapter(listItem: Array<String>, binder: AutoCompleteTextView) {
    val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listItem)
    binder.setAdapter(adapter)
}