package com.example.customtab.banner

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.customtab.R
import com.example.customtab.data.Banner
import com.example.customtab.databinding.ItemBannerBinding
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class BannerAdapterDelegate(
    private val onClickBanner: (Banner) -> Unit
) :
    AbsListItemAdapterDelegate<Banner, Banner, BannerAdapterDelegate.Holder>() {

    override fun isForViewType(item: Banner, items: MutableList<Banner>, position: Int): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        val binding = ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding, onClickBanner)
    }

    override fun onBindViewHolder(item: Banner, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    class Holder(
        private val binding: ItemBannerBinding,
        private val onClickBanner: (Banner) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentBanner: Banner? = null

        init {
            binding.imageBanner.setOnClickListener {
                currentBanner?.let {
                    onClickBanner(it)
                }
            }
        }

        fun bind(banner: Banner) {
            currentBanner = banner
            Glide.with(itemView)
                .load(banner.bannerUrl)
                .placeholder(R.drawable.image)
                .into(binding.imageBanner)
        }
    }
}