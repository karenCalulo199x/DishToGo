package com.appscals.dish2go.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import com.appscals.dish2go.R
import com.appscals.dish2go.databinding.ActivityMainBinding
import com.appscals.dish2go.view.fragment.FavoritesFragment
import com.appscals.dish2go.view.fragment.HomeFragment
import com.appscals.dish2go.view.fragment.MenuFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initViews()
    }

    private fun initViews() {

        val firstFragment = HomeFragment()
        val secondFragment = MenuFragment()
        val thirdFragment = FavoritesFragment()

        replaceFragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_home -> replaceFragment(firstFragment)
                R.id.menu_food -> replaceFragment(secondFragment)
                R.id.menu_fave -> replaceFragment(thirdFragment)
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_layout, fragment)
            commit()
        }
    }
}