package com.breezy.ui.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.breezy.ui.theme.BreezyTheme

// ═══════════════════════════════════════════
// 文本层级枚举
// ═══════════════════════════════════════════

enum class BreezyTextLevel {
    /** 超大标题 36sp */
    DisplayLarge,
    /** 大标题 28sp */
    DisplayMedium,
    /** 小标题 24sp */
    DisplaySmall,
    /** 标题1 22sp */
    H1,
    /** 标题2 18sp */
    H2,
    /** 标题3 16sp */
    H3,
    /** 正文大 16sp */
    BodyLarge,
    /** 正文中 14sp */
    BodyMedium,
    /** 正文小 12sp */
    BodySmall,
    /** 标签/说明 12sp Medium */
    Label,
    /** 小标注 10sp */
    Caption
}

// ═══════════════════════════════════════════
// BreezyText — 统一文本组件
// ═══════════════════════════════════════════

/**
 * 提供统一的文本样式层级，便于在整个 App 中保持一致的排版。
 *
 * @param text 文本内容
 * @param modifier 修饰符
 * @param level 文本层级
 * @param color 自定义颜色，默认跟随主题
 * @param align 文本对齐方式
 * @param maxLines 最大行数
 * @param overflow 溢出处理
 */
@Composable
fun BreezyText(
    text: String,
    modifier: Modifier = Modifier,
    level: BreezyTextLevel = BreezyTextLevel.BodyMedium,
    color: Color = Color.Unspecified,
    align: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis
) {
    val resolvedColor = if (color == Color.Unspecified) BreezyTheme.colors.onSurface else color
    val style = when (level) {
        BreezyTextLevel.DisplayLarge -> BreezyTheme.typography.displayLarge
        BreezyTextLevel.DisplayMedium -> BreezyTheme.typography.displayMedium
        BreezyTextLevel.DisplaySmall -> BreezyTheme.typography.displaySmall
        BreezyTextLevel.H1 -> BreezyTheme.typography.headlineLarge
        BreezyTextLevel.H2 -> BreezyTheme.typography.headlineMedium
        BreezyTextLevel.H3 -> BreezyTheme.typography.headlineSmall
        BreezyTextLevel.BodyLarge -> BreezyTheme.typography.bodyLarge
        BreezyTextLevel.BodyMedium -> BreezyTheme.typography.bodyMedium
        BreezyTextLevel.BodySmall -> BreezyTheme.typography.bodySmall
        BreezyTextLevel.Label -> BreezyTheme.typography.labelMedium
        BreezyTextLevel.Caption -> BreezyTheme.typography.labelSmall
    }

    Text(
        text = text,
        modifier = modifier,
        style = style,
        color = resolvedColor,
        textAlign = align,
        maxLines = maxLines,
        overflow = overflow
    )
}

// ═══════════════════════════════════════════
// 扩展工具函数
// ═══════════════════════════════════════════

/**
 * 自定义样式的文本组件，适用于特殊场景。
 */
@Composable
fun BreezyCustomText(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight = FontWeight.Normal,
    color: Color = BreezyTheme.colors.onSurface,
    align: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis
) {
    Text(
        text = text,
        modifier = modifier,
        style = TextStyle(
            fontSize = fontSize,
            fontWeight = fontWeight
        ),
        color = color,
        textAlign = align,
        maxLines = maxLines,
        overflow = overflow
    )
}
