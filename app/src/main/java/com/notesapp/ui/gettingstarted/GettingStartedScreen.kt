package com.notesapp.ui.gettingstarted

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.notesapp.ui.navigation.Screens
import com.notesapp.ui.util.OnBoardingPage

@Composable
fun GettingStartedScreen(
    navController: NavHostController,
    viewModel: GettingStartedViewModel,
) {
    GettingStartedContent(
        onFinishClick = {
            viewModel.completeOnboarding()
            navController.navigate(Screens.MainScreen.route) {
                popUpTo(Screens.GettingStartedScreen.route) { inclusive = true }
            }
        },
    )
}

@Composable
fun GettingStartedContent(
    onFinishClick: () -> Unit,
    initialPage: Int = 0,
) {
    val pages =
        listOf(
            OnBoardingPage.First,
            OnBoardingPage.Second,
            OnBoardingPage.Third,
        )
    val pagerState =
        rememberPagerState(
            initialPage = initialPage,
            pageCount = { pages.size },
        )

    Column(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            modifier = Modifier.weight(10f),
            state = pagerState,
            verticalAlignment = Alignment.Top,
        ) { position ->
            PagerScreen(onBoardingPage = pages[position])
        }
        Row(
            modifier =
                Modifier
                    .weight(1.5f)
                    .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) Color.DarkGray
                    else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(16.dp),
                )
            }
        }
        FinishButton(
            modifier = Modifier.weight(2f),
            pagerState = pagerState,
            onClick = onFinishClick,
        )
    }
}

@Composable
fun PagerScreen(onBoardingPage: OnBoardingPage) {
    Column(
        modifier =
            Modifier
                .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Image(
            modifier =
                Modifier
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight(0.7f),
            painter = painterResource(onBoardingPage.image),
            contentDescription = "Pager Image",
        )
        Text(
            text = onBoardingPage.title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
        )
        Text(
            text = onBoardingPage.description,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun FinishButton(
    modifier: Modifier,
    pagerState: PagerState,
    onClick: () -> Unit,
) {
    Row(
        modifier =
            modifier
                .padding(horizontal = 60.dp),
    ) {
        AnimatedVisibility(
            modifier = Modifier.fillMaxWidth(),
            visible = pagerState.currentPage == pagerState.pageCount - 1
        ) {
            Button(
                onClick = onClick,
                colors =
                    ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                    ),
            ) {
                Text(text = "Finish")
            }
        }
    }
}

@Preview(name = "Onboarding first page", showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun FirstOnBoardingScreenPreview() {
    GettingStartedContent(
        onFinishClick = {},
        initialPage = 0,
    )
}

@Preview(name = "Onboarding second page", showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun SecondOnBoardingScreenPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        GettingStartedContent(
            onFinishClick = {},
            initialPage = 1,
        )
    }
}

@Preview(name = "Onboarding third page", showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun ThirdOnBoardingScreenPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        GettingStartedContent(
            onFinishClick = {},
            initialPage = 2,
        )
    }
}
