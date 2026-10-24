package com.breezy.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.breezy.ui.theme.BreezyTheme
import com.breezy.ui.theme.BreezyColorScheme
import com.breezy.ui.theme.BreezyVisualStyle
import com.breezy.ui.theme.resolveVisualStyle
import com.breezy.ui.theme.BreezyPress3D
import com.breezy.ui.theme.LocalBreezyPress3DEnabled
import com.breezy.ui.input.BreezySwitch

// ═══════════════════════════════════════════
// BreezyList — 纵向列表
// ═══════════════════════════════════════════

/**
 * 通用 LazyColumn 列表封装，支持标题头。
 */
@Composable
fun <T> BreezyList(
    items: List<T>,
    modifier: Modifier = Modifier,
    title: String? = null,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    verticalSpacing: Dp = 8.dp,
    itemContent: @Composable (T) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(verticalSpacing)
    ) {
        title?.let {
            item {
                Text(
                    text = it,
                    style = BreezyTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }
        items(items) { item -> itemContent(item) }
    }
}

// ═══════════════════════════════════════════
// BreezyGrid — 网格列表
// ═══════════════════════════════════════════

/**
 * 通用 LazyVerticalGrid 网格封装。
 *
 * @param items 数据列表
 * @param columns 列数，默认 2
 * @param modifier 修饰符
 * @param title 可选标题
 * @param contentPadding 内边距
 * @param horizontalSpacing 水平间距
 * @param verticalSpacing 垂直间距
 * @param itemContent 每一项的 Composable
 */
@Composable
fun <T> BreezyGrid(
    items: List<T>,
    columns: Int = 2,
    modifier: Modifier = Modifier,
    title: String? = null,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    horizontalSpacing: Dp = 12.dp,
    verticalSpacing: Dp = 12.dp,
    itemContent: @Composable (T) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = modifier.fillMaxWidth(),
        contentPadding = contentPadding,
        horizontalArrangement = Arrangement.spacedBy(horizontalSpacing),
        verticalArrangement = Arrangement.spacedBy(verticalSpacing)
    ) {
        title?.let {
            item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(columns) }) {
                Text(
                    text = it,
                    style = BreezyTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }
        items(items) { item -> itemContent(item) }
    }
}

// ═══════════════════════════════════════════
// BreezyHorizontalList — 横向滚动列表
// ═══════════════════════════════════════════

@Composable
fun <T> BreezyHorizontalList(
    items: List<T>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp),
    horizontalSpacing: Dp = 12.dp,
    itemContent: @Composable (T) -> Unit
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = contentPadding,
        horizontalArrangement = Arrangement.spacedBy(horizontalSpacing)
    ) {
        items(items) { item -> itemContent(item) }
    }
}

// ═══════════════════════════════════════════
// BreezyListItem — 标准列表项
// ═══════════════════════════════════════════

/**
 * 标准的 Material 风格列表项。
 * 常用于设置页、菜单等场景。
 *
 * @param colorOverride 覆盖颜色方案
 * @param visualStyle 表面样式
 * @param press3D 是否启用 3D 按下效果
 * @param trailingSwitchChecked 若不为 null 则右侧显示开关，值为当前开关状态
 * @param onTrailingSwitchChange 开关状态回调
 * @param trailingDropdown 是否显示右侧下拉菜单按钮
 * @param dropdownContent 下拉菜单内容（提供此参数时自动处理展开/收起，右对齐）
 */
@Composable
fun BreezyListItem(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    leadingIcon: ImageVector? = null,
    leadingIconTint: Color = BreezyTheme.colors.primary,
    trailingText: String? = null,
    showChevron: Boolean = false,
    onClick: (() -> Unit)? = null,
    colorOverride: BreezyColorScheme? = null,
    visualStyle: BreezyVisualStyle? = null,
    press3D: Boolean = LocalBreezyPress3DEnabled.current,
    trailingSwitchChecked: Boolean? = null,
    onTrailingSwitchChange: ((Boolean) -> Unit)? = null,
    trailingDropdown: Boolean = false,
    dropdownContent: (@Composable ColumnScope.() -> Unit)? = null
) {
    val colors = colorOverride ?: BreezyTheme.colorScheme
    val resolvedVisualStyle = resolveVisualStyle(visualStyle)
    var dropdownExpanded by remember { mutableStateOf(false) }

    // 列表项背景：surface 混合 ~12% 主题色，形成柔和染色
    val tintedSurface = androidx.compose.ui.graphics.lerp(
        BreezyTheme.colors.surface,
        BreezyTheme.colors.primary,
        0.12f
    )

    val rowContent: @Composable () -> Unit = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            leadingIcon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    tint = leadingIconTint,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = BreezyTheme.typography.bodyLarge,
                    color = BreezyTheme.colors.onSurface
                )
                subtitle?.let {
                    Text(
                        text = it,
                        style = BreezyTheme.typography.bodySmall,
                        color = BreezyTheme.colors.onSurfaceVariant,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }

            trailingText?.let {
                Text(
                    text = it,
                    style = BreezyTheme.typography.bodyMedium,
                    color = BreezyTheme.colors.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(4.dp))
            }

            // 右侧开关
            if (trailingSwitchChecked != null) {
                BreezySwitch(
                    checked = trailingSwitchChecked,
                    onCheckedChange = { onTrailingSwitchChange?.invoke(it) },
                    colorOverride = colorOverride
                )
            }

            // 右侧下拉按钮
            if (trailingDropdown) {
                IconButton(onClick = {
                    dropdownExpanded = true
                    onClick?.invoke()
                }) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowDown,
                        contentDescription = "更多选项",
                        tint = BreezyTheme.colors.onSurfaceVariant,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            if (showChevron) {
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = BreezyTheme.colors.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }

    val surface: @Composable () -> Unit = {
        if (onClick != null || dropdownContent != null) {
            Surface(
                onClick = {
                    if (dropdownContent != null) dropdownExpanded = true
                    onClick?.invoke()
                },
                modifier = modifier
                    .fillMaxWidth()
                    .BreezyPress3D(enabled = press3D),
                color = tintedSurface,
                shape = BreezyTheme.shapes.small
            ) {
                rowContent()
            }
        } else {
            Surface(
                modifier = modifier
                    .fillMaxWidth()
                    .BreezyPress3D(enabled = false),
                color = tintedSurface,
                shape = BreezyTheme.shapes.small
            ) {
                rowContent()
            }
        }
    }

    if (dropdownContent != null) {
        Box(modifier = Modifier.fillMaxWidth()) {
            surface()
            DropdownMenu(
                expanded = dropdownExpanded,
                onDismissRequest = { dropdownExpanded = false },
                modifier = Modifier.fillMaxWidth(0.5f),
                offset = DpOffset(
                    x = ((LocalConfiguration.current.screenWidthDp - 16) * 0.5f).dp,
                    y = (-8).dp
                )
            ) {
                dropdownContent()
            }
        }
    } else {
        surface()
    }
}

// ═══════════════════════════════════════════
// BreezyDivider — 分割线
// ═══════════════════════════════════════════

@Composable
fun BreezyDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = 0.5.dp,
    color: Color = BreezyTheme.colors.outline.copy(alpha = 0.5f)
) {
    HorizontalDivider(
        modifier = modifier.fillMaxWidth(),
        thickness = thickness,
        color = color
    )
}
