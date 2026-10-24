package com.breezy.ui.theme

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

// ═══════════════════════════════════════════
// BreezyColorScheme — 可动态修改的颜色方案
// ═══════════════════════════════════════════

/**
 * 完整的 Breezy 颜色方案，可在运行时动态修改。
 *
 * 使用方式：
 * - 静态设置：`BreezyTheme(colorScheme = BreezyColorScheme.defaultLight())`
 * - 动态修改：通过 Compose state 持有 [BreezyColorScheme] 实例并传入 [BreezyTheme]
 */
data class BreezyColorScheme(
    val primary: Color,
    val onPrimary: Color,
    val primaryContainer: Color,
    val secondary: Color,
    val onSecondary: Color,
    val secondaryContainer: Color,
    val background: Color,
    val surface: Color,
    val onBackground: Color,
    val onSurface: Color,
    val onSurfaceVariant: Color,
    val outline: Color,
    val error: Color,
    val onError: Color,
    // 语义色扩展
    val success: Color = Color(0xFF10B981),
    val warning: Color = Color(0xFFF59E0B),
    val info: Color = Color(0xFF3B82F6),
    // 品牌色保留（用于特殊场合）
    val brandPrimary: Color = primary,
    val brandSecondary: Color = secondary,
) {
    companion object {
        // ── 浅色主题默认 ──
        fun defaultLight() = BreezyColorScheme(
            primary = Color(0xFF4F46E5),
            onPrimary = Color.White,
            primaryContainer = Color(0xFF818CF8),
            secondary = Color(0xFF06B6D4),
            onSecondary = Color.White,
            secondaryContainer = Color(0xFF67E8F9),
            background = Color(0xFFF8FAFC),
            surface = Color.White,
            onBackground = Color(0xFF1E293B),
            onSurface = Color(0xFF1E293B),
            onSurfaceVariant = Color(0xFF64748B),
            outline = Color(0xFFE2E8F0),
            error = Color(0xFFEF4444),
            onError = Color.White,
        )

        // ── 深色主题默认 ──
        fun defaultDark() = BreezyColorScheme(
            primary = Color(0xFF818CF8),
            onPrimary = Color.White,
            primaryContainer = Color(0xFF3730A3),
            secondary = Color(0xFF67E8F9),
            onSecondary = Color.Black,
            secondaryContainer = Color(0xFF0891B2),
            background = Color(0xFF0F172A),
            surface = Color(0xFF1E293B),
            onBackground = Color(0xFFF1F5F9),
            onSurface = Color(0xFFF1F5F9),
            onSurfaceVariant = Color(0xFF94A3B8),
            outline = Color(0xFF334155),
            error = Color(0xFFEF4444),
            onError = Color.White,
        )
    }
}

// ═══════════════════════════════════════════
// CompositionLocal — 组件树中访问当前颜色方案
// ═══════════════════════════════════════════

val LocalBreezyColorScheme = staticCompositionLocalOf {
    BreezyColorScheme.defaultLight()
}

// ═══════════════════════════════════════════
// 工具：将 BreezyColorScheme 转为 Material3 ColorScheme
// ═══════════════════════════════════════════

fun BreezyColorScheme.toMaterial3ColorScheme(
    darkTheme: Boolean = false
): androidx.compose.material3.ColorScheme {
    return if (darkTheme) {
        androidx.compose.material3.darkColorScheme(
            primary = primary,
            onPrimary = onPrimary,
            primaryContainer = primaryContainer,
            secondary = secondary,
            onSecondary = onSecondary,
            secondaryContainer = secondaryContainer,
            background = background,
            surface = surface,
            onBackground = onBackground,
            onSurface = onSurface,
            onSurfaceVariant = onSurfaceVariant,
            outline = outline,
            error = error,
            onError = onError,
        )
    } else {
        androidx.compose.material3.lightColorScheme(
            primary = primary,
            onPrimary = onPrimary,
            primaryContainer = primaryContainer,
            secondary = secondary,
            onSecondary = onSecondary,
            secondaryContainer = secondaryContainer,
            background = background,
            surface = surface,
            onBackground = onBackground,
            onSurface = onSurface,
            onSurfaceVariant = onSurfaceVariant,
            outline = outline,
            error = error,
            onError = onError,
        )
    }
}
