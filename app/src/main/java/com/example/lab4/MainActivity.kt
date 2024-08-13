package com.example.lab4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.example.lab4.ui.theme.Lab4Theme
import coil.compose.rememberImagePainter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab4Theme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    RecipeScreen()
                }
            }
        }
    }
}

@Composable
fun RecipeScreen() {
    var nameInput by remember { mutableStateOf("") }
    var imageUrlInput by remember { mutableStateOf("") }
    val itemList = remember { mutableStateListOf<Pair<String, String>>() }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        TextField(
            value = nameInput,
            onValueChange = { nameInput = it },
            label = { Text("Nombre de la receta") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = imageUrlInput,
            onValueChange = { imageUrlInput = it },
            label = { Text("URL de la imagen") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (nameInput.isNotEmpty() && imageUrlInput.isNotEmpty()) {
                    itemList.add(Pair(nameInput, imageUrlInput))
                    nameInput = ""
                    imageUrlInput = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(itemList) { item ->
                RecipeItem(name = item.first, imageUrl = item.second)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun RecipeItem(name: String, imageUrl: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = name, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Image(
            painter = rememberImagePainter(data = imageUrl),
            contentDescription = "Recipe Image",
            modifier = Modifier
                .size(150.dp)
                .padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeScreenPreview() {
    Lab4Theme {
        RecipeScreen()
    }
}