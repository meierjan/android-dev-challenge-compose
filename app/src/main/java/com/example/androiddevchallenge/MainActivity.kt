/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.example.androiddevchallenge.data.DUMMY_DATA
import com.example.androiddevchallenge.ui.theme.MyTheme

data class Pet(
    val id: Long,
    val icon: String = "\uD83D\uDC3E",
    val name: String,
    val animalType: Species,
    val gender: Gender,
    val yearOfBirth: Short,
    val historySummary: String
) {
    enum class Gender {
        Male, Female, Diverse
    }

    sealed class Species(private val name: String) {

        open val displayName = name

        object Cat : Species("Cat")
        data class Dog(val breed: String) : Species("Dog")
    }
}


class PetsViewModel : ViewModel() {


    val pets: List<Pet> = DUMMY_DATA

    val pet: Map<Long, Pet> by lazy { DUMMY_DATA.associateBy { it.id } }


}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                val navController = rememberNavController()

                NavHost(navController, startDestination = "/") {
                    composable("/") { PetsOverviewPage(navController) }
                    composable(
                        "pet/{petId}",
                        arguments = listOf(navArgument("petId") {
                            type = NavType.LongType
                        })
                    ) { backStackEntry ->
                        PetsDetailView(
                            navController,
                            backStackEntry.arguments?.getLong("petId")
                        )
                    }
                }
            }
        }
    }
}

// Start building your app here!
@Composable
fun PetsOverviewPage(navController: NavHostController) {

    val viewModel: PetsViewModel = viewModel()

    Surface(color = MaterialTheme.colors.background) {
        LazyColumn {
            itemsIndexed(viewModel.pets) { _, item ->
                PetListView(item) { navController.navigate("pet/${item.id}") }
            }
        }
    }
}

@Composable
fun PetListView(pet: Pet, onPetClicked: () -> Unit) {
    Card(
        Modifier
            .padding(8.dp)
            .clickable { onPetClicked() }
    ) {
        Row(Modifier.padding(4.dp)) {
            PetImageView(
                pet,
                Modifier
                    .fillMaxSize()
                    .weight(0.4F, true)
            )
            PetFactView(pet, Modifier.weight(0.6F))
        }
    }
}


@Composable
fun PetImageView(pet: Pet, modifier: Modifier = Modifier) {
    Text(
        text = pet.icon,
        fontSize = 50.sp,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}

@Composable
fun PetFactView(pet: Pet, modifier: Modifier) {
    Column(modifier) {
        // Headline
        Text(
            text = pet.name,
            maxLines = 1,
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.primary
        )

        // Description
        Row {
            Column(Modifier.weight(0.5F, fill = true)) {
                Text(text = "Species")
                Text(
                    text = pet.animalType.displayName,
                    style = MaterialTheme.typography.h6,
                    maxLines = 1
                )
            }
            Column(Modifier.weight(0.5F, fill = true)) {
                Text(text = "Gender")
                Text(
                    text = pet.gender.name,
                    style = MaterialTheme.typography.h6,
                    maxLines = 1
                )
            }
        }
    }

}

@Preview
@Composable
fun previewPetListView() {
    PetListView(pet = DUMMY_DATA[0], onPetClicked = { /*TODO*/ })
}

@Composable
fun PetsDetailView(navController: NavHostController, petId: Long?) {

    val viewModel: PetsViewModel = viewModel()
    val pet = viewModel.pet[petId]!!
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            painterResource(id = android.R.drawable.ic_menu_close_clear_cancel),
                            "back"
                        )
                    }
                },
                elevation = 0.dp,

                contentColor = MaterialTheme.colors.primary,
                backgroundColor = MaterialTheme.colors.background,
                title = { })
        }
    ) {
        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(Modifier.fillMaxWidth()) {
                Row(
                    Modifier
                        .fillMaxWidth()
                ) {
                    Surface(
                        modifier = Modifier
                            .weight(0.5F, true)
                    ) {
                        PetImageView(pet)
                    }
                    PetFactView(pet, Modifier.weight(0.5F))
                }
                Text(
                    text = "History",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = pet.historySummary,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}


