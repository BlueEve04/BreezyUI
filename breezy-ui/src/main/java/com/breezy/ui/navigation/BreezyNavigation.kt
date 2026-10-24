package com.breezy.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.breezy.ui.theme.BreezyTheme
import com.breezy.ui.button.BreezyIconButton
import com.breezy.ui.button.BreezyButtonStyle
import com.breezy.ui.button.BreezyButtonSize

// ═══════════════════════════════════════════
// BreezyTopAppBar — 顶部导航栏
// ═══════════════════════════════════════════

/**
 * 顶部导航栏，支持标题、返回按钮、右侧操作按钮。
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreezyTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    showBackButton: Boolean = false,
    onBackClick: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = BreezyTheme.colors.surface,
    contentColor: Color = BreezyTheme.colors.onSurface
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = BreezyTheme.typography.titleLarge
            )
        },
        modifier = modifier,
        navigationIcon = {
            if (showBackButton) {
                BreezyIconButton(
                    icon = Icons.Default.ArrowBack,
                    onClick = onBackClick,
                    style = BreezyButtonStyle.Text
                )
            }
        },
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = backgroundColor,
            titleContentColor = contentColor,
            navigationIconContentColor = contentColor,
            actionIconContentColor = contentColor
        )
    )
}

// ═══════════════════════════════════════════
// BreezyBottomNavBar — 底部标签栏
// ═══════════════════════════════════════════

/**
 * 底部导航项数据类。
 */
data class BreezyNavItem(
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = null
)

/**
 * 底部导航栏，通常 3-5 个标签。
 *
 * @param items 导航项列表
 * @param selectedIndex 当前选中索引
 * @param onItemSelected 选中回调，返回索引
 * @param modifier 修饰符
 */
@Composable
fun BreezyBottomNavBar(
    items: List<BreezyNavItem>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
        containerColor = BreezyTheme.colors.surface,
        contentColor = BreezyTheme.colors.onSurface
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = { onItemSelected(index) },
                icon = {
                    BadgedBox(
                        badge = {
                            item.badgeCount?.let { count ->
                                Badge { Text(count.toString()) }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (selectedIndex == index) item.selectedIcon
                                else item.unselectedIcon,
                            contentDescription = item.label
                        )
                    }
                },
                label = { Text(item.label, style = BreezyTheme.typography.labelSmall) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = BreezyTheme.colors.primary,
                    selectedTextColor = BreezyTheme.colors.primary,
                    unselectedIconColor = BreezyTheme.colors.onSurfaceVariant,
                    unselectedTextColor = BreezyTheme.colors.onSurfaceVariant,
                    indicatorColor = BreezyTheme.colors.primary.copy(alpha = 0.1f)
                )
            )
        }
    }
}

// ═══════════════════════════════════════════
// BreezyTabRow — 标签页栏
// ═══════════════════════════════════════════

/**
 * 顶部标签页，支持滑动切换内容。
 *
 * @param tabs 标签标题列表
 * @param selectedIndex 当前选中索引
 * @param onTabSelected 标签选中回调
 */
@Composable
fun BreezyTabRow(
    tabs: List<String>,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    TabRow(
        selectedTabIndex = selectedIndex,
        modifier = modifier,
        containerColor = BreezyTheme.colors.surface,
        contentColor = BreezyTheme.colors.primary
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedIndex == index,
                onClick = { onTabSelected(index) },
                text = {
                    Text(
                        text = title,
                        style = BreezyTheme.typography.labelLarge,
                        color = if (selectedIndex == index) BreezyTheme.colors.primary
                            else BreezyTheme.colors.onSurfaceVariant
                    )
                }
            )
        }
    }
}

// ═══════════════════════════════════════════
// BreezyScaffold — 页面脚手架
// ═══════════════════════════════════════════

/**
 * 便捷的页面脚手架，整合顶部导航栏、底部导航栏和内容区。
 *
 * @param topBarTitle 顶部标题
 * @param showBackButton 是否显示返回按钮
 * @param onBackClick 返回回调
 * @param topBarActions 顶部右侧操作按钮
 * @param bottomNavItems 底部导航项（null 则不显示）
 * @param bottomNavSelectedIndex 底部导航选中索引
 * @param onBottomNavSelected 底部导航选中回调
 * @param floatingActionButton 浮动按钮
 * @param content 主内容区
 */
@Composable
fun BreezyScaffold(
    topBarTitle: String? = null,
    showBackButton: Boolean = false,
    onBackClick: () -> Unit = {},
    topBarActions: @Composable RowScope.() -> Unit = {},
    bottomNavItems: List<BreezyNavItem>? = null,
    bottomNavSelectedIndex: Int = 0,
    onBottomNavSelected: (Int) -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            topBarTitle?.let { title ->
                BreezyTopAppBar(
                    title = title,
                    showBackButton = showBackButton,
                    onBackClick = onBackClick,
                    actions = topBarActions
                )
            }
        },
        bottomBar = {
            bottomNavItems?.let { items ->
                BreezyBottomNavBar(
                    items = items,
                    selectedIndex = bottomNavSelectedIndex,
                    onItemSelected = onBottomNavSelected
                )
            }
        },
        floatingActionButton = floatingActionButton,
        containerColor = BreezyTheme.colors.background
    ) { paddingValues ->
        content(paddingValues)
    }
}
