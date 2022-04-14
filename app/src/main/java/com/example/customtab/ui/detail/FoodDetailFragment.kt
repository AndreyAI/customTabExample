package com.example.customtab.ui.detail

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.customtab.R
import com.example.customtab.data.FoodDetail
import com.example.customtab.data.Repository
import com.example.customtab.databinding.FragmentFoodBinding

class FoodDetailFragment : Fragment(R.layout.fragment_food) {

    private val args: FoodDetailFragmentArgs by navArgs()
    private val binding by viewBinding(FragmentFoodBinding::bind)
    private val viewModel: ViewModelFoodDetail by viewModels() {
        ViewModelFoodDetail.DetailFactory(
            repository = Repository()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.bind(args.id)
        viewModel.state.observe(viewLifecycleOwner) {
            initState(it)
        }
        viewModel.infoFood.observe(viewLifecycleOwner) {
            bindViewModel(it)
        }
    }

    private fun initState(screenState: ScreenState) = when (screenState) {
        ScreenState.LoadingState -> initUi(false, true, false)
        ScreenState.DefaultState -> initUi(true, false, false)
        ScreenState.ErrorState -> initUi(false, false, true)
    }

    private fun initUi(isContainer: Boolean, isProgress: Boolean, isError: Boolean) {
        with(binding) {
            container.isVisible = isContainer
            progress.isVisible = isProgress
            textError.isVisible = isError
        }
    }

    private fun bindViewModel(foodDetail: FoodDetail) {
        with(binding) {
            textTitle.text = foodDetail.title
            textDirectorName.text = foodDetail.directors
            textActorsNames.text = foodDetail.actors
            textYear.text = foodDetail.year
            textGenre.text = foodDetail.genre
            textRating.text = foodDetail.rating

            Glide.with(imagePoster)
                .load(foodDetail.poster)
                .placeholder(R.drawable.image)
                .into(imagePoster)
        }
    }
}