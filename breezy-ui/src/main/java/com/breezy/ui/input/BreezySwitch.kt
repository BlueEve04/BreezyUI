package com.breezy.ui.input

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.breezy.ui.theme.BreezyColorScheme
import com.breezy.ui.theme.BreezyTheme

/**
 * Breezy 开关组件。
 *
 * @param checked 开关状态
 * @param onCheckedChange 状态变更回调
 * @param modifier 修饰符
 * @param enabled 是否可用
 * @param colorOverride 覆盖颜色方案
 */
@Composable
fun BreezySwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colorOverride: BreezyColorScheme? = null
) {
    val colors = colorOverride ?: BreezyTheme.colorScheme

    val thumbColor by animateColorAsState(
        targetValue = if (checked) colors.primary else colors.onSurfaceVariant.copy(alpha = 0.5f),
        animationSpec = spring(),
        label = "switch_thumb"
    )
    val trackColor by animateColorAsState(
        targetValue = if (checked) colors.primary.copy(alpha = 0.4f) else colors.onSurface.copy(alpha = 0.12f),
        animationSpec = spring(),
        label = "switch_track"
    )

    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier.size(32.dp, 20.dp),
        enabled = enabled,
        colors = SwitchDefaults.colors(
            checkedThumbColor = colors.primary,
            checkedTrackColor = colors.primary.copy(alpha = 0.4f),
            uncheckedThumbColor = colors.onSurfaceVariant.copy(alpha = 0.5f),
            uncheckedTrackColor = colors.onSurface.copy(alpha = 0.12f),
            disabledCheckedThumbColor = colors.primary.copy(alpha = 0.38f),
            disabledCheckedTrackColor = colors.primary.copy(alpha = 0.12f),
            disabledUncheckedThumbColor = colors.onSurface.copy(alpha = 0.12f),
            disabledUncheckedTrackColor = colors.onSurface.copy(alpha = 0.06f)
        )
    )
}
