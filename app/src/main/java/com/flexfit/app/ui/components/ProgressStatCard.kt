package com.flexfit.app.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.flexfit.app.ui.screens.ProgressStat

@Composable
fun ProgressStatCard(
    stat: ProgressStat,
    modifier: Modifier = Modifier
) {
    var isVisible by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        isVisible = true
    }

    val animatedProgress by animateFloatAsState(
        targetValue = if (isVisible) stat.changePercent / 100f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    val isPositive = stat.changePercent > 0

    Card(
        modifier = modifier
            .width(160.dp)
            .height(140.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Icon background
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(stat.color.copy(alpha = 0.15f))
                    .align(Alignment.TopEnd),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = stat.icon,
                    contentDescription = null,
                    tint = stat.color,
                    modifier = Modifier.size(22.dp)
                )
            }

            Column(
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Text(
                    text = stat.title,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = stat.currentValue,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    if (stat.unit.isNotEmpty()) {
                        Text(
                            text = " ${stat.unit}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(bottom = 2.dp)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Progress indicator
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (isPositive) Icons.Default.TrendingUp else Icons.Default.TrendingDown,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = if (isPositive) stat.color else MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${if (isPositive) "+" else ""}${(stat.changePercent * animatedProgress).toInt()}%",
                        style = MaterialTheme.typography.bodySmall,
                        color = if (isPositive) stat.color else MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Medium
                    )
                }
                
                Text(
                    text = "from ${stat.previousValue} ${stat.unit}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}