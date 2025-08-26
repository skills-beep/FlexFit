package com.flexfit.app.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flexfit.app.ui.screens.ChartData
import com.flexfit.app.ui.theme.FitnessBlue
import com.flexfit.app.ui.theme.FitnessGreen
import kotlin.math.max

@Composable
fun ProgressChart(
    data: List<ChartData>,
    modifier: Modifier = Modifier
) {
    var isVisible by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        isVisible = true
    }

    val animatedProgress by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1500,
            easing = FastOutSlowInEasing
        )
    )

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Chart
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            ) {
                Canvas(
                    modifier = Modifier.fillMaxSize()
                ) {
                    drawChart(
                        data = data,
                        progress = animatedProgress,
                        color = FitnessBlue
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Labels
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                data.forEach { item ->
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

private fun DrawScope.drawChart(
    data: List<ChartData>,
    progress: Float,
    color: Color
) {
    if (data.isEmpty()) return
    
    val maxValue = data.maxOf { it.value }
    val minValue = data.minOf { it.value }
    val valueRange = maxValue - minValue
    
    val width = size.width
    val height = size.height - 40f // Leave space for labels
    val barWidth = width / data.size * 0.6f
    val spacing = width / data.size * 0.4f
    
    data.forEachIndexed { index, item ->
        val barHeight = if (valueRange > 0) {
            (item.value - minValue) / valueRange * height * progress
        } else {
            height * 0.5f * progress
        }
        
        val x = index * (barWidth + spacing) + spacing / 2
        val y = height - barHeight
        
        // Draw bar with gradient
        val gradient = Brush.verticalGradient(
            colors = listOf(
                color,
                color.copy(alpha = 0.6f)
            ),
            startY = y,
            endY = height
        )
        
        drawRoundRect(
            brush = gradient,
            topLeft = Offset(x, y),
            size = Size(barWidth, barHeight),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(8f, 8f)
        )
        
        // Draw value text
        if (progress > 0.8f) {
            drawContext.canvas.nativeCanvas.drawText(
                "${item.value.toInt()}",
                x + barWidth / 2,
                y - 10f,
                androidx.compose.ui.graphics.Paint().asFrameworkPaint().apply {
                    textAlign = android.graphics.Paint.Align.CENTER
                    textSize = 12.sp.toPx()
                    this.color = color.toArgb()
                    isFakeBoldText = true
                }
            )
        }
    }
}