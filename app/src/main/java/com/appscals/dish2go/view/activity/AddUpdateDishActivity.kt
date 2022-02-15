package com.appscals.dish2go.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.appscals.dish2go.R
import com.appscals.dish2go.databinding.ActivityAddUpdateDishBinding
import com.appscals.dish2go.view.utils.displayToast
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
            this.showUploadDialog(callback1 = {
                this.displayToast("111111111111111111111111111")
            }, callback2 = {
                this.displayToast("22222222222222222222222222222")
            }
            )
        }
    }

}