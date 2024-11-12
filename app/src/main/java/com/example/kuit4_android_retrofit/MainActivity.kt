package com.example.kuit4_android_retrofit

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.kuit4_android_retrofit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navItem: NavigationItem
    private var backPressedTime: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initOnBackPressedDispatcher()
        initBottomNavigation()
    }

    private fun initOnBackPressedDispatcher() {
        val onBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    when (binding.mainBottomNav.selectedItemId) {
                        R.id.home_fragment -> {
                            if (System.currentTimeMillis() - backPressedTime <= 2000) {
                                finishAffinity()
                            } else {
                                backPressedTime = System.currentTimeMillis()
                            }
                        }

                        else ->
                            binding.mainBottomNav.selectedItemId = R.id.home_fragment
                    }
                }
            }

        onBackPressedDispatcher.addCallback(onBackPressedCallback)
    }

    private fun initBottomNavigation() {
        binding.mainBottomNav.selectedItemId = R.id.home_fragment
        navItem = NavigationItem.HOME

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_fragment -> {
                    navItem = NavigationItem.HOME

                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.map_fragment -> {
                    navItem = NavigationItem.MAP

                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.main_frm, MapFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.favorite_fragment -> {
                    navItem = NavigationItem.FAVORITE

                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.main_frm, FavoriteFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.history_fragment -> {
                    navItem = NavigationItem.HISTORY

                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.main_frm, HistoryFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.myeats_fragment -> {
                    navItem = NavigationItem.MYEATS

                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.main_frm, MyEatsFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val view = currentFocus
            if (view is EditText) {
                val outRect = Rect()
                view.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    hideKeyboard(view)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    private fun hideKeyboard(view: View) {
        view.clearFocus()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    enum class NavigationItem {
        HOME,
        MAP,
        FAVORITE,
        HISTORY,
        MYEATS,
        ;

        fun setSelectedItemId(): Int =
            when (this) {
                HOME -> R.id.home_fragment
                MAP -> R.id.map_fragment
                FAVORITE -> R.id.favorite_fragment
                HISTORY -> R.id.history_fragment
                MYEATS -> R.id.myeats_fragment
            }
    }
}
