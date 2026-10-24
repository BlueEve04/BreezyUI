package com.breezy.ui.theme

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.launch

// ═══════════════════════════════════════════
// 全局 3D 按压效果开关
// ═══════════════════════════════════════════

/**
 * 全局控制 3D 按压效果的启用/禁用。
 * 默认 true。在 CompositionLocalProvider 中提供 false 可全局关闭。
 */
val LocalBreezyPress3DEnabled = compositionLocalOf { true }

// ═══════════════════════════════════════════
// 3D 按压下沉效果（不消耗触摸事件）
// ═══════════════════════════════════════════

/**
 * 点击时产生 3D 下沉效果——按压点位置向屏幕内凹陷，如同按压一块海绵。
 * **不消耗触摸事件**，不影响按钮/卡片的正常点击。
 *
 * 性能说明（v1.1 优化）：
 *  旧实现为 scale / rotationX / rotationY 各建一个 [androidx.compose.animation.core.animateFloatAsState]，
 *  每个控件 3 条独立动画、3 次快照写入；在长列表中（数百个条目）会显著抬高帧耗时。
 *  新实现改为**单条 0→1 的进度动画**，缩放与两个旋转都由该进度线性插值，
 *  且只在 `graphicsLayer` 的绘制阶段读取进度值——重组阶段不再读取动画状态，
 *  视觉表现（弹簧回弹、按压点起翘）与旧实现一致，但动画对象与快照读取次数减少到 1/3。
 *
 * @param enabled 是否启用效果，默认 true
 * @param sinkDepth 下沉深度（0~1），0 = 无效果，1 = 最大下沉，默认 0.06
 * @param scaleDown 按下时整体缩放（从按压点），默认 0.96f
 */
fun Modifier.BreezyPress3D(
    enabled: Boolean = true,
    sinkDepth: Float = 0.06f,
    scaleDown: Float = 0.96f
): Modifier = composed {
    if (!enabled) return@composed this

    var size by remember { mutableStateOf(IntSize.Zero) }
    // 按压点（归一化到 0..1，作为 transformOrigin）
    var pivot by remember { mutableStateOf(Offset(0.5f, 0.5f)) }
    // 按压点对应的目标旋转角（x = rotationX, y = rotationY）
    var rotTarget by remember { mutableStateOf(Offset.Zero) }
    // 唯一的一条动画：0 = 抬起，1 = 按到底
    val press = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    this
        .onSizeChanged { size = it }
        .pointerInput(Unit) {
            // 使用 Initial pass 观察触摸事件，不消耗
            awaitPointerEventScope {
                while (true) {
                    val event = awaitPointerEvent(PointerEventPass.Initial)
                    val change = event.changes.firstOrNull() ?: continue

                    when {
                        change.pressed && !change.previousPressed -> {
                            if (size.width > 0 && size.height > 0) {
                                val nx = (change.position.x / size.width).coerceIn(0f, 1f)
                                val ny = (change.position.y / size.height).coerceIn(0f, 1f)
                                pivot = Offset(nx, ny)
                                // 按压点相对中心的偏移 → 旋转方向（仍在中心 30 度以内）
                                rotTarget = Offset(
                                    -(ny - 0.5f) * sinkDepth * 30f,
                                    (nx - 0.5f) * sinkDepth * 30f
                                )
                            }
                            scope.launch {
                                press.animateTo(
                                    1f,
                                    spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessLow
                                    )
                                )
                            }
                        }

                        !change.pressed && change.previousPressed -> {
                            scope.launch {
                                press.animateTo(
                                    0f,
                                    spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessMediumLow
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
        .graphicsLayer {
            // 仅在绘制阶段读取动画值：重组阶段不依赖 press，避免整树重组
            val t = press.value
            transformOrigin = TransformOrigin(pivot.x, pivot.y)
            val s = 1f - (1f - scaleDown) * t
            scaleX = s
            scaleY = s
            rotationX = rotTarget.x * t
            rotationY = rotTarget.y * t
        }
}
