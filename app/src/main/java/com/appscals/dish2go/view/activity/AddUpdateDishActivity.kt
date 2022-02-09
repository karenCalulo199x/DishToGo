package com.appscals.dish2go.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.appscals.dish2go.R
import com.appscals.dish2go.databinding.ActivityAddUpdateDishBinding

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
            toolbar.appBarBackIv.setOnClickListener{
                onBackPressed()
            }
        }
    }

    private fun initViews() {

    }

}