package com.breezy.ui

// ═══════════════════════════════════════════════════════════
// BreezyUI — 统一入口
// 
// 在 App 中只需导入此包即可使用所有控件：
//   import com.breezy.ui.*
// 
// 推荐用法（在 Activity 中）：
//   setContent {
//       BreezyTheme {
//           BreezyScaffold(topBarTitle = "我的应用") { padding ->
//               BreezyList(...)
//           }
//       }
//   }
// ═══════════════════════════════════════════════════════════

// ── 主题 ──
// BreezyTheme(), BreezyColors, BreezyTheme.colors/typography/shapes

// ── 按钮 ──
// BreezyButton, BreezyIconButton
// BreezyButtonStyle (Primary, Secondary, Outlined, Text, Danger)
// BreezyButtonSize (Small, Medium, Large)

// ── 文本 ──
// BreezyText, BreezyCustomText
// BreezyTextLevel (DisplayLarge, H1, BodyMedium, Caption...)

// ── 输入 ──
// BreezyTextField, BreezyPasswordField, BreezySearchField

// ── 卡片 ──
// BreezyCard, BreezyInfoCard, BreezyStatCard

// ── 弹窗 ──
// BreezyDialog, BreezyBottomSheet, BreezyToast, BreezyLoadingDialog
// BreezyToastType (Success, Error, Warning, Info)

// ── 列表 ──
// BreezyList, BreezyGrid, BreezyHorizontalList
// BreezyListItem, BreezyDivider

// ── 导航 ──
// BreezyTopAppBar, BreezyBottomNavBar, BreezyTabRow
// BreezyScaffold, BreezyNavItem

// ── 加载/状态 ──
// BreezyLoading, BreezyEmptyState, BreezyLoadingDialog
// BreezyShimmerList, BreezyShimmerItem, BreezyProgressBar
// BreezyPullRefreshList
// BreezyEmptyStateType (Empty, Error, NoResult, Custom)
// Modifier.shimmer()
