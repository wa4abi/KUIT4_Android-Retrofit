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
import com.example.kuit4_android_retrofit.data.MenuDB
import com.example.kuit4_android_retrofit.data.MenuData
import com.example.kuit4_android_retrofit.databinding.FragmentHomeBinding
import com.example.kuit4_android_retrofit.databinding.ItemCategoryMenuBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var db: MenuDB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        db = MenuDB.getInstance(requireContext())

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
                    insert(MenuData(menuName = getString(R.string.pork_cutlet), menuImg = R.drawable.img_pork_cutlet))
                    insert(MenuData(menuName = getString(R.string.japanese_food), menuImg = R.drawable.img_japanese_food))
                    insert(MenuData(menuName = getString(R.string.korean_food), menuImg = R.drawable.img_korean_food))
                    insert(MenuData(menuName = getString(R.string.chicken), menuImg = R.drawable.img_chicken))
                    insert(MenuData(menuName = getString(R.string.snack_food), menuImg = R.drawable.img_snack_food))
                    insert(MenuData(menuName = getString(R.string.bossam), menuImg = R.drawable.img_bossam))
                    insert(MenuData(menuName = getString(R.string.soup), menuImg = R.drawable.img_soup))
                    insert(MenuData(menuName = getString(R.string.barbeque), menuImg = R.drawable.img_barbeque))
                    insert(MenuData(menuName = getString(R.string.pizza), menuImg = R.drawable.img_pizza))
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

    private fun addCategoryItems(categoryList: List<MenuData>) {
        val inflater = LayoutInflater.from(requireContext())
        categoryList.forEach { category ->
            val categoryBinding = ItemCategoryMenuBinding.inflate(inflater, binding.hsvMainMenuCategory, false)
            categoryBinding.ivMainCategory.setImageResource(category.menuImg)
            categoryBinding.tvMainCategoryName.text = category.menuName
            binding.llMainMenuCategory.addView(categoryBinding.root)
        }
    }
}
