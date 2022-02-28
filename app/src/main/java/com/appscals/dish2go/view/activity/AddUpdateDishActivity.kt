package com.appscals.dish2go.view.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.appscals.dish2go.R
import com.appscals.dish2go.databinding.ActivityAddUpdateDishBinding
import com.appscals.dish2go.view.utils.*
import com.appscals.dish2go.view.utils.Constants.mCAMERA
import com.appscals.dish2go.view.utils.Constants.readExtStorage
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class AddUpdateDishActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddUpdateDishBinding
    private var mImagePath: String = ""

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
                    mImagePath = saveImageToInternalStorage(thumbnail)
                    println(mImagePath)

                    this.setImage(thumbnail, binding.layoutImg)

                    binding.uploadImg.setImageDrawable(
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.ic_edit
                        )
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

                    selectedImage?.let { selectedImg ->
                        setImageGlide(selectedImg)
                    }

                    binding.uploadImg.setImageDrawable(
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.ic_edit
                        )
                    )

                }
            }
        }

    private fun setImageGlide(selectedImg: Uri) {
        Glide.with(this)
            .load(selectedImg)
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    displayToast("ERROR LOADING IMAGE")
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    resource?.let {
                        val bitmap: Bitmap = resource.toBitmap()
                        mImagePath = saveImageToInternalStorage(bitmap)
                        println(mImagePath)
                    }
                    return false
                }
            })
            .into(binding.layoutImg)
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
        val camPermissionList = listOf(mCAMERA, readExtStorage)
        val picPermissionList = listOf(readExtStorage)

        binding.uploadImg.setOnClickListener {
            this.showUploadDialog(
                callback1 = {
                    this.getPermissions(camPermissionList,
                        onSuccess = {
                            println("CAMERA SUCCESS")
                            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            launchCameraAction.launch(intent)
                        }, onDenied = {
                            this.callAlertDialog()
                        }
                    )
                }, callback2 = {
                    this.getPermissions(
                        picPermissionList,
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

        this.setDropDownAdapter(
            resources.getStringArray(R.array.category_list),
            binding.categoryAutocompleteTv
        )
        this.setDropDownAdapter(
            resources.getStringArray(R.array.type_list),
            binding.typeAutoCompleteTv
        )
    }

}