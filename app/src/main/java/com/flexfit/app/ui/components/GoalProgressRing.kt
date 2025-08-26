package com.flexfit.app.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flexfit.app.ui.theme.FitnessGreen
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun GoalProgressRing(
    progress: Float,
    modifier: Modifier = Modifier,
    strokeWidth: Float = 8f,
    animationDuration: Int = 1000
) {
    var isVisible by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        isVisible = true
    }

    val animatedProgress by animateFloatAsState(
        targetValue = if (isVisible) progress.coerceIn(0f, 1f) else 0f,
        animationSpec = tween(
            durationMillis = animationDuration,
            easing = FastOutSlowInEasing
        )
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawProgressRing(
                progress = animatedProgress,
                strokeWidth = strokeWidth,
                backgroundColor = Color.Gray.copy(alpha = 0.2f),
                progressColor = FitnessGreen
            )
        }
        
        // Progress text
        Text(
            text = "${(animatedProgress * 100).toInt()}%",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = FitnessGreen
        )
    }
}

private fun DrawScope.drawProgressRing(
    progress: Float,
    strokeWidth: Float,
    backgroundColor: Color,
    progressColor: Color
) {
    val center = size.center
    val radius = (size.minDimension - strokeWidth) / 2f
    
    // Draw background ring
    drawCircle(
        color = backgroundColor,
        radius = radius,
        center = center,
        style = Stroke(width = strokeWidth)
    )
    
    // Draw progress arc
    if (progress > 0f) {
        drawArc(
            color = progressColor,
            startAngle = -90f,
            sweepAngle = 360f * progress,
            useCenter = false,
            style = Stroke(
                width = strokeWidth,
                cap = StrokeCap.Round
            ),
            topLeft = androidx.compose.ui.geometry.Offset(
                center.x - radius,
                center.y - radius
            ),
            size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2)
        )
    }
}