package com.appscals.dish2go.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.appscals.dish2go.databinding.ActivityAddUpdateDishBinding

class AddUpdateDishActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddUpdateDishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddUpdateDishBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initViews()
    }

    private fun initViews() {

    }

}