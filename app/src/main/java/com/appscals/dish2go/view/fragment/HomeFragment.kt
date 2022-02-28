package com.appscals.dish2go.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.appscals.dish2go.R
import com.appscals.dish2go.databinding.FragmentHomeBinding
import com.appscals.dish2go.view.activity.AddUpdateDishActivity

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(
            inflater,
            container,
            false
        )
        initViews()
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.appbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_addDish -> {
            activity?.let {
                val intent = Intent(it, AddUpdateDishActivity::class.java)
                it.startActivity(intent)
            }
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun initViews() {
    }
}