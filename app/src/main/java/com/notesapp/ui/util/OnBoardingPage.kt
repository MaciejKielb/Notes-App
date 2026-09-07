package com.notesapp.ui.util

import androidx.annotation.DrawableRes
import com.notesapp.R

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String,
) {
    object First : OnBoardingPage(
        image = R.drawable.first_page_image,
        title = "Quick capture",
        description = "Capture in a second — your thought won’t slip away.",
    )

    object Second : OnBoardingPage(
        image = R.drawable.second_page_image,
        title = "Stay organized",
        description = "Everything in one place — your notes, always at hand.",
    )

    object Third : OnBoardingPage(
        image = R.drawable.third_page_image,
        title = "Start",
        description = "Ready to write — your first note is waiting..",
    )
}
