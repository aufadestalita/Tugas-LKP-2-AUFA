package com.example.aufapunya

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aufapunya.model.InfoJurusan
import com.example.aufapunya.model.InfoSource
import com.example.aufapunya.ui.theme.AufaPunyaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AufaPunyaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    InfoJurusanList(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun InfoJurusanList(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp) // Jarak antar kartu diperkecil sedikit
    ) {
        items(InfoSource.dummyInfoJurusan) { info ->
            InfoJurusanCard(info = info)
        }
    }
}

@Composable
fun InfoJurusanCard(info: InfoJurusan) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp), // Radius sudut lebih pas untuk gambar kecil
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = info.imageRes),
                contentDescription = "Project Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp) // Ukuran gambar lebih ringkas
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = info.namaJurusan,
                    fontSize = 17.sp, // Ukuran judul disesuaikan
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp))

                Text(
                    text = info.deskripsi,
                    fontSize = 13.sp,
                    lineHeight = 18.sp,
                    maxLines = 2, // Batasi 2 baris agar item berikutnya terlihat
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Prospek: ${info.prospekKerja}",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.secondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InfoJurusanPreview() {
    AufaPunyaTheme {
        InfoJurusanList()
    }
}