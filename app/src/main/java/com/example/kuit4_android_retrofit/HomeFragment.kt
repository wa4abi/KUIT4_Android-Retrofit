package com.example.kuit4_android_retrofit

import RVPopularMenuAdapter
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kuit4_android_retrofit.data.MenuCategoryDB
import com.example.kuit4_android_retrofit.data.MenuCategoryData
import com.example.kuit4_android_retrofit.databinding.FragmentHomeBinding
import com.example.kuit4_android_retrofit.databinding.ItemMenuCategoryBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var db: MenuCategoryDB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        db = MenuCategoryDB.getInstance(requireContext())

        initDatabaseIfNeeded()
        loadCategoryItems()
        loadPopularMenuItems()

        return binding.root
    }

    private fun initDatabaseIfNeeded() {
        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences("menu_category", Context.MODE_PRIVATE)

        if (!sharedPreferences.getBoolean("isDataInitialized", false)) {
            lifecycleScope.launch(Dispatchers.IO) {
                db.menuDao().apply {
                    insert(MenuCategoryData(getString(R.string.pork_cutlet), R.drawable.img_pork_cutlet))
                    insert(MenuCategoryData(getString(R.string.japanese_food), R.drawable.img_japanese_food))
                    insert(MenuCategoryData(getString(R.string.korean_food), R.drawable.img_korean_food))
                    insert(MenuCategoryData(getString(R.string.chicken), R.drawable.img_chicken))
                    insert(MenuCategoryData(getString(R.string.snack_food), R.drawable.img_snack_food))
                    insert(MenuCategoryData(getString(R.string.bossam), R.drawable.img_bossam))
                    insert(MenuCategoryData(getString(R.string.soup), R.drawable.img_soup))
                    insert(MenuCategoryData(getString(R.string.barbeque), R.drawable.img_barbeque))
                    insert(MenuCategoryData(getString(R.string.pizza), R.drawable.img_pizza))
                }

                with(sharedPreferences.edit()) {
                    putBoolean("isDataInitialized", true)
                    apply()
                }
            }
        }
    }

    private fun loadCategoryItems() {
        lifecycleScope.launch {
            val categoryList =
                withContext(Dispatchers.IO) {
                    db.menuDao().getAll()
                }
            addCategoryItems(categoryList)
        }
    }

    private fun loadPopularMenuItems() {
        lifecycleScope.launch {
            val popularMenuItems =
                withContext(Dispatchers.IO) {
                    db.menuDao().getAll()
                }

            binding.rvMainPopularMenus.layoutManager = LinearLayoutManager(requireContext())
            binding.rvMainPopularMenus.adapter = RVPopularMenuAdapter(popularMenuItems)
        }
    }

    private fun addCategoryItems(categoryList: List<MenuCategoryData>) {
        val inflater = LayoutInflater.from(requireContext())
        categoryList.forEach { category ->
            val categoryBinding = ItemMenuCategoryBinding.inflate(inflater, binding.hsvMainMenuCategory, false)
            categoryBinding.ivMenuCategoryImg.setImageResource(category.menuCategoryImg)
            categoryBinding.tvMenuCategoryName.text = category.menuCategoryName
            binding.llMainMenuCategory.addView(categoryBinding.root)
        }
    }
}
