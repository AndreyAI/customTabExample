package com.example.customtab.banner

import androidx.recyclerview.widget.DiffUtil
import com.example.customtab.data.Banner
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class BannerAdapter(
    onClickBanner: (Banner) -> Unit
) : AsyncListDifferDelegationAdapter<Banner>(BannerDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(BannerAdapterDelegate(onClickBanner))
    }

    class BannerDiffUtilCallback : DiffUtil.ItemCallback<Banner>() {
        override fun areItemsTheSame(oldItem: Banner, newItem: Banner): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Banner, newItem: Banner): Boolean {
            return oldItem == newItem
        }
    }
}