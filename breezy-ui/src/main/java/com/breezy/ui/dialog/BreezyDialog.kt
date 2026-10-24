package com.breezy.ui.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.breezy.ui.button.BreezyButton
import com.breezy.ui.button.BreezyButtonStyle
import com.breezy.ui.theme.BreezyTheme

// ═══════════════════════════════════════════
// BreezyDialog — 标准弹窗
// ═══════════════════════════════════════════

/**
 * 标准确认弹窗，带有标题、内容、确认与取消按钮。
 *
 * @param show 是否显示
 * @param onDismiss 关闭回调
 * @param title 标题
 * @param text 正文内容
 * @param confirmText 确认按钮文字
 * @param cancelText 取消按钮文字（null 则隐藏）
 * @param onConfirm 确认回调
 * @param icon 顶部图标
 * @param iconTint 图标颜色
 */
@Composable
fun BreezyDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    title: String,
    text: String,
    confirmText: String = "确认",
    cancelText: String? = "取消",
    onConfirm: () -> Unit = {},
    icon: ImageVector? = null,
    iconTint: Color = BreezyTheme.colors.primary
) {
    if (show) {
        AlertDialog(
            onDismissRequest = onDismiss,
            shape = BreezyTheme.shapes.large,
            containerColor = BreezyTheme.colors.surface,
            icon = icon?.let {
                { Icon(imageVector = it, contentDescription = null, tint = iconTint) }
            },
            title = {
                Text(
                    text = title,
                    style = BreezyTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            text = {
                Text(
                    text = text,
                    style = BreezyTheme.typography.bodyMedium,
                    color = BreezyTheme.colors.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                BreezyButton(
                    text = confirmText,
                    onClick = {
                        onConfirm()
                        onDismiss()
                    },
                    fullWidth = cancelText == null
                )
            },
            dismissButton = cancelText?.let { text ->
                {
                    BreezyButton(
                        text = text,
                        onClick = onDismiss,
                        style = BreezyButtonStyle.Outlined
                    )
                }
            }
        )
    }
}

// ═══════════════════════════════════════════
// BreezyBottomSheet — 底部弹窗
// ═══════════════════════════════════════════

/**
 * 底部弹出面板，适用于选项列表等场景。
 *
 * @param show 是否显示
 * @param onDismiss 关闭
 * @param title 可选标题
 * @param content 自定义内容
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreezyBottomSheet(
    show: Boolean,
    onDismiss: () -> Unit,
    title: String? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    if (show) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            containerColor = BreezyTheme.colors.surface,
            dragHandle = { BottomSheetDefaults.DragHandle() }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 32.dp)
            ) {
                title?.let {
                    Text(
                        text = it,
                        style = BreezyTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
                content()
            }
        }
    }
}

// ═══════════════════════════════════════════
// BreezyToast — Toast 提示
// ═══════════════════════════════════════════

enum class BreezyToastType {
    Success, Error, Warning, Info
}

/**
 * 应用内 Snackbar / Toast 风格的简短提示弹窗。
 * 由 [show] 控制显示，自动在 [durationMs] 毫秒后关闭。
 */
@Composable
fun BreezyToast(
    show: Boolean,
    message: String,
    type: BreezyToastType = BreezyToastType.Info,
    onDismiss: () -> Unit,
    durationMs: Long = 3000L
) {
    if (show) {
        LaunchedEffect(show) {
            kotlinx.coroutines.delay(durationMs)
            onDismiss()
        }

        val (icon, tint) = when (type) {
            BreezyToastType.Success -> Icons.Default.CheckCircle to BreezyTheme.colors.primary
            BreezyToastType.Error -> Icons.Default.Error to BreezyTheme.colors.error
            BreezyToastType.Warning -> Icons.Default.Warning to BreezyTheme.colors.error
            BreezyToastType.Info -> Icons.Default.Info to BreezyTheme.colors.primary
        }

        AlertDialog(
            onDismissRequest = onDismiss,
            shape = BreezyTheme.shapes.medium,
            icon = { Icon(imageVector = icon, contentDescription = null, tint = tint) },
            text = {
                Text(
                    text = message,
                    style = BreezyTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {},
            containerColor = BreezyTheme.colors.surface
        )
    }
}

// ═══════════════════════════════════════════
// BreezyLoadingDialog — 加载弹窗
// ═══════════════════════════════════════════

/**
 * 加载中弹窗，居中显示旋转进度和可选的提示文字。
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreezyLoadingDialog(
    show: Boolean,
    message: String = "加载中...",
    onDismiss: (() -> Unit)? = null
) {
    if (show) {
        AlertDialog(
            onDismissRequest = { onDismiss?.invoke() },
            modifier = Modifier.widthIn(min = 200.dp, max = 280.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    color = BreezyTheme.colors.primary,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = message,
                    style = BreezyTheme.typography.bodyMedium,
                    color = BreezyTheme.colors.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
