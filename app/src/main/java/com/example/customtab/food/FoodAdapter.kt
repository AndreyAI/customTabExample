package com.example.customtab.food

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.customtab.R
import com.example.customtab.data.Food
import com.example.customtab.databinding.ItemFoodBinding

class FoodAdapter(
    private val onDetailFood: (Food) -> Unit
) :
    PagingDataAdapter<Food, FoodAdapter.Holder>(FOOD_COMPARATOR) {

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding, onDetailFood)
    }

    class Holder(
        private val binding: ItemFoodBinding,
        private val onDetailFood: (Food) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentFood: Food? = null

        init {
            binding.root.setOnClickListener {
                currentFood?.let {
                    onDetailFood(it)
                }
            }
        }

        fun bind(food: Food?) {
            currentFood = food
            binding.textTitle.text = currentFood?.title
            binding.textPrice.text = currentFood?.type
            binding.textDesc.text = currentFood?.year

            Glide.with(itemView)
                .load(currentFood?.poster)
                .placeholder(R.drawable.image)
                .into(binding.imageFood)
        }
    }

    companion object {
        private val FOOD_COMPARATOR = object : DiffUtil.ItemCallback<Food>() {
            override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean {
                return oldItem == newItem
            }
        }
    }
}