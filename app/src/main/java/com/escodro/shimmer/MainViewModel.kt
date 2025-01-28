package com.escodro.shimmer

import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class MainViewModel : ViewModel() {

    val state: Flow<MainState> = flow {
        emit(MainState.Loading)
        delay(4_000)
        emit(
            MainState.Success(
                ItemData(
                    imageResId = R.drawable.ic_android,
                    title = "Android",
                    description = "Android is a special mobile operating system"
                )
            )
        )
    }
}

internal sealed interface MainState {
    data object Loading : MainState
    data class Success(val listItem: ItemData) : MainState
}

data class ItemData(
    @DrawableRes val imageResId: Int,
    val title: String,
    val description: String
)
