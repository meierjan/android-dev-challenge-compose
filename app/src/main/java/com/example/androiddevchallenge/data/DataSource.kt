package com.example.androiddevchallenge.data

import com.example.androiddevchallenge.Pet

val DUMMY_DATA = listOf(
    Pet(
        id = 0,
        name = "Aaliyah",
        animalType = Pet.AnimalType.Cat,
        gender = Pet.Gender.Female,
        yearOfBirth = 2001,
        historySummary = ""
    ),
    Pet(
        id = 1,
        name = "Max",
        animalType = Pet.AnimalType.Dog("Beagle"),
        gender = Pet.Gender.Male,
        yearOfBirth = 1988,
        historySummary = ""
    ),
    Pet(
        id = 2,
        name = "Rocky",
        animalType = Pet.AnimalType.Cat,
        gender = Pet.Gender.Male,
        yearOfBirth = 1998,
        historySummary = ""
    ),
    Pet(
        id = 3,
        name = "Tigger",
        animalType = Pet.AnimalType.Cat,
        gender = Pet.Gender.Male,
        yearOfBirth = 2004,
        historySummary = ""
    ),
    Pet(
        id = 4,
        name = "Mirko",
        animalType = Pet.AnimalType.Cat,
        gender = Pet.Gender.Male,
        yearOfBirth = 1986,
        historySummary = ""
    ),
    Pet(
        id = 5,
        name = "Maja",
        animalType = Pet.AnimalType.Dog("Labrador"),
        gender = Pet.Gender.Female,
        yearOfBirth = 1996,
        historySummary = ""
    )
)