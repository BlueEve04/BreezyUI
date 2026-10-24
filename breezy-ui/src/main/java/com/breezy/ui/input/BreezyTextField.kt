package com.breezy.ui.input

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.breezy.ui.theme.BreezyTheme

// ═══════════════════════════════════════════
// BreezyTextField — 文本输入框
// ═══════════════════════════════════════════

/**
 * 通用文本输入框。
 *
 * @param value 当前文本
 * @param onValueChange 值变化回调
 * @param modifier 修饰符
 * @param label 标签文字
 * @param placeholder 占位文字
 * @param leadingIcon 前置图标
 * @param trailingIcon 后置图标
 * @param enabled 是否启用
 * @param readOnly 是否只读
 * @param singleLine 是否单行
 * @param maxLines 最大行数
 * @param keyboardType 键盘类型
 * @param imeAction 键盘确认按钮动作
 * @param onDone 键盘确认回调
 * @param isError 是否错误状态
 * @param supportingText 底部辅助文字（错误或提示）
 * @param visualTransformation 视觉变换（如密码掩码）
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreezyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else 5,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Default,
    onDone: (() -> Unit)? = null,
    isError: Boolean = false,
    supportingText: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    val colors = BreezyTheme.colors

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        readOnly = readOnly,
        singleLine = singleLine,
        maxLines = maxLines,
        label = label?.let { { Text(it) } },
        placeholder = placeholder?.let { { Text(it) } },
        leadingIcon = leadingIcon?.let {
            { Icon(imageVector = it, contentDescription = null) }
        },
        trailingIcon = trailingIcon?.let { icon ->
            {
                if (onTrailingIconClick != null) {
                    IconButton(onClick = onTrailingIconClick) {
                        Icon(imageVector = icon, contentDescription = null)
                    }
                } else {
                    Icon(imageVector = icon, contentDescription = null)
                }
            }
        },
        isError = isError,
        supportingText = supportingText?.let {
            { Text(it, color = if (isError) colors.error else colors.onSurfaceVariant) }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onDone = { onDone?.invoke() }
        ),
        shape = BreezyTheme.shapes.small,
        visualTransformation = visualTransformation,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colors.primary,
            unfocusedBorderColor = colors.outline,
            disabledBorderColor = colors.onSurface.copy(alpha = 0.12f),
            errorBorderColor = colors.error,
            focusedLabelColor = colors.primary,
            errorLabelColor = colors.error,
            cursorColor = colors.primary
        )
    )
}

// ═══════════════════════════════════════════
// BreezyPasswordField — 密码输入框
// ═══════════════════════════════════════════

/**
 * 密码输入框，自带显示/隐藏切换。
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreezyPasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "密码",
    placeholder: String = "请输入密码",
    enabled: Boolean = true,
    isError: Boolean = false,
    supportingText: String? = null,
    onDone: (() -> Unit)? = null
) {
    var passwordVisible by remember { mutableStateOf(false) }

    BreezyTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = label,
        placeholder = placeholder,
        leadingIcon = Icons.Default.Lock,
        trailingIcon = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
        onTrailingIconClick = { passwordVisible = !passwordVisible },
        enabled = enabled,
        keyboardType = KeyboardType.Password,
        imeAction = ImeAction.Done,
        onDone = onDone,
        isError = isError,
        supportingText = supportingText,
        visualTransformation = if (passwordVisible) VisualTransformation.None
            else PasswordVisualTransformation()
    )
}

// ═══════════════════════════════════════════
// BreezySearchField — 搜索框
// ═══════════════════════════════════════════

/**
 * 搜索输入框。
 */
@Composable
fun BreezySearchField(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "搜索...",
    onSearch: () -> Unit = {},
    onClear: () -> Unit = {}
) {
    BreezyTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier,
        placeholder = placeholder,
        leadingIcon = Icons.Default.Search,
        trailingIcon = if (query.isNotEmpty()) Icons.Default.Clear else null,
        onTrailingIconClick = {
            onClear()
            onQueryChange("")
        },
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Search,
        onDone = onSearch
    )
}
