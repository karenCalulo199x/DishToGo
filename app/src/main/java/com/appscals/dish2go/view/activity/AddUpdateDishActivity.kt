package com.appscals.dish2go.view.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.appscals.dish2go.R
import com.appscals.dish2go.databinding.ActivityAddUpdateDishBinding
import com.appscals.dish2go.view.utils.Constants.mCAMERA
import com.appscals.dish2go.view.utils.Constants.mPICTURE
import com.appscals.dish2go.view.utils.callAlertDialog
import com.appscals.dish2go.view.utils.setPermission
import com.appscals.dish2go.view.utils.showUploadDialog

class AddUpdateDishActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddUpdateDishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddUpdateDishBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setUpActionBar()
        initViews()
    }

    private val launchCameraAction =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                data?.extras?.let {
                    val thumbnail: Bitmap = data.extras!!.get("data") as Bitmap
                    binding.layoutImg.setImageBitmap(thumbnail)
                    binding.uploadImg.setImageDrawable(
                        ContextCompat.getDrawable(this, R.drawable.ic_edit)
                    )
                }
            }
        }

    private val launchPictureAction =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                data?.let {
                    val selectedImage = data.data
                    binding.layoutImg.setImageURI(selectedImage)
                    binding.uploadImg.setImageDrawable(
                        ContextCompat.getDrawable(this, R.drawable.ic_edit)
                    )

                }
            }
        }


    private fun setUpActionBar() {
        binding.apply {
            toolbar.appBarTitleTv.text = getString(R.string.add_dish_title)
            toolbar.appBarBackIv.setOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun initViews() {
        binding.uploadImg.setOnClickListener {
            this.showUploadDialog(
                callback1 = {
                    this.setPermission(mCAMERA,
                        onSuccess = {
                            println("CAMERA SUCCESS")
                            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            launchCameraAction.launch(intent)
                        }, onDenied = {
                            this.callAlertDialog()
                        }
                    )
                }, callback2 = {
                    this.setPermission(mPICTURE,
                        onSuccess = {
                            println("PICTURE SUCCESS")
                            val intent = Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                            )
                            launchPictureAction.launch(intent)
                        }, onDenied = {
                            this.callAlertDialog()
                        }
                    )
                }
            )
        }
    }

}