package com.example.memeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.memeapp.ui.theme.MemeAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MemeAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MemeViewerInstagramStyle()
                }
            }
        }
    }
}

@Composable
fun MemeViewerInstagramStyle(modifier: Modifier = Modifier) { // Removed fillMaxSize default here to be more flexible if used elsewhere
    data class Meme(val imageRes: Int, val username: String, val caption: String)

    val memes = listOf(
        Meme(
            R.drawable.whatsapp_image_2025_08_09_at_5_05_17_pm,
            username = "code_master",
            caption = "#kotlin #python #java"
        ),
        Meme(
            R.drawable.whatsapp_image_2025_08_09_at_5_05_38_pm,
            username = "physics_nerd",
            caption = "Heaviest objects in the universe!"
        ),
        Meme(
            R.drawable.whatsapp_image_2025_08_09_at_5_06_21_pm,
            username = "dev_life",
            caption = "When Android Studio just won't build..."
        ),
        Meme(
            R.drawable.whatsapp_image_2025_08_09_at_5_09_25_pm,
            username = "dev_life",
            caption = "When Android Studio just won't build..."
        ),
        Meme(
            R.drawable.whatsapp_image_2025_08_10_at_12_48_04_am,
            username = "dev_life",
            caption = "When Android Studio just won't build..."
        ),
        Meme(
            R.drawable.whatsapp_image_2025_08_10_at_12_48_40_am,
            username = "dev_life",
            caption = "When Android Studio just won't build..."
        ),
        Meme(
            R.drawable.whatsapp_image_2025_08_10_at_12_52_10_am,
            username = "dev_life",
            caption = "When Android Studio just won't build..."
        ),
        Meme(
            R.drawable.whatsapp_image_2025_08_10_at_12_53_24_am,
            username = "dev_life",
            caption = "When Android Studio just won't build..."
        )
    )

    var currentIndex by remember { mutableIntStateOf(0) }
    var liked by remember { mutableStateOf(false) }
    var showHeart by remember { mutableStateOf(false) }
    var imageWidth by remember { mutableIntStateOf(0) }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.Center)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .onSizeChanged { size ->
                        imageWidth = size.width
                    }
                    .pointerInput(currentIndex, imageWidth) {
                        detectTapGestures(
                            onDoubleTap = {
                                liked = !liked
                                showHeart = true
                                coroutineScope.launch {
                                    delay(700)
                                    showHeart = false
                                }
                            },
                            onTap = { offset ->
                                if (imageWidth == 0) return@detectTapGestures

                                if (offset.x > imageWidth / 2) {
                                    currentIndex = (currentIndex + 1) % memes.size
                                    liked = false // Reset like status on new meme
                                } else {
                                    currentIndex = (currentIndex - 1 + memes.size) % memes.size
                                    liked = false
                                }
                            }
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = memes[currentIndex].imageRes),
                    contentDescription = "Meme Image",
                    modifier = Modifier.fillMaxSize()
                )


                androidx.compose.animation.AnimatedVisibility(
                    visible = showHeart,
                    enter = fadeIn(),
                    exit = fadeOut(),
                    modifier = Modifier.align(Alignment.Center),
                    label = "HeartVisibility"
                ) {

                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Liked Heart",
                        tint = Color.Red,
                        modifier = Modifier.size(100.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = memes[currentIndex].username,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = memes[currentIndex].caption,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = if (liked) "1 like" else "0 likes",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}




