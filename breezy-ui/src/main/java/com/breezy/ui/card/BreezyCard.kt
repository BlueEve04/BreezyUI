package com.breezy.ui.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.breezy.ui.theme.BreezyTheme
import com.breezy.ui.theme.BreezyColorScheme
import com.breezy.ui.theme.BreezyVisualStyle
import com.breezy.ui.theme.resolveVisualStyle
import com.breezy.ui.theme.BreezyPress3D
import com.breezy.ui.theme.LocalBreezyPress3DEnabled

// ═══════════════════════════════════════════
// BreezyCard — 卡片容器
// ═══════════════════════════════════════════

/**
 * 通用卡片组件，支持标题、副标题、图标、操作区等。
 *
 * @param modifier 修饰符
 * @param onClick 点击回调，设置后卡片可点击
 * @param header 头部 Composable
 * @param content 内容 Composable
 * @param footer 底部 Composable
 * @param elevated 是否带阴影（elevated）
 * @param shape 形状
 * @param padding 内边距
 * @param backgroundColor 背景色
 * @param colorOverride 覆盖颜色方案
 * @param visualStyle 表面样式
 * @param press3D 是否启用 3D 按下效果
 */
@Composable
fun BreezyCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    header: @Composable (() -> Unit)? = null,
    content: @Composable (() -> Unit)? = null,
    footer: @Composable (() -> Unit)? = null,
    elevated: Boolean = false,
    shape: Shape = BreezyTheme.shapes.medium,
    padding: PaddingValues = PaddingValues(16.dp),
    backgroundColor: Color = BreezyTheme.colors.surface,
    colorOverride: BreezyColorScheme? = null,
    visualStyle: BreezyVisualStyle? = null,
    press3D: Boolean = LocalBreezyPress3DEnabled.current
) {

    val contentBlock: @Composable () -> Unit = {
        Column(modifier = Modifier.padding(padding)) {
            header?.invoke()
            if (header != null && content != null) {
                Spacer(modifier = Modifier.height(12.dp))
            }
            content?.invoke()
            if (footer != null && (header != null || content != null)) {
                Spacer(modifier = Modifier.height(12.dp))
                footer.invoke()
            }
        }
    }

    if (onClick != null) {
        Card(
            onClick = onClick,
            modifier = modifier
                .fillMaxWidth()
                .BreezyPress3D(enabled = press3D),
            shape = shape,
            colors = CardDefaults.cardColors(
                containerColor = backgroundColor
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = if (elevated) 4.dp else 0.dp
            ),
            border = if (!elevated)
                androidx.compose.foundation.BorderStroke(1.dp, BreezyTheme.colors.outline.copy(alpha = 0.5f))
            else null
        ) {
            contentBlock()
        }
    } else {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .BreezyPress3D(enabled = false),
            shape = shape,
            colors = CardDefaults.cardColors(
                containerColor = backgroundColor
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = if (elevated) 4.dp else 0.dp
            ),
            border = if (!elevated)
                androidx.compose.foundation.BorderStroke(1.dp, BreezyTheme.colors.outline.copy(alpha = 0.5f))
            else null
        ) {
            contentBlock()
        }
    }
}

// ═══════════════════════════════════════════
// BreezyInfoCard — 信息展示卡片
// ═══════════════════════════════════════════

/**
 * 带有标题、描述和可选图标的快捷信息卡片。
 */

@Composable
fun BreezyInfoCard(
    title: String,
    description: String? = null,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    iconTint: Color = BreezyTheme.colors.primary,
    onClick: (() -> Unit)? = null,
    elevated: Boolean = false,
    colorOverride: BreezyColorScheme? = null,
    visualStyle: BreezyVisualStyle? = null,
    press3D: Boolean = LocalBreezyPress3DEnabled.current
) {
    BreezyCard(
        modifier = modifier,
        onClick = onClick,
        elevated = elevated,
        colorOverride = colorOverride,
        visualStyle = visualStyle,
        press3D = press3D,
        header = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                icon?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                        tint = iconTint,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                }
                Text(
                    text = title,
                    style = BreezyTheme.typography.titleMedium,
                    color = BreezyTheme.colors.onSurface
                )
            }
        },
        content = description?.let {
            {
                Text(
                    text = it,
                    style = BreezyTheme.typography.bodyMedium,
                    color = BreezyTheme.colors.onSurfaceVariant
                )
            }
        }
    )
}

// ═══════════════════════════════════════════
// BreezyStatCard — 数据统计卡片
// ═══════════════════════════════════════════

/**
 * 数据统计卡片，展示一个数值和标签。
 */
@Composable
fun BreezyStatCard(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    valueColor: Color = BreezyTheme.colors.primary,
    onClick: (() -> Unit)? = null,
    colorOverride: BreezyColorScheme? = null,
    visualStyle: BreezyVisualStyle? = null,
    press3D: Boolean = LocalBreezyPress3DEnabled.current
) {
    BreezyCard(
        modifier = modifier,
        onClick = onClick,
        elevated = true,
        colorOverride = colorOverride,
        visualStyle = visualStyle,
        press3D = press3D,
        content = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                icon?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                        tint = valueColor,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                Text(
                    text = value,
                    style = BreezyTheme.typography.headlineMedium,
                    color = valueColor
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = label,
                    style = BreezyTheme.typography.labelMedium,
                    color = BreezyTheme.colors.onSurfaceVariant
                )
            }
        }
    )
}
