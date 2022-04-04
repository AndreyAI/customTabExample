package com.example.customtab

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.customtab.banner.BannerAdapter
import com.example.customtab.data.Banner
import com.example.customtab.databinding.ActivityMainBinding
import com.example.customtab.food.FoodAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::bind)
    private var bannerAdapter: BannerAdapter? = null
    private val viewModel: ViewModelMain by viewModels()
    private var foodAdapter: FoodAdapter? = null

    private val banners = listOf(
        Banner(
            0,
            R.drawable.f1
        ),
        Banner(
            1,
            R.drawable.f2
        ),
        Banner(
            2,
            R.drawable.f3
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initBanners()
        initTabs()
        initFood()

    }

    private fun initTabs() {

        val listCategories = listOf(
            R.string.pizza,
            R.string.combo,
            R.string.deserts,
            R.string.snacks,
            R.string.sprite,
            R.string.souses
        )

        var view: View?
        for (i in 0 until binding.tabLayout.tabCount) {
            view = binding.tabLayout.getTabAt(i)?.customView as TextView
            view.text = getText(listCategories[i])
            binding.tabLayout.getTabAt(i)?.customView = view
        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Handle tab reselect
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Handle tab unselect
            }
        })
    }

    private fun initBanners() {

        bannerAdapter = BannerAdapter {

        }
        bannerAdapter!!.items = banners
        with(binding.listBanner) {
            setHasFixedSize(true)
            adapter = bannerAdapter
        }
    }

    private fun initFood() {
        foodAdapter = FoodAdapter {
        }

        foodAdapter?.addLoadStateListener { state ->
            with(binding) {
                if (state.refresh == LoadState.Loading) {
                    listFood.visibility = View.INVISIBLE
                    progress.visibility = View.VISIBLE
                } else {
                    listFood.visibility = View.VISIBLE
                    progress.visibility = View.INVISIBLE
                }
            }
        }
        binding.listFood.adapter = foodAdapter

        lifecycleScope.launch {
            viewModel.pagingDataFlow.collectLatest { pagingData ->
                foodAdapter?.submitData(pagingData)
            }
        }
    }

    override fun onDestroy() {
        bannerAdapter = null
        foodAdapter = null
        super.onDestroy()
    }

}