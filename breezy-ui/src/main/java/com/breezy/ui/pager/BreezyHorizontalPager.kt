package com.breezy.ui.pager

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import android.util.Log
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * 轻量水平翻页组件
 */
@Composable
fun BreezyHorizontalPager(
    pageCount: Int,
    modifier: Modifier = Modifier,
    initialPage: Int = 0,
    onPageChanged: (Int) -> Unit = {},
    pageContent: @Composable (Int) -> Unit
) {
    val scope = rememberCoroutineScope()
    var containerWidthPx by remember { mutableStateOf(10000f) }
    val offsetX = remember { Animatable(-initialPage * 10000f) } // 初始避开 page 0
    var currentPage by remember { mutableIntStateOf(initialPage) }

    // 宽度测量后，修正偏移到正确位置
    LaunchedEffect(containerWidthPx) {
        if (containerWidthPx in 1f..9999f) {
            offsetX.animateTo(-currentPage * containerWidthPx, tween(200))
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .onSizeChanged { size ->
                if (size.width > 0) {
                    Log.i("BreezyPager", "onSizeChanged: ${size.width}px")
                    containerWidthPx = size.width.toFloat()
                }
            }
            .pointerInput(Unit) {
                if (containerWidthPx >= 9999f) return@pointerInput // 真实宽度未就绪
                var dragStart = 0f
                detectHorizontalDragGestures(
                    onDragStart = { dragStart = offsetX.value },
                    onDragEnd = {
                        val page = (-offsetX.value / containerWidthPx).roundToInt()
                            .coerceIn(0, pageCount - 1)
                        val target = -page * containerWidthPx
                        scope.launch { offsetX.animateTo(target, tween(200)) }
                        if (page != currentPage) {
                            currentPage = page
                            onPageChanged(page)
                        }
                    },
                    onHorizontalDrag = { change, dragAmount ->
                        change.consume()
                        val maxOffset = -(pageCount - 1) * containerWidthPx
                        val newOffset = (dragStart + change.position.x - change.previousPosition.x)
                            .coerceIn(maxOffset, 0f)
                        scope.launch(Dispatchers.Main.immediate) {
                            offsetX.snapTo(newOffset)
                        }
                    }
                )
            }
    ) {
        for (i in 0 until pageCount) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .offset { IntOffset((offsetX.value + i * containerWidthPx).roundToInt(), 0) }
            ) {
                pageContent(i)
            }
        }
    }
}
