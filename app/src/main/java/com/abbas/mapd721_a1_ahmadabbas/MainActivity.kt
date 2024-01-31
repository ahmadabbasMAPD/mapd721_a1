package com.abbas.mapd721_a1_ahmadabbas


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abbas.mapd721_a1_ahmadabbas.ui.theme.MAPD721A1AhmadAbbasTheme
import kotlinx.coroutines.launch



// This is a class that extends ComponentActivity and sets the content view

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the content view to a composable function called MainScreen
        setContent {
            // Apply a custom theme
            MAPD721A1AhmadAbbasTheme {
                // Create a surface with a background color
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Call the MainScreen composable function
                    MainScreen()
                }
            }
        }
    }
}

// This is a composable function that displays the main screen of the app
@Composable
fun MainScreen(){
    // Get the current context
    val context = LocalContext.current
    // Create a coroutine scope
    val coroutineScope = rememberCoroutineScope()

    // Create a data store object
    val dataStore = DataStore(context)

    // Get the saved user data states from the data store
    val savedUsernameState = dataStore.getUsername.collectAsState(initial = "")
    val savedUserEmailState = dataStore.getUserEmail.collectAsState(initial = "")
    val savedUserIdState = dataStore.getUserId.collectAsState(initial = "")

    // Create mutable states for the user input fields
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var id by remember { mutableStateOf("") }

    // Create a column layout with padding
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        // Created an outlined text field for the username input
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = username,
            onValueChange = { username = it },
            label = { Text(text = "Username", color = Color.Black, fontSize = 14.sp) }
        )
        // Add some space between the text fields
        Spacer(modifier = Modifier.height(8.dp))

        // Create an outlined text field for the email input
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "User Email", color = Color.Black, fontSize = 14.sp) },
        )
        // Add some space between the text fields
        Spacer(modifier = Modifier.height(8.dp))

        // Create an outlined text field for the id input
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = id,
            onValueChange = { id = it },
            label = { Text(text = "User ID", color = Color.Black, fontSize = 14.sp) },
        )
        // Add some space between the text fields and the buttons
        Spacer(modifier = Modifier.height(16.dp))

        // Create a row layout for the buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            // Create a button for saving the user data
            Button(
                onClick = {
                    // Launch a coroutine to save the user data
                    coroutineScope.launch {
                        // Check if the id input is not empty
                        if (id.isNotEmpty()) {
                            try {
                                // Try to convert the id input to an integer and save the user data
                                dataStore.saveUserData(UserData(username, email, id.toInt()))
                            } catch (e: NumberFormatException) {
                                // If the id input is not a valid integer, save the user data with -1 as the id
                                dataStore.saveUserData(UserData(username, email, -1))
                            }
                        } else {
                            // If the id input is empty, save the user data with -1 as the id
                            dataStore.saveUserData(UserData(username, email, -1))
                        }
                    }
                }
            ) {
                // Set the text of the button to "Save"
                Text("Save")
            }

            // Create a button for clearing the user data
            Button(
                onClick = {
                    // Launch a coroutine to clear the user data
                    coroutineScope.launch {
                        dataStore.clearUserData()
                    }
                }) {
                // Set the text of the button to "Clear"
                Text("Clear")
            }

            // Create a button for loading the user data
            Button(
                onClick = {
                    // Load the user data from the saved states and assign them to the input fields
                    username = savedUsernameState.value ?: ""
                    email = savedUserEmailState.value ?: ""
                    id = if(savedUserIdState.value != -1) {
                        // If the saved id is not -1, convert it to a string
                        (savedUserIdState.value ?: "").toString()
                    } else {
                        // If the saved id is -1, set the id input to empty
                        ""
                    }
                }) {
                // Set the text of the button to "Load"
                Text("Load")
            }
        }

        // Add some space between the buttons and the text
        Spacer(modifier = Modifier.weight(1f))

        // Create a column layout for the text
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
        ) {
            // Display the name of the author
            Text("Ahmad Abbas")
            // Display the id of the author
            Text("301372338")
        }
    }
}

// This is a preview function that shows how the main screen looks like
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    // Apply the custom theme
    MAPD721A1AhmadAbbasTheme {
        // Call the MainScreen composable function
        MainScreen()
    }
}

