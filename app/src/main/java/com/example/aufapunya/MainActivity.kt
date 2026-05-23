package com.example.aufapunya

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.aufapunya.model.InfoJurusan
import com.example.aufapunya.model.InfoSource
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
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            DaftarInfoScreen(navController)
        }
        composable("detail/{namaJurusan}") { backStackEntry ->
            val nama = backStackEntry.arguments?.getString("namaJurusan")
            val info = InfoSource.dummyInfoJurusan.find { it.namaJurusan == nama }
            if (info != null) {
                DetailScreen(info = info, navController = navController, isFullScreen = true)
            }
        }
    }
}

@Composable
fun DaftarInfoScreen(navController: NavController) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            Text(text = "Rekomendasi Jurusan", style = MaterialTheme.typography.titleLarge)
            LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                items(InfoSource.dummyInfoJurusan) { info ->
                    JurusanRowItem(info = info, navController = navController)
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(text = "Daftar Semua Jurusan", style = MaterialTheme.typography.titleLarge)
        }
        items(InfoSource.dummyInfoJurusan) { info ->
            InfoDetailCard(info = info, navController = navController)
        }
    }
}

@Composable
fun JurusanRowItem(info: InfoJurusan, navController: NavController) {
    Card(
        modifier = Modifier.width(160.dp).clickable { navController.navigate("detail/${info.namaJurusan}") },
        shape = RoundedCornerShape(12.dp)
    ) {
        Column {
            Image(painter = painterResource(id = info.imageRes), contentDescription = null, modifier = Modifier.fillMaxWidth().height(100.dp), contentScale = ContentScale.Crop)
            Text(text = info.namaJurusan, style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(8.dp), maxLines = 1)
        }
    }
}

@Composable
fun InfoDetailCard(info: InfoJurusan, navController: NavController) {
    var isFavorite by remember { mutableStateOf(false) }

    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
        Column {
            Box {
                Image(painter = painterResource(id = info.imageRes), contentDescription = null, modifier = Modifier.fillMaxWidth().height(200.dp), contentScale = ContentScale.Crop)
                IconButton(
                    onClick = { isFavorite = !isFavorite },
                    modifier = Modifier.align(Alignment.TopEnd).padding(8.dp)
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = info.namaJurusan, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { navController.navigate("detail/${info.namaJurusan}") }, modifier = Modifier.fillMaxWidth()) {
                    Text("Lihat Detail")
                }
            }
        }
    }
}

@Composable
fun DetailScreen(info: InfoJurusan, navController: NavController, isFullScreen: Boolean = false) {
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Box(modifier = Modifier.fillMaxWidth().padding(24.dp)) {
        Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Detail: ${info.namaJurusan}", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = info.deskripsi)
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = {
                        if (isFullScreen) {
                            navController.popBackStack()
                        } else {
                            coroutineScope.launch {
                                isLoading = true
                                delay(2000)
                                // Teks snackbar disesuaikan:
                                snackbarHostState.showSnackbar("Pendaftaran ${info.namaJurusan} berhasil diproses!")
                                isLoading = false
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp), color = MaterialTheme.colorScheme.onPrimary, strokeWidth = 2.dp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Memproses...")
                    } else {

                        Text(if (isFullScreen) "Kembali" else "Daftar Jurusan Ini")
                    }
                }
            }
        }
        SnackbarHost(hostState = snackbarHostState, modifier = Modifier.align(Alignment.BottomCenter))
    }
}