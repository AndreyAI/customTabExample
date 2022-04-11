package com.example.customtab.ui.detail

sealed class ScreenState {
    object DefaultState : ScreenState()
    object LoadingState : ScreenState()
    object ErrorState : ScreenState()
}
