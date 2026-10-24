package com.breezy.ui.loading

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.breezy.ui.button.BreezyButton
import com.breezy.ui.button.BreezyButtonStyle
import com.breezy.ui.theme.BreezyTheme

// ═══════════════════════════════════════════
// BreezyLoading — 加载中
// ═══════════════════════════════════════════

/**
 * 加载中状态组件，居中显示进度圈和文字。
 */
@Composable
fun BreezyLoading(
    modifier: Modifier = Modifier,
    message: String = "加载中...",
    size: Float = 40f
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(
                color = BreezyTheme.colors.primary,
                strokeWidth = 3.dp,
                modifier = Modifier.size(size.dp)
            )
            if (message.isNotEmpty()) {
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

// ═══════════════════════════════════════════
// BreezyEmptyState — 空状态/错误状态
// ═══════════════════════════════════════════

enum class BreezyEmptyStateType {
    /** 暂无数据 */
    Empty,
    /** 网络错误 */
    Error,
    /** 搜索无结果 */
    NoResult,
    /** 自定义 */
    Custom
}

/**
 * 空状态占位组件。
 *
 * @param type 空状态类型
 * @param title 主标题
 * @param message 描述文字
 * @param icon 自定义图标
 * @param actionText 操作按钮文字（如"重试"）
 * @param onAction 操作按钮回调
 */
@Composable
fun BreezyEmptyState(
    modifier: Modifier = Modifier,
    type: BreezyEmptyStateType = BreezyEmptyStateType.Empty,
    title: String? = null,
    message: String? = null,
    icon: ImageVector? = null,
    iconTint: Color = BreezyTheme.colors.onSurfaceVariant,
    actionText: String? = null,
    onAction: (() -> Unit)? = null
) {
    val (defaultIcon, defaultTitle, defaultMessage) = when (type) {
        BreezyEmptyStateType.Empty -> Triple(
            Icons.Default.Inbox,
            "暂无数据",
            "这里还没有任何内容"
        )
        BreezyEmptyStateType.Error -> Triple(
            Icons.Default.ErrorOutline,
            "加载失败",
            "请检查网络连接后重试"
        )
        BreezyEmptyStateType.NoResult -> Triple(
            Icons.Default.SearchOff,
            "无搜索结果",
            "试试其他关键词"
        )
        BreezyEmptyStateType.Custom -> Triple(null, null, null)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(48.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            (icon ?: defaultIcon)?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(64.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = title ?: defaultTitle ?: "",
                style = BreezyTheme.typography.titleMedium,
                color = BreezyTheme.colors.onSurface,
                textAlign = TextAlign.Center
            )

            (message ?: defaultMessage)?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = it,
                    style = BreezyTheme.typography.bodyMedium,
                    color = BreezyTheme.colors.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }

            actionText?.let { text ->
                Spacer(modifier = Modifier.height(20.dp))
                BreezyButton(
                    text = text,
                    onClick = { onAction?.invoke() },
                    style = BreezyButtonStyle.Outlined
                )
            }
        }
    }
}

// ═══════════════════════════════════════════
// BreezyShimmer — 骨架屏闪烁动画
// ═══════════════════════════════════════════

/**
 * 骨架屏闪烁动画修饰符，用于内容加载中的占位效果。
 */
@Composable
fun Modifier.shimmer(): Modifier {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val alpha by transition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800),
            repeatMode = RepeatMode.Reverse
        ),
        label = "shimmerAlpha"
    )
    return this.alpha(alpha)
}

/**
 * 骨架屏列表加载占位。
 */
@Composable
fun BreezyShimmerList(
    itemCount: Int = 5,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        repeat(itemCount) {
            BreezyShimmerItem()
        }
    }
}

@Composable
fun BreezyShimmerItem() {
    val bg = BreezyTheme.colors.outline.copy(alpha = 0.3f)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shimmer(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 圆形头像占位
        Surface(
            modifier = Modifier.size(48.dp),
            shape = androidx.compose.foundation.shape.CircleShape,
            color = bg
        ) {}

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            // 标题占位
            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(14.dp),
                shape = BreezyTheme.shapes.extraSmall,
                color = bg
            ) {}
            Spacer(modifier = Modifier.height(8.dp))
            // 副标题占位
            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(12.dp),
                shape = BreezyTheme.shapes.extraSmall,
                color = bg
            ) {}
        }
    }
}

// ═══════════════════════════════════════════
// BreezyPullRefresh — 下拉刷新容器
// ═══════════════════════════════════════════

/**
 * 下拉刷新 + 上拉加载更多 列表容器。
 *
 * @param isRefreshing 是否正在刷新
 * @param onRefresh 刷新回调
 * @param isLoadingMore 是否正在加载更多
 * @param onLoadMore 加载更多回调
 * @param hasMore 是否还有更多数据
 * @param content 列表内容
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreezyPullRefreshList(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    isLoadingMore: Boolean = false,
    onLoadMore: (() -> Unit)? = null,
    hasMore: Boolean = false,
    content: @Composable () -> Unit
) {
    // 触发加载更多
    LaunchedEffect(isLoadingMore, hasMore) {
        if (!isLoadingMore && hasMore) {
            onLoadMore?.invoke()
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (isRefreshing) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp,
                            color = BreezyTheme.colors.primary
                        )
                    }
                }
            }

            item { content() }

            if (isLoadingMore) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp,
                            color = BreezyTheme.colors.primary
                        )
                    }
                }
            }

            if (!hasMore && !isLoadingMore) {
                item {
                    Text(
                        text = "— 没有更多了 —",
                        style = BreezyTheme.typography.labelMedium,
                        color = BreezyTheme.colors.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}

// ═══════════════════════════════════════════
// BreezyProgressBar — 进度条
// ═══════════════════════════════════════════

/**
 * 水平进度条，带文字标签。
 */
@Composable
fun BreezyProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    showLabel: Boolean = true,
    color: Color = BreezyTheme.colors.primary
) {
    Column(modifier = modifier.fillMaxWidth()) {
        if (showLabel) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "进度",
                    style = BreezyTheme.typography.labelMedium,
                    color = BreezyTheme.colors.onSurfaceVariant
                )
                Text(
                    text = "${(progress * 100).toInt()}%",
                    style = BreezyTheme.typography.labelMedium,
                    color = color
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        LinearProgressIndicator(
            progress = { progress.coerceIn(0f, 1f) },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            color = color,
            trackColor = BreezyTheme.colors.outline.copy(alpha = 0.3f),
        )
    }
}
