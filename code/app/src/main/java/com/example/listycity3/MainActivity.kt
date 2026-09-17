package com.example.listycity3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.listycity3.ui.theme.ListyCity3Theme

class MainActivity : ComponentActivity() {
    private val cityRepository = CityRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ListyCity3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    var selectedCity by remember { mutableStateOf<City?>(null) }
                    var nameText by remember { mutableStateOf("") }
                    var provinceText by remember { mutableStateOf("") }

                    Column(modifier = Modifier.padding(innerPadding)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = nameText,
                                onValueChange = { nameText = it },
                                label = { Text("City") },
                                modifier = Modifier.weight(1f)
                            )
                            OutlinedTextField(
                                value = provinceText,
                                onValueChange = { provinceText = it },
                                label = { Text("Province") },
                                modifier = Modifier.weight(1f)
                            )
                            Button(onClick = {
                                if (nameText.isNotBlank() && provinceText.isNotBlank()) {
                                    val newCity = City(nameText, provinceText)
                                    val current = selectedCity
                                    if (current == null) {
                                        cityRepository.addCity(newCity)
                                    } else {
                                        cityRepository.updateCity(current, newCity)
                                    }
                                    selectedCity = null
                                    nameText = ""
                                    provinceText = ""
                                }
                            }) {
                                Text(if (selectedCity == null) "ADD" else "UPDATE")
                            }
                        }

                        CityListScreen(
                            cities = cityRepository.cities,
                            onCityClick = { city ->
                                selectedCity = city
                                nameText = city.name
                                provinceText = city.province
                            }
                        )
                    }
                }
            }
        }
    }
}