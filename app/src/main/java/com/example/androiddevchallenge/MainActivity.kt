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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.example.androiddevchallenge.ui.theme.MyTheme

data class Pet(
    val id: Long,
    val name: String
)

class PetsViewModel : ViewModel() {

    private val petsDataStore: Map<Long, Pet> = hashMapOf(
        0L to Pet(0, "Aaliyah"),
        1L to Pet(1, "Asterix"),
        2L to Pet(2, "Rocky"),
        3L to Pet(3, "Mara")
    )

    val pets: List<Pet> = petsDataStore.map { it.value }

    val pet: Map<Long, Pet> = petsDataStore


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
    Text(text = pet.name, Modifier.clickable { onPetClicked() })
}

@Composable
fun PetsDetailView(navController: NavHostController, petId: Long?) {

    val viewModel: PetsViewModel = viewModel()
    val pet = viewModel.pet[petId]!!
    Surface(color = MaterialTheme.colors.background) {
        Text(text = pet.name)
    }

}
