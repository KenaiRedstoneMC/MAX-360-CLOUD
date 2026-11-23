package com.kenairedstone.max360cloud

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    private var selectedFolder by mutableStateOf<Uri?>(null)

    private val folderPicker = registerForActivityResult(
        ActivityResultContracts.OpenDocumentTree()
    ) { uri ->
        selectedFolder = uri
        if (uri != null) {
            contentResolver.takePersistableUriPermission(
                uri,
                android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text("MAX 360 CLOUD", style = MaterialTheme.typography.headlineMedium)
                        Text("GoPro MAX (2019) → Android → Google Drive")
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text("Estado del proyecto", style = MaterialTheme.typography.titleMedium)
                                Text("Versión inicial: selector de carpeta disponible.")
                                Text("Conexión directa con la cámara: en desarrollo.")
                                Text("Subida a Google Drive: pendiente de integrar OAuth.")
                            }
                        }
                        Button(
                            onClick = { folderPicker.launch(null) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Seleccionar carpeta de medios")
                        }
                        Text(
                            selectedFolder?.let { "Carpeta seleccionada:\n$it" }
                                ?: "Todavía no has seleccionado una carpeta."
                        )
                        Text(
                            "Esta versión no sincroniza ni sube archivos todavía. " +
                                "No necesita tu contraseña de Google."
                        )
                    }
                }
            }
        }
    }
}
