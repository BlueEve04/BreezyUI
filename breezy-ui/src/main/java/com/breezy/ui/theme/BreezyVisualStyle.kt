package com.breezy.ui.theme

import androidx.compose.foundation.background
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// ═══════════════════════════════════════════
// BreezyVisualStyle — 控件表面样式
// ═══════════════════════════════════════════

/**
 * 控件的表面渲染样式。
 *
 * - **Normal**: 直接着色（默认行为，不透明）
 * - **SemiTransparent**: 半透明(70% 不透明度)
 * - **FrostedGlass**: 磨砂玻璃效果（模糊 + 半透明）
 */
enum class BreezyVisualStyle {
    Normal,
    SemiTransparent,
    FrostedGlass
}

// ═══════════════════════════════════════════
// CompositionLocal — 全局默认视觉样式
// ═══════════════════════════════════════════

val LocalBreezyVisualStyle = compositionLocalOf { BreezyVisualStyle.Normal }

// ═══════════════════════════════════════════
// Modifier 扩展：根据视觉样式应用效果
// ═══════════════════════════════════════════

/**
 * 根据 [BreezyVisualStyle] 为控件表面应用对应的视觉效果。
 *
 * @param style 视觉样式，默认从 [LocalBreezyVisualStyle] 获取
 * @param backgroundColor 控件背景色
 * @param shape 控件形状
 * @param blurRadius 磨砂模糊半径（仅 [BreezyVisualStyle.FrostedGlass] 生效）
 */
@Composable
fun Modifier.BreezySurfaceStyle(
    style: BreezyVisualStyle = LocalBreezyVisualStyle.current,
    backgroundColor: Color = Color.Transparent,
    shape: Shape? = null,
    blurRadius: Dp = 12.dp
): Modifier {
    return when (style) {
        BreezyVisualStyle.Normal -> {
            this.then(
                if (backgroundColor != Color.Transparent) {
                    Modifier.background(backgroundColor, shape ?: androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
                } else Modifier
            )
        }
        BreezyVisualStyle.SemiTransparent -> {
            val bg = backgroundColor.copy(alpha = 0.7f)
            this.then(
                Modifier.background(bg, shape ?: androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
            )
        }
        BreezyVisualStyle.FrostedGlass -> {
            val bg = backgroundColor.copy(alpha = 0.25f)
            this.then(
                Modifier
                    .blur(blurRadius)
                    .then(
                        if (shape != null) Modifier.clip(shape) else Modifier
                    )
                    .background(bg)
            )
        }
    }
}

/**
 * 便捷函数：解析组件级别的视觉样式参数。
 * 若 [override] 不为 null 则使用 [override]，否则从 CompositionLocal 获取默认值。
 */
@Composable
fun resolveVisualStyle(override: BreezyVisualStyle?): BreezyVisualStyle {
    val local = LocalBreezyVisualStyle.current
    return override ?: local
}

/**
 * 便捷函数：获取半透明版本的颜色。
 */
fun Color.semiTransparent(): Color = this.copy(alpha = 0.7f)
fun Color.frostedGlass(): Color = this.copy(alpha = 0.25f)
