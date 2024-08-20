package com.example.lab4

//Imported libraries
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
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
import coil.compose.rememberAsyncImagePainter
import com.example.lab4.ui.theme.Lab4Theme  

//Main que ejecuta la funcion composable de la app
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab4Theme {
                //Tema base dado por Android como template
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    AppScreen() //Ejecutacion de la app
                }
            }
        }
    }
}

//Configuracion de pantalla con dos text fields, boton y lazyColumn
@Composable
fun AppScreen() {
    //Definicion de variables de estado mutable para el almacenamiento de la lista de elementos (parte de Gestion del Estado)
    var nameInput by remember { mutableStateOf("") }
    var imageUrlInput by remember { mutableStateOf("") }
    val itemList = remember { mutableStateListOf<Pair<String, String>>() }
    //Para Validacion
    var errorMessage by remember { mutableStateOf<String?>(null) }

    //Columna donde se contiene el contenido de la pantalla de la app
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        //Ambos textfields donde se ingresará el nombre de la imagen y el URL de la imagen. Ambos conectados a la variable mutable.
        TextField(
            value = nameInput,
            onValueChange = { nameInput = it },
            label = { Text("Nombre de la receta") },
            modifier = Modifier.fillMaxWidth()
        )

        //Espacio entre ambos textfields
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = imageUrlInput,
            onValueChange = { imageUrlInput = it },
            label = { Text("URL de la imagen") },
            modifier = Modifier.fillMaxWidth()
        )

        //Espacio entre textfield y boton
        Spacer(modifier = Modifier.height(16.dp))

        if (errorMessage != null) {
            Text(
                text = errorMessage ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        //Boton etiquetado que ingresa los elementos a la lista
        Button(
            //Event handler del boton que añade el contenido de los inputs a la lista y luego los deja vacios
            onClick = {
                errorMessage = when {
                    nameInput.isEmpty() -> "El nombre no puede estar vacío."
                    imageUrlInput.isEmpty() -> "La URL no puede estar vacía."
                    itemList.any { it.first == nameInput } -> "El nombre ya está en la lista."
                    else -> null
                }

                if (errorMessage == null) {
                    itemList.add(Pair(nameInput, imageUrlInput))
                    nameInput = ""
                    imageUrlInput = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar")
        }

        //Espacio entre boton y lazyColumn
        Spacer(modifier = Modifier.height(16.dp))

        //LazyColumn que muestra el nombre ingresado de la imagen y la imagen encontrada mediante el URL.
        LazyColumn {
            items(itemList) { item ->
                //Nombre ingresado en el primer textfield e imagen recibida por medio de busqueda por coil
                AppItem(
                    name = item.first,
                    imageUrl = item.second,
                    onItemClicked = { itemList.remove(item) }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

//Bloque de contenido que devuelve la app, donde se contiene el texto y la imagen obtenida por la web.
@Composable
fun AppItem(name: String, imageUrl: String, onItemClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onItemClicked),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Texto ingresado por medio del primer textfield
        Text(text = name, style = MaterialTheme.typography.bodyLarge)
        //Espacio entre texto e imagen
        Spacer(modifier = Modifier.height(8.dp))
        //Imagen obtenida por la web con el uso de la libreria de coil
        Image(
            painter = rememberAsyncImagePainter(
                model = imageUrl,
            ),
            contentDescription = "Image",
            modifier = Modifier
                .size(150.dp)
                .padding(8.dp)
        )
    }
}

//Preview Predeterminada
@Preview(showBackground = true)
@Composable
fun AppScreenPreview() {
    Lab4Theme {
        AppScreen()
    }
}