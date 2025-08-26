package com.flexfit.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.flexfit.app.ui.components.GoalCard
import com.flexfit.app.ui.components.GoalProgressRing
import com.flexfit.app.ui.theme.*

data class Goal(
    val id: String,
    val title: String,
    val description: String,
    val targetValue: Float,
    val currentValue: Float,
    val unit: String,
    val icon: ImageVector,
    val color: Color,
    val deadline: String,
    val category: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalsScreen() {
    val goals = remember {
        listOf(
            Goal(
                "weekly_workouts",
                "Weekly Workouts",
                "Complete 5 workouts this week",
                5f,
                3f,
                "workouts",
                Icons.Default.FitnessCenter,
                FitnessBlue,
                "End of week",
                "Fitness"
            ),
            Goal(
                "weight_loss",
                "Weight Goal",
                "Lose 5 kg in 3 months",
                5f,
                2.3f,
                "kg",
                Icons.Default.MonitorWeight,
                FitnessGreen,
                "3 months",
                "Health"
            ),
            Goal(
                "daily_steps",
                "Daily Steps",
                "Walk 10,000 steps daily",
                10000f,
                7500f,
                "steps",
                Icons.Default.DirectionsWalk,
                FitnessOrange,
                "Daily",
                "Activity"
            ),
            Goal(
                "monthly_calories",
                "Monthly Calories",
                "Burn 15,000 calories this month",
                15000f,
                8500f,
                "kcal",
                Icons.Default.LocalFireDepartment,
                FitnessRed,
                "End of month",
                "Fitness"
            ),
            Goal(
                "strength_goal",
                "Strength Training",
                "Increase bench press by 10kg",
                10f,
                4f,
                "kg",
                Icons.Default.FitnessCenter,
                FitnessPurple,
                "2 months",
                "Strength"
            )
        )
    }

    val completedGoals = goals.filter { it.currentValue >= it.targetValue }
    val activeGoals = goals.filter { it.currentValue < it.targetValue }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Header
        item {
            GoalsHeader()
        }

        // Overall Progress
        item {
            OverallProgressCard(goals = goals)
        }

        // Active Goals
        if (activeGoals.isNotEmpty()) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Active Goals",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    TextButton(onClick = { /* Add new goal */ }) {
                        Text("Add Goal")
                    }
                }
            }

            items(activeGoals) { goal ->
                GoalCard(goal = goal)
            }
        }

        // Completed Goals
        if (completedGoals.isNotEmpty()) {
            item {
                Text(
                    text = "Completed Goals",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            items(completedGoals) { goal ->
                GoalCard(goal = goal)
            }
        }
    }
}

@Composable
fun GoalsHeader() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            FitnessPurple,
                            FitnessBlue
                        )
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Text(
                    text = "Your Goals 🎯",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Stay focused and achieve greatness!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }
        }
    }
}

@Composable
fun OverallProgressCard(goals: List<Goal>) {
    val totalProgress = goals.sumOf { (it.currentValue / it.targetValue).coerceAtMost(1.0).toDouble() }
    val averageProgress = if (goals.isNotEmpty()) totalProgress / goals.size else 0.0
    val completedCount = goals.count { it.currentValue >= it.targetValue }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Progress Ring
            GoalProgressRing(
                progress = averageProgress.toFloat(),
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.width(20.dp))

            // Progress Details
            Column {
                Text(
                    text = "Overall Progress",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = "${(averageProgress * 100).toInt()}% of all goals",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = FitnessGreen
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "$completedCount of ${goals.size} completed",
                        style = MaterialTheme.typography.bodyMedium,
                        color = FitnessGreen,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}