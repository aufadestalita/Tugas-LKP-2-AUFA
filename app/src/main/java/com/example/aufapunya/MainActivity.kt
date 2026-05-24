package com.example.aufapunya

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import coil.compose.AsyncImage
// Import dari paket data yang baru
import com.example.aufapunya.data.model.InfoJurusan
import com.example.aufapunya.data.repository.JurusanRepository
import com.example.aufapunya.ui.theme.AufaPunyaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AufaPunyaTheme {
                val navController = rememberNavController()
                AppNavigation(navController)
            }
        }
    }
}

@Composable
fun AppNavigation(navController: androidx.navigation.NavHostController) {
    var jurusanList by remember { mutableStateOf<List<InfoJurusan>>(emptyList()) }

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            DaftarJurusanScreen(navController) { jurusanList = it }
        }
        composable("detail/{namaJurusan}") { backStackEntry ->
            val nama = backStackEntry.arguments?.getString("namaJurusan")
            val info = jurusanList.find { it.namaJurusan == nama }
            if (info != null) {
                DetailScreen(info = info, navController = navController)
            }
        }
    }
}

@Composable
fun DaftarJurusanScreen(
    navController: NavController,
    onJurusanLoaded: (List<InfoJurusan>) -> Unit = {}
) {
    // Gunakan Repository
    val repository = remember { JurusanRepository() }

    var listJurusan by remember { mutableStateOf<List<InfoJurusan>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isLoading = true
        val result = repository.getJurusan()
        if (result.isNotEmpty()) {
            listJurusan = result
            onJurusanLoaded(result)
            isError = false
        } else {
            isError = true
        }
        isLoading = false
    }

    when {
        isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        isError -> {
            Box(modifier = Modifier.fillMaxSize().padding(32.dp), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Gagal Memuat Data", color = Color.Red, fontWeight = FontWeight.Bold)
                    Text("Pastikan koneksi internet Anda menyala")
                }
            }
        }
        else -> {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                item { Text("Daftar Jurusan", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold) }
                items(listJurusan) { info ->
                    InfoDetailCard(info = info, navController = navController)
                }
            }
        }
    }
}
@Composable
fun InfoDetailCard(info: InfoJurusan, navController: NavController) {
    var isFavorite by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column {
            Box {
                AsyncImage(
                    model = info.imageUrl,
                    contentDescription = info.namaJurusan,
                    placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                    error = painterResource(id = R.drawable.ic_launcher_foreground),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
                IconButton(
                    onClick = { isFavorite = !isFavorite },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = null,
                        tint = if (isFavorite) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = info.namaJurusan,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { navController.navigate("detail/${info.namaJurusan}") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Lihat Detail")
                }
            }
        }
    }
}

@Composable
fun DetailScreen(info: InfoJurusan, navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                AsyncImage(
                    model = info.imageUrl,
                    contentDescription = info.namaJurusan,
                    placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                    error = painterResource(id = R.drawable.ic_launcher_foreground),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = info.namaJurusan,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = info.deskripsi,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Kembali")
                }
            }
        }
    }
}

