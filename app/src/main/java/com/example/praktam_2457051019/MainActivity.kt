package com.example.praktam_2457051019

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HaloCpuApp()
        }
    }
}

data class HardwareData(
    val nama: String,
    val deskripsi: String,
    val gambarResId: Int
)

@Composable
fun HaloCpuApp() {
    val hardwareList = listOf(
        HardwareData("CPU", "Tugasnya berpikir dan memerintah semua bagian lain.", R.drawable.cpu),
        HardwareData("Memori RAM", "Ingatan jangka pendek supaya komputer tidak lemot!", R.drawable.ram),
        HardwareData("Layar Monitor", "Tempat kita melihat gambar dan game seru.", R.drawable.monitor),
        HardwareData("VGA", "Membuat gambar dan animasi game di layar jadi super bagus!", R.drawable.graphic_card),
        HardwareData("Motherboard", "Papan besar tempat semua bagian komputer menempel dan bekerja sama.", R.drawable.motherboard)
    )

    // 1. Mengubah Column + verticalScroll menjadi LazyColumn (Modul 6)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F4F8)),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp) // Memberikan jarak otomatis antar item
    ) {

        // 2. Menggunakan scope function 'item' untuk membungkus header dan LazyRow (Modul 6)
        item {
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF2196F3))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Halo CPU", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                }
            }

            Text(
                text = "Komponen Populer",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF333333),
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // 3. Menambahkan LazyRow untuk tampilan menyamping (Modul 6)
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Menampilkan 3 data pertama saja untuk LazyRow
                items(hardwareList.take(3)) { hardware ->
                    HardwareRowItem(hardware = hardware)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Daftar Komponen Lengkap",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF333333),
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // 4. Menggunakan scope function 'items' untuk me-render list keseluruhan (Modul 6)
        items(hardwareList) { hardware ->
            HardwareCard(hardware = hardware)
        }
    }
}

// 5. Membuat Fungsi Composable baru untuk item di LazyRow (Modul 6)
@Composable
fun HardwareRowItem(hardware: HardwareData) {
    Card(
        modifier = Modifier.width(160.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            Image(
                painter = painterResource(id = hardware.gambarResId),
                contentDescription = hardware.nama,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color(0xFFE3F2FD))
                    .padding(16.dp),
                contentScale = ContentScale.Fit
            )
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = hardware.nama,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1565C0)
                )
                Text(
                    text = "Klik untuk detail",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

// Composable HardwareCard yang sebelumnya (digunakan untuk LazyColumn)
@Composable
fun HardwareCard(hardware: HardwareData) {
    var isFavorite by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                Image(
                    painter = painterResource(id = hardware.gambarResId),
                    contentDescription = hardware.nama,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFE3F2FD))
                        .padding(8.dp)
                )

                IconButton(
                    onClick = { isFavorite = !isFavorite },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(32.dp)
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite Icon",
                        tint = if (isFavorite) Color.Red else Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = hardware.nama,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF1565C0)
                )
                Text(
                    text = hardware.deskripsi,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
                )

                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)),
                    modifier = Modifier.height(36.dp)
                ) {
                    Text(text = "Pelajari", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}