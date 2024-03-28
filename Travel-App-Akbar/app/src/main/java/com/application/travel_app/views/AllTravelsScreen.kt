package com.application.travel_app.views

import HandleBackPress
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.application.travel_app.data.database.Travel
import com.application.travel_app.data.viewmodel.TravelsViewModel
import com.application.travel_app.navigation.Screen
import com.application.travel_app.views.utils.TitleText
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import coil.compose.AsyncImage
import com.application.travel_app.R
import com.application.travel_app.ui.theme.Green40


@Composable
fun AllTravelsScreen(navController: NavController, travelsViewModel: TravelsViewModel) {
    val travels: List<Travel> by travelsViewModel.allTravels.observeAsState(initial = listOf())
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    LaunchedEffect(Unit) {
        travelsViewModel.getTravels()
    }

    LaunchedEffect(Unit) {
        travelsViewModel.getTravels()
    }

    Box(
        modifier = Modifier
            .background(color = Green40)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .background(color = Green40)
        ) {
            if (travels.isEmpty()) {
                TitleText(
                    text = stringResource(id = R.string.all_travels_title),
                    modifier = Modifier,
                )
                Column(
                    modifier = Modifier.offset(y = -(35).dp)
                ) {
                    EmptyContent()
                }
            } else {
                TitleText(
                    text = stringResource(id = R.string.all_travels_title),
                    modifier = Modifier,
                )
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .background(color = Green40),
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    items(travels) { travel ->
                        OutlinedCard(
                            Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .clickable {
                                    navController.navigate(route = Screen.TravelDetailsScreen.route + "/" + travel.id)
                                },
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .padding(10.dp)
                            ) {
                                AsyncImage(
                                    model = "https://source.unsplash.com/random/300x300/?travel",
                                    contentDescription = null,
                                    contentScale = ContentScale.FillBounds,
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .clip(shape = RoundedCornerShape(10.dp))
                                )
                                Column {
                                    Text(
                                        text = travel.placeName,
                                        modifier = Modifier
                                            .padding(8.dp, 8.dp, 0.dp, 0.dp),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 24.sp
                                    )
                                    Text(
                                        text = "Review - ${travel.review}",
                                        modifier = Modifier
                                            .padding(8.dp, 8.dp, 0.dp, 5.dp),
                                        fontStyle = FontStyle.Italic,
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        text = "${travel.location}",
                                        modifier = Modifier
                                            .padding(8.dp, 0.dp, 0.dp, 0.dp),
                                        fontSize = 14.sp,
                                    )
                                }

                                Text(
                                    text = "${travel.price}/Person",
                                    modifier = Modifier.padding(0.dp, 90.dp, 0.dp, 0.dp),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.End
                                )
                            }
                        }
                    }
                }
            }
        }
    }



    if (backDispatcher != null) {
        HandleBackPress(backDispatcher) {
            navController.navigate(Screen.HomeScreen.route)
        }
    }
}

@Composable
fun EmptyContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Green40),
    ) {
        Icon(
            modifier = Modifier.size(120.dp),
            painter = painterResource(
                R.drawable.travel
            ), contentDescription = stringResource(
                R.string.no_travel_place
            ),
            tint = Color.LightGray
        )
        androidx.compose.material.Text(
            text = stringResource(
                R.string.text_empty_content
            ),
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.h6.fontSize,
            color = Color.LightGray
        )
    }
}
