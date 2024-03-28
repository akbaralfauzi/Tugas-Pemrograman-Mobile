package com.application.travel_app.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.navigation.NavController
import com.application.travel_app.R
import com.application.travel_app.data.database.Travel
import com.application.travel_app.data.viewmodel.TravelsViewModel
import com.application.travel_app.navigation.Screen
import com.application.travel_app.views.utils.CustomUpdateButton
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TravelDetailScreen(id: Int, travelsViewModel: TravelsViewModel, navController: NavController) {
    val context = LocalContext.current
    var placeNameState: String? by remember { mutableStateOf(null) }
    var locationState: String? by remember { mutableStateOf(null) }
    var reviewState: String? by remember { mutableStateOf(null) }
    var priceState: String? by remember { mutableStateOf(null) }
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


    LaunchedEffect(Unit) {
        travelsViewModel.getTravel(id)
    }
    travelsViewModel.getTravel(id)

    val travel = travelsViewModel.getTravel.observeAsState().value
    travel ?: return
    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(
                    text = "Details Donation",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 60.dp),
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            Column(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                OutlinedTextField(
                    value = placeNameState
                        ?: travel.placeName,  // display database text if no modified text available
                    onValueChange = { placeNameState = it },
                    label = { Text(stringResource(id = R.string.place_name)) },
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = locationState
                        ?: travel.location,
                    onValueChange = { locationState = it },
                    label = { Text(stringResource(id = R.string.location)) },
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Box {
                    OutlinedTextField(
                        value = reviewState ?: travel.review,
                        onValueChange = { reviewState = it },
                        placeholder = { androidx.compose.material.Text(text = travel.review) },
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
                            .fillMaxWidth(0.8f)
                    ) {
                        reviewList.forEachIndexed { index, selectedItem ->
                            DropdownMenuItem(onClick = {
                                reviewState = selectedItem
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

            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box {
                    OutlinedTextField(
                        value = priceState ?: travel.price,
                        onValueChange = { priceState = it },
                        placeholder = { androidx.compose.material.Text(text = travel.price) },
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
                            .fillMaxWidth(0.8f)
                    ) {
                        priceList.forEachIndexed { index, selectedItem ->
                            DropdownMenuItem(onClick = {
                                priceState = selectedItem
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

            Spacer(modifier = Modifier.height(15.dp))

            Column(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                CustomUpdateButton(stringResource(id = R.string.update_place))
                {
                    // Create the travel object
                    val travel = Travel(
                        placeName = placeNameState ?: travel.placeName,
                        location = locationState ?: travel.location,
                        review = reviewState ?: travel.review,
                        price = priceState ?: travel.price
                    )

                    // Update the travel in the database
                    travelsViewModel.updateTravel(
                        id,
                        travel.placeName,
                        travel.location,
                        travel.review,
                        travel.price
                    )
                    Toast.makeText(context, "Place updated successfully", Toast.LENGTH_SHORT)
                        .show()

                }
                Column(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    val openDialog = remember { mutableStateOf(false) }

                    Button(onClick = { openDialog.value = true }) {
                        Text(text = "Delete Place")
                    }

                    if (openDialog.value) {
                        AlertDialog(
                            onDismissRequest = { openDialog.value = false },
                            title = {
                                Text(text = "Deleting Place")
                            },
                            text = {
                                Text(text = "Do you really want to Delete this Place ?")
                            },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        travel?.let { id ->
                                            travelsViewModel.deleteTravel(id)
                                        }
                                        openDialog.value = false
                                        Toast.makeText(context, "Place Deleted successfully", Toast.LENGTH_SHORT)
                                            .show()
                                        navController.navigate(Screen.AllTravelsScreen.route)
                                    },
                                ) {
                                    Text(text = "CONFIRM")

                                }
                            },
                            dismissButton = {
                                Button(onClick = { openDialog.value = false }
                                ) {
                                    Text(text = "CANCEL")
                                }
                            }
                        )
                    }
                }

            }

        }

    }
}



