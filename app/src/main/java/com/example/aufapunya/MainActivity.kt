package com.example.aufapunya

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aufapunya.model.InfoJurusan
import com.example.aufapunya.model.InfoSource
import com.example.aufapunya.ui.theme.AufaPunyaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AufaPunyaTheme {

                DaftarInfoScreen()
            }
        }
    }
}

@Composable
fun DaftarInfoScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {

        InfoSource.dummyInfoJurusan.forEach { info ->
            InfoDetailCard(info = info)


            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun InfoDetailCard(info: InfoJurusan) {

    Column(modifier = Modifier.fillMaxWidth()) {

        Image(
            painter = painterResource(id = info.imageRes),
            contentDescription = info.namaJurusan,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))


        Text(
            text = info.namaJurusan,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))


        Text(
            text = info.deskripsi,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(8.dp))


        Text(
            text = "Prospek Kerja: ${info.prospekKerja}",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(16.dp))


        Button(
            onClick = { /* Handle klik di sini jika perlu */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Lihat Detail Jurusan")
        }
    } //update upa
}

@Preview(showBackground = true)
@Composable
fun InfoJurusanPreview() {
    AufaPunyaTheme {
        DaftarInfoScreen()
    }
}