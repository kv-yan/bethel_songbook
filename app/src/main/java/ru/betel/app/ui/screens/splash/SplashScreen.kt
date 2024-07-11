package ru.betel.app.ui.screens.splash

import android.content.Context
import android.net.Uri
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import android.window.SplashScreen
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout.RESIZE_MODE_ZOOM
import com.google.android.exoplayer2.ui.StyledPlayerView
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
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(1000) // 1 second delay for example
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
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_splash),
            contentDescription = null,
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
