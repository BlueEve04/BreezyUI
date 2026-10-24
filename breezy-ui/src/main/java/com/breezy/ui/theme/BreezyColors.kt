package com.breezy.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

// ═══════════════════════════════════════════
// 品牌色定义（静态常量，向后兼容）
// ═══════════════════════════════════════════

object BreezyColors {
    val Primary = BreezyColorScheme.defaultLight().primary
    val PrimaryLight = Color(0xFF818CF8)
    val PrimaryDark = Color(0xFF3730A3)
    val Secondary = BreezyColorScheme.defaultLight().secondary
    val SecondaryLight = Color(0xFF67E8F9)
    val SecondaryDark = Color(0xFF0891B2)
    val Success = BreezyColorScheme.defaultLight().success
    val Warning = BreezyColorScheme.defaultLight().warning
    val Error = BreezyColorScheme.defaultLight().error
    val Info = BreezyColorScheme.defaultLight().info
    val BackgroundLight = BreezyColorScheme.defaultLight().background
    val SurfaceLight = BreezyColorScheme.defaultLight().surface
    val OnSurfaceLight = BreezyColorScheme.defaultLight().onSurface
    val OnSurfaceVariantLight = BreezyColorScheme.defaultLight().onSurfaceVariant
    val OutlineLight = BreezyColorScheme.defaultLight().outline
    val BackgroundDark = BreezyColorScheme.defaultDark().background
    val SurfaceDark = BreezyColorScheme.defaultDark().surface
    val OnSurfaceDark = BreezyColorScheme.defaultDark().onSurface
    val OnSurfaceVariantDark = BreezyColorScheme.defaultDark().onSurfaceVariant
    val OutlineDark = BreezyColorScheme.defaultDark().outline
}

// ═══════════════════════════════════════════
// BreezyTheme — 可接受的参数扩展
// ═══════════════════════════════════════════

/**
 * BreezyUI 主题入口。
 *
 * @param darkTheme 是否暗色模式，默认跟随系统
 * @param colorSchemeOverride 可选的自定义颜色方案（支持动态修改）
 * @param content 内容
 */
@Composable
fun BreezyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    colorSchemeOverride: BreezyColorScheme? = null,
    content: @Composable () -> Unit
) {
    val scheme = colorSchemeOverride ?: if (darkTheme) {
        BreezyColorScheme.defaultDark()
    } else {
        BreezyColorScheme.defaultLight()
    }

    CompositionLocalProvider(
        LocalBreezyColorScheme provides scheme
    ) {
        MaterialTheme(
            colorScheme = scheme.toMaterial3ColorScheme(darkTheme),
            typography = BreezyTypography,
            shapes = BreezyShapes,
            content = content
        )
    }
}

// ═══════════════════════════════════════════
// 便捷访问当前主题
// ═══════════════════════════════════════════

object BreezyTheme {
    /** 当前自定义颜色方案 */
    val colorScheme: BreezyColorScheme
        @Composable get() = LocalBreezyColorScheme.current

    /** 当前 MaterialTheme 颜色 */
    val colors: ColorScheme
        @Composable get() = MaterialTheme.colorScheme

    val typography: Typography
        @Composable get() = MaterialTheme.typography

    val shapes: Shapes
        @Composable get() = MaterialTheme.shapes
}

