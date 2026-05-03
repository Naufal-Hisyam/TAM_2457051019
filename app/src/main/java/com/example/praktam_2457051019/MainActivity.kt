package com.example.praktam_2457051019

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.praktam_2457051019.ui.theme.PraktamTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PraktamTheme {
                HaloCpuApp()
            }
        }
    }
}

data class HardwareData(
    val nama: String,
    val deskripsi: String,
    val imageUrl: String
)

@Composable
fun HaloCpuApp() {
    val snackbarHostState = remember { SnackbarHostState() }

    var hardwareList by remember { mutableStateOf<List<HardwareData>>(emptyList()) }
    var isScreenLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        try {
            delay(1500)
            hardwareList = listOf(
                HardwareData("CPU", "Tugasnya berpikir dan memerintah semua bagian lain.", "https://dummyimage.com/400x400/2196F3/ffffff&text=Gambar+CPU"),
                HardwareData("Memori RAM", "Ingatan jangka pendek supaya komputer tidak lemot!", "https://dummyimage.com/400x400/2196F3/ffffff&text=Gambar+RAM"),
                HardwareData("Layar Monitor", "Tempat kita melihat gambar dan game seru.", "https://dummyimage.com/400x400/2196F3/ffffff&text=Gambar+Monitor"),
                HardwareData("VGA", "Membuat gambar dan animasi game di layar jadi super bagus!", "https://dummyimage.com/400x400/2196F3/ffffff&text=Gambar+VGA"),
                HardwareData("Motherboard", "Papan besar tempat semua bagian komputer menempel dan bekerja sama.", "https://dummyimage.com/400x400/2196F3/ffffff&text=Gambar+Motherboard")
            )
            isScreenLoading = false
        } catch (e: Exception) {
            isError = true
            isScreenLoading = false
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (isScreenLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (isError || hardwareList.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Gagal Memuat Data", style = MaterialTheme.typography.titleLarge, color = Color.Red)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Pastikan koneksi internet Anda menyala", style = MaterialTheme.typography.bodyMedium, color = Color.Gray, textAlign = TextAlign.Center)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Halo CPU", color = MaterialTheme.colorScheme.onPrimary, style = MaterialTheme.typography.titleMedium)
                        }
                    }

                    Text("Komponen Populer", color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(bottom = 12.dp))

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        items(hardwareList.take(3)) { hardware ->
                            HardwareRowItem(hardware = hardware)
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    Text("Daftar Komponen Lengkap", color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(bottom = 8.dp))
                }

                items(hardwareList) { hardware ->
                    HardwareCard(hardware = hardware, snackbarHostState = snackbarHostState)
                }
            }
        }

        SnackbarHost(hostState = snackbarHostState, modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun HardwareRowItem(hardware: HardwareData) {
    Card(
        modifier = Modifier.width(160.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            AsyncImage(
                model = hardware.imageUrl,
                contentDescription = hardware.nama,
                placeholder = painterResource(id = R.drawable.cpu),
                error = painterResource(id = R.drawable.cpu),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(12.dp)) {
                Text(hardware.nama, style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.tertiary)
                Text("Klik untuk detail", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 4.dp))
            }
        }
    }
}

@Composable
fun HardwareCard(hardware: HardwareData, snackbarHostState: SnackbarHostState) {
    var isFavorite by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Box {
                AsyncImage(
                    model = hardware.imageUrl,
                    contentDescription = hardware.nama,
                    placeholder = painterResource(id = R.drawable.cpu),
                    error = painterResource(id = R.drawable.cpu),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )

                IconButton(
                    onClick = { isFavorite = !isFavorite },
                    modifier = Modifier.align(Alignment.TopEnd).size(32.dp)
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite Icon",
                        tint = if (isFavorite) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(hardware.nama, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.tertiary)
                Text(hardware.deskripsi, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 4.dp, bottom = 8.dp))

                Button(
                    onClick = {
                        coroutineScope.launch {
                            isLoading = true
                            delay(2000)
                            isLoading = false
                            snackbarHostState.showSnackbar("Detail ${hardware.nama} berhasil dibuka!")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                    modifier = Modifier.height(36.dp),
                    enabled = !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(16.dp), color = MaterialTheme.colorScheme.onPrimary, strokeWidth = 2.dp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Memproses...", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onPrimary)
                    } else {
                        Text("Pelajari", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            }
        }
    }
}