package com.breezy.ui.button

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.breezy.ui.theme.BreezyTheme
import com.breezy.ui.theme.BreezyColorScheme
import com.breezy.ui.theme.BreezyVisualStyle
import com.breezy.ui.theme.resolveVisualStyle
import com.breezy.ui.theme.BreezyPress3D
import com.breezy.ui.theme.LocalBreezyPress3DEnabled

// ═══════════════════════════════════════════
// 按钮样式枚举
// ═══════════════════════════════════════════

/**
 * 按钮视觉风格
 */
enum class BreezyButtonStyle {
    /** 实心主色按钮 */
    Primary,
    /** 次要按钮 */
    Secondary,
    /** 描边按钮 */
    Outlined,
    /** 纯文本按钮 */
    Text,
    /** 危险/警告按钮 */
    Danger
}

/**
 * 按钮尺寸
 */
enum class BreezyButtonSize(
    val height: Dp,
    val horizontalPadding: Dp,
    val iconSize: Dp
) {
    Small(height = 32.dp, horizontalPadding = 12.dp, iconSize = 16.dp),
    Medium(height = 40.dp, horizontalPadding = 16.dp, iconSize = 20.dp),
    Large(height = 48.dp, horizontalPadding = 24.dp, iconSize = 24.dp)
}

// ═══════════════════════════════════════════
// BreezyButton — 主按钮组件
// ═══════════════════════════════════════════

/**
 * 通用按钮组件。
 *
 * @param text 按钮文字
 * @param onClick 点击回调
 * @param modifier 修饰符
 * @param style 按钮风格，默认 Primary
 * @param size 按钮尺寸，默认 Medium
 * @param icon 前置图标
 * @param trailingIcon 后置图标（如箭头）
 * @param enabled 是否启用
 * @param loading 是否显示加载中
 * @param fullWidth 是否撑满宽度
 * @param shape 自定义形状
 * @param colorOverride 覆盖颜色方案（null 则使用主题色）
 * @param visualStyle 表面样式（null 则使用全局默认 Normal）
 * @param press3D 是否启用 3D 按下效果，默认 true
 */
@Composable
fun BreezyButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: BreezyButtonStyle = BreezyButtonStyle.Primary,
    size: BreezyButtonSize = BreezyButtonSize.Medium,
    icon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    enabled: Boolean = true,
    loading: Boolean = false,
    fullWidth: Boolean = false,
    shape: Shape = BreezyTheme.shapes.medium,
    colorOverride: BreezyColorScheme? = null,
    visualStyle: BreezyVisualStyle? = null,
    press3D: Boolean = LocalBreezyPress3DEnabled.current
) {
    val colors = colorOverride ?: BreezyTheme.colorScheme
    val resolvedVisualStyle = resolveVisualStyle(visualStyle)

    // 根据风格确定颜色
    val containerColor: Color
    val contentColor: Color
    val border: BorderStroke?

    when (style) {
        BreezyButtonStyle.Primary -> {
            containerColor = if (enabled) colors.primary else colors.onSurface.copy(alpha = 0.12f)
            contentColor = if (enabled) colors.onPrimary else colors.onSurface.copy(alpha = 0.38f)
            border = null
        }
        BreezyButtonStyle.Secondary -> {
            containerColor = if (enabled) colors.secondary else colors.onSecondary.copy(alpha = 0.12f)
            contentColor = if (enabled) colors.onSecondary else colors.onSurface.copy(alpha = 0.38f)
            border = null
        }
        BreezyButtonStyle.Outlined -> {
            containerColor = Color.Transparent
            contentColor = if (enabled) colors.primary else colors.onSurface.copy(alpha = 0.38f)
            border = BorderStroke(1.dp, if (enabled) colors.outline else colors.onSurface.copy(alpha = 0.12f))
        }
        BreezyButtonStyle.Text -> {
            containerColor = Color.Transparent
            contentColor = if (enabled) colors.primary else colors.onSurface.copy(alpha = 0.38f)
            border = null
        }
        BreezyButtonStyle.Danger -> {
            containerColor = if (enabled) colors.error else colors.onSurface.copy(alpha = 0.12f)
            contentColor = if (enabled) colors.onError else colors.onSurface.copy(alpha = 0.38f)
            border = null
        }
    }

    Button(
        onClick = { if (enabled && !loading) onClick() },
        modifier = modifier
            .then(if (fullWidth) Modifier.fillMaxWidth() else Modifier)
            .animateContentSize()
            .BreezyPress3D(enabled = press3D && enabled && !loading),
        enabled = enabled && !loading,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = containerColor,
            disabledContentColor = contentColor
        ),
        border = border,
        contentPadding = PaddingValues(
            horizontal = size.horizontalPadding,
            vertical = 0.dp
        )
    ) {
        Box(
            modifier = Modifier.height(size.height),
            contentAlignment = Alignment.Center
        ) {
            if (loading) {
                // 加载状态
                CircularProgressIndicator(
                    modifier = Modifier.size(size.iconSize),
                    color = contentColor,
                    strokeWidth = 2.dp
                )
            } else {
                // 正常状态
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    icon?.let {
                        Icon(
                            imageVector = it,
                            contentDescription = null,
                            modifier = Modifier.size(size.iconSize)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                    }

                    Text(
                        text = text,
                        style = when (size) {
                            BreezyButtonSize.Small -> BreezyTheme.typography.labelMedium
                            BreezyButtonSize.Medium -> BreezyTheme.typography.labelLarge
                            BreezyButtonSize.Large -> BreezyTheme.typography.titleSmall
                        }
                    )

                    trailingIcon?.let {
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = it,
                            contentDescription = null,
                            modifier = Modifier.size(size.iconSize)
                        )
                    }
                }
            }
        }
    }
}

// ═══════════════════════════════════════════
// BreezyIconButton — 图标按钮
// ═══════════════════════════════════════════

/**
 * 纯图标按钮。
 *
 * @param colorOverride 覆盖颜色方案
 * @param visualStyle 表面样式
 * @param press3D 是否启用 3D 按下效果
 */
@Composable
fun BreezyIconButton(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: BreezyButtonStyle = BreezyButtonStyle.Primary,
    size: BreezyButtonSize = BreezyButtonSize.Medium,
    enabled: Boolean = true,
    contentDescription: String? = null,
    colorOverride: BreezyColorScheme? = null,
    visualStyle: BreezyVisualStyle? = null,
    press3D: Boolean = LocalBreezyPress3DEnabled.current
) {
    val colors = colorOverride ?: BreezyTheme.colorScheme
    val resolvedVisualStyle = resolveVisualStyle(visualStyle)
    // 根据 visualStyle 调整透明度
    val alphaMod = when (resolvedVisualStyle) {
        BreezyVisualStyle.SemiTransparent -> 0.7f
        BreezyVisualStyle.FrostedGlass -> 0.25f
        BreezyVisualStyle.Normal -> 1f
    }

    val containerColor: Color
    val iconColor: Color

    when (style) {
        BreezyButtonStyle.Primary -> {
            containerColor = if (enabled) colors.primary else colors.onSurface.copy(alpha = 0.12f)
            iconColor = if (enabled) colors.onPrimary else colors.onSurface.copy(alpha = 0.38f)
        }
        BreezyButtonStyle.Secondary -> {
            containerColor = if (enabled) colors.secondary else colors.onSurface.copy(alpha = 0.12f)
            iconColor = if (enabled) colors.onSecondary else colors.onSurface.copy(alpha = 0.38f)
        }
        BreezyButtonStyle.Outlined -> {
            containerColor = Color.Transparent
            iconColor = if (enabled) colors.primary else colors.onSurface.copy(alpha = 0.38f)
        }
        BreezyButtonStyle.Text -> {
            containerColor = Color.Transparent
            iconColor = if (enabled) colors.onSurfaceVariant else colors.onSurface.copy(alpha = 0.38f)
        }
        BreezyButtonStyle.Danger -> {
            containerColor = if (enabled) colors.error else colors.onSurface.copy(alpha = 0.12f)
            iconColor = if (enabled) colors.onError else colors.onSurface.copy(alpha = 0.38f)
        }
    }

    val btnSize = size.height

    IconButton(
        onClick = { if (enabled) onClick() },
        modifier = modifier
            .size(btnSize)
            .BreezyPress3D(enabled = press3D && enabled),
        enabled = enabled,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = containerColor,
            contentColor = iconColor
        )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.size(size.iconSize)
        )
    }
}
