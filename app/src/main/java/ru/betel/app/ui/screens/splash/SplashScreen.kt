package ru.betel.app.ui.screens.splash

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import ru.betel.app.R

/*@Composable
fun SplashScreen(uri: Uri) {
    Surface(modifier = Modifier.fillMaxSize()) {
        VideoSplashScreen(uri)
    }
}


@Composable
fun VideoSplashScreen(videoUri: Uri) {
    val context = LocalContext.current
    val exoPlayer = remember { context.buildExoPlayer(videoUri) }

    DisposableEffect(
        AndroidView(
            factory = { it.buildPlayerView(exoPlayer) }, modifier = Modifier.fillMaxSize()
        )
    ) {
        onDispose {
            exoPlayer.release()
        }
    }
}

private fun Context.buildExoPlayer(uri: Uri) = ExoPlayer.Builder(this).build().apply {
    setMediaItem(MediaItem.fromUri(uri))
    this.isDeviceMuted = true
    repeatMode = Player.REPEAT_MODE_ALL
    playWhenReady = true
    prepare()
}

private fun Context.buildPlayerView(exoPlayer: ExoPlayer) = StyledPlayerView(this).apply {
    player = exoPlayer
    layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
    useController = false
    resizeMode = RESIZE_MODE_ZOOM
}*/


@Composable
fun SplashScreen() {
    var isVisible by remember {
        mutableStateOf(
            false
        )
    }

    LaunchedEffect(Unit) {
        delay(500) // 1 second delay for example
        isVisible = true
    }

    val fadeInAlpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
    )

    val offsetY by animateDpAsState(
        targetValue = if (isVisible) 0.dp else (-200).dp,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.des_1),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(225.dp)
                .offset(y = offsetY)
                .alpha(fadeInAlpha)
        )
    }
}

@Preview
@Composable
private fun Splash() {
    SplashScreen()
}
