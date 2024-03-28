package com.application.travel_app.views

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleDown
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.application.travel_app.R
import com.application.travel_app.data.database.Travel
import com.application.travel_app.data.viewmodel.TravelsViewModel
import com.application.travel_app.ui.theme.Green40
import com.application.travel_app.views.utils.CustomButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTravelScreen(travelsViewModel: TravelsViewModel) {
    val context = LocalContext.current
    var placeName by rememberSaveable { mutableStateOf("") }
    var location by rememberSaveable { mutableStateOf("") }
    var review by rememberSaveable { mutableStateOf("") }
    var price by rememberSaveable { mutableStateOf("") }
    var isReviewDropDownExpanded by remember { mutableStateOf(false) }
    var isPriceDropDownExpanded by remember { mutableStateOf(false) }
    val reviewList = listOf("Very Bad ", "Bad", "Good", "Great", "Very Great")
    val priceList = listOf(
        "$20",
        "$30",
        "$40",
        "$55",
        "$67",
    )


    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Add Travel Place",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Green40,
                modifier = Modifier
                    .padding(top = 60.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = placeName,
                label = { Text(stringResource(id = R.string.place_name)) },
                onValueChange = {
                    placeName = it
                },
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = location,
                label = { Text(stringResource(id = R.string.location)) },
                onValueChange = {
                    location = it
                },
            )
            Spacer(modifier = Modifier.height(10.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box {
                    OutlinedTextField(
                        value = review,
                        onValueChange = { review = it },
                        placeholder = { androidx.compose.material.Text(text = "Give Your Review") },
                        enabled = false,
                        modifier = Modifier
                            .clickable {
                                isReviewDropDownExpanded = true
                            }
                            .fillMaxWidth(0.8f),
                        textStyle = TextStyle(color = Color.Black),
                        trailingIcon = { Icon(imageVector = Icons.Default.ArrowCircleDown, "") }
                    )

                    DropdownMenu(
                        expanded = isReviewDropDownExpanded,
                        onDismissRequest = { isReviewDropDownExpanded = false },
                        modifier = Modifier
                            .fillMaxWidth(0.8f),
                    ) {
                        reviewList.forEachIndexed { index, selectedItem ->
                            DropdownMenuItem(onClick = {
                                review = selectedItem
                                isReviewDropDownExpanded = false
                            }) {
                                androidx.compose.material.Text(selectedItem)
                            }
                            if (index != reviewList.lastIndex)
                                Divider(Modifier.background(Color.Black))
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box {
                    OutlinedTextField(
                        value = price,
                        onValueChange = { price = it },
                        placeholder = { androidx.compose.material.Text(text = "Choose The Price") },
                        enabled = false,
                        modifier = Modifier
                            .clickable {
                                isPriceDropDownExpanded = true
                            }
                            .fillMaxWidth(0.8f),
                        textStyle = TextStyle(color = Color.Black),
                        trailingIcon = { Icon(imageVector = Icons.Default.ArrowCircleDown, "") }
                    )

                    DropdownMenu(
                        expanded = isPriceDropDownExpanded,
                        onDismissRequest = { isPriceDropDownExpanded = false },
                        modifier = Modifier
                            .fillMaxWidth(0.8f),
                    ) {
                        priceList.forEachIndexed { index, selectedItem ->
                            DropdownMenuItem(onClick = {
                                price = selectedItem
                                isPriceDropDownExpanded = false
                            }) {
                                androidx.compose.material.Text(selectedItem)
                            }
                            if (index != priceList.lastIndex)
                                Divider(Modifier.background(Color.Black))
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            CustomButton(stringResource(id = R.string.add_place)) {
                // Create the travel object
                if (placeName == "" || location == "" || review == "" || price == "") {
                    Toast.makeText(context, "Place Added Failed", Toast.LENGTH_SHORT).show()
                    Log.d("data db", "Data Gagal")
                } else {
                    val travel = Travel(placeName, location, review, price)
                    Log.d("data db", "Data Berhasil $travel")

                    // Update the travel to the database
                    travelsViewModel.addTravel(travel)
                    // Clear text fields
                    placeName = ""
                    location = ""
                    review = ""
                    price = ""
                    Toast.makeText(context, "Place added successfully", Toast.LENGTH_SHORT)
                        .show()
                }

            }
        }
    }
}
