# BreezyUI

> 轻量级 Jetpack Compose 组件库 — 30+ 精心设计的 Android UI 控件

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.22-blue.svg)](https://kotlinlang.org)
[![Compose](https://img.shields.io/badge/Compose-BOM%202024.02.00-green.svg)](https://developer.android.com/jetpack/compose)
[![Min SDK](https://img.shields.io/badge/Min%20SDK-24-orange.svg)](https://developer.android.com)

---

## ✨ 特性

- 🎨 **动态主题色** — 运行时切换品牌色/语义色，每个控件独立颜色覆盖
- 🪞 **表面样式** — Normal / 半透明 / 磨砂玻璃 三种视觉效果
- 🎯 **3D 按压下沉** — 点击时按压点局部凹陷，不消耗触摸事件
- 🧩 **30+ 组件** — 按钮/文本/输入/卡片/弹窗/列表/导航/加载/状态
- 🌗 **亮暗双主题** — 内置浅色/深色方案，自动跟随系统
- 📦 **Maven Local 发布** — `com.breezy.ui:breezy-ui:1.0.0`

---

## 📦 快速开始

### 添加依赖

```kotlin
// build.gradle.kts (app module)
dependencies {
    implementation("com.breezy.ui:breezy-ui:1.0.0")
}
```

### 基础使用

```kotlin
setContent {
    BreezyTheme {
        BreezyScaffold(topBarTitle = "我的应用") { padding ->
            // 你的内容
        }
    }
}
```

---

## 🧭 组件总览

### 🎨 主题

| 组件 | 说明 |
|------|------|
| `BreezyTheme` | 主题入口，支持 `colorSchemeOverride` 动态换色 |
| `BreezyColorScheme` | 完整颜色方案数据类，可读写 |
| `BreezyVisualStyle` | Normal / SemiTransparent / FrostedGlass |
| `BreezyTheme.colors / typography / shapes` | 静态便捷访问 |

```kotlin
// 动态切换主题
var scheme by remember { mutableStateOf(BreezyColorScheme.defaultLight()) }
BreezyTheme(colorSchemeOverride = scheme) { ... }
```

### 🔘 按钮

| 组件 | 说明 |
|------|------|
| `BreezyButton` | 通用按钮 — 5 种风格 × 3 种尺寸 |
| `BreezyIconButton` | 纯图标按钮 |

**风格**: `Primary` / `Secondary` / `Outlined` / `Text` / `Danger`  
**尺寸**: `Small(32)` / `Medium(40)` / `Large(48)`

```kotlin
BreezyButton(
    text = "提交",
    onClick = { },
    style = BreezyButtonStyle.Primary,
    size = BreezyButtonSize.Large,
    icon = Icons.Default.Send,
    loading = false,
    press3D = true  // 3D 按压效果
)
```

### ⌨️ 输入

| 组件 | 说明 |
|------|------|
| `BreezyTextField` | 文本输入框 |
| `BreezyPasswordField` | 密码输入框（内置显隐切换） |
| `BreezySearchField` | 搜索框 |
| `BreezySwitch` | 开关组件 |

```kotlin
var text by remember { mutableStateOf("") }
BreezyTextField(
    value = text,
    onValueChange = { text = it },
    label = "邮箱",
    leadingIcon = Icons.Default.Email,
    keyboardType = KeyboardType.Email
)

var checked by remember { mutableStateOf(true) }
BreezySwitch(checked = checked, onCheckedChange = { checked = it })
```

### 🃏 卡片

| 组件 | 说明 |
|------|------|
| `BreezyCard` | 通用卡片容器（header/content/footer） |
| `BreezyInfoCard` | 信息卡片（图标 + 标题 + 描述） |
| `BreezyStatCard` | 统计卡片（数值 + 标签） |

```kotlin
BreezyCard(
    onClick = { },
    elevated = true,
    header = { BreezyText("标题", level = BreezyTextLevel.H3) },
    content = { BreezyText("内容") },
    footer = { BreezyButton(text = "操作", onClick = { }) }
)
```

### 💬 弹窗

| 组件 | 说明 |
|------|------|
| `BreezyDialog` | 标准确认弹窗 |
| `BreezyBottomSheet` | 底部弹出面板 |
| `BreezyToast` | 简短提示（自动消失） |
| `BreezyLoadingDialog` | 加载中弹窗 |

```kotlin
BreezyDialog(
    show = showDialog,
    onDismiss = { showDialog = false },
    title = "确认删除？",
    text = "此操作不可撤销",
    confirmText = "删除",
    onConfirm = { deleteItem() }
)
```

### 📋 列表

| 组件 | 说明 |
|------|------|
| `BreezyList<T>` | 纵向滚动列表 |
| `BreezyGrid<T>` | 网格列表 |
| `BreezyHorizontalList<T>` | 横向滚动列表 |
| `BreezyListItem` | 标准列表项 |
| `BreezyDivider` | 分割线 |

**列表项右侧元素（可组合）**:
- `trailingText` — 文字
- `showChevron` — 箭头
- `trailingSwitchChecked` + `onTrailingSwitchChange` — 开关
- `trailingDropdown` — 下拉按钮

```kotlin
BreezyListItem(
    title = "Wi-Fi",
    leadingIcon = Icons.Default.Wifi,
    trailingSwitchChecked = wifiOn,
    onTrailingSwitchChange = { wifiOn = it }
)
```

### ⏳ 加载与状态

| 组件 | 说明 |
|------|------|
| `BreezyLoading` | 加载动画 |
| `BreezyEmptyState` | 空状态/错误/无结果 |
| `BreezyProgressBar` | 进度条 |
| `BreezyShimmerList` | 骨架屏占位 |
| `BreezyPullRefreshList` | 下拉刷新容器 |

### 🧭 导航

| 组件 | 说明 |
|------|------|
| `BreezyTopAppBar` | 顶部导航栏 |
| `BreezyBottomNavBar` | 底部标签栏 |
| `BreezyTabRow` | 标签页 |
| `BreezyScaffold` | 页面脚手架 |

```kotlin
BreezyScaffold(
    topBarTitle = "设置",
    showBackButton = true,
    onBackClick = { navBack() },
    bottomNavItems = navItems,
    bottomNavSelectedIndex = currentTab,
    onBottomNavSelected = { currentTab = it }
) { padding ->
    // 内容区
}
```

### 📝 文本

| 组件 | 说明 |
|------|------|
| `BreezyText` | 统一排版层级（11 级字体） |
| `BreezyCustomText` | 自定义样式文本 |

**层级**: `DisplayLarge` / `DisplayMedium` / `DisplaySmall` / `H1` / `H2` / `H3` / `BodyLarge` / `BodyMedium` / `BodySmall` / `Label` / `Caption`

---

## 🎯 3D 按压效果

所有可点击组件（按钮/卡片/列表项）默认启用 3D 按压下沉效果：

- 点击时按压点向屏幕内凹陷
- 弹簧回弹动画
- 不消耗触摸事件（不影响正常点击）
- 通过 `CompositionLocal` 全局开关

```kotlin
// 全局开关
CompositionLocalProvider(
    LocalBreezyPress3DEnabled provides false  // 关闭所有 3D 效果
) { ... }

// 单个组件开关
BreezyButton(text = "点击", onClick = { }, press3D = false)
```

---

## 🎨 主题配置

### 预设配色切换

```kotlin
val presets = listOf(
    BreezyColorScheme.defaultLight(),               // 靛蓝（默认）
    BreezyColorScheme.defaultLight().copy(          // 自定义
        primary = Color(0xFF059669),
        secondary = Color(0xFFF59E0B)
    )
)
```

### 表面样式

```kotlin
// 全局设置
CompositionLocalProvider(
    LocalBreezyVisualStyle provides BreezyVisualStyle.SemiTransparent
) { ... }

// 单个组件
BreezyCard(visualStyle = BreezyVisualStyle.FrostedGlass, ...)
```

---

## 🛠 构建

```bash
# 发布到本地 Maven
cd BreezyUI && ./gradlew publishToMavenLocal

# 构建 Demo APK
cd BreezyUI_Sample && ./gradlew assembleDebug
```

---

## 📄 许可

MIT License

---

**BreezyUI** — Build beautiful Android apps with less code. 🌊
