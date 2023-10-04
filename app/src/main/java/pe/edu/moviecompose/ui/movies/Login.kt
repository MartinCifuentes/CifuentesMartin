package pe.edu.moviecompose.ui.movies

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(){
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = username.value,
            onValueChange = {newValue->
                username.value = newValue
            },
            placeholder = {Text("Username")}
        )
        OutlinedTextField(
            value = password.value,
            onValueChange = {newValue->
                password.value = newValue
            },
            visualTransformation = PasswordVisualTransformation(),
            placeholder = {Text("Password")}
        )
        Button(onClick = { }){
            Text("Sign in")
        }
        TextButton(onClick = {}){
            Text("Sign up")
        }
    }
}