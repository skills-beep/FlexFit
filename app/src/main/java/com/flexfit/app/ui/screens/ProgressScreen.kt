package com.flexfit.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import com.flexfit.app.ui.components.ProgressChart
import com.flexfit.app.ui.components.AchievementCard
import com.flexfit.app.ui.components.ProgressStatCard
import com.flexfit.app.ui.theme.*

data class ProgressStat(
    val title: String,
    val currentValue: String,
    val previousValue: String,
    val unit: String,
    val icon: ImageVector,
    val color: Color,
    val changePercent: Float
)

data class Achievement(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val color: Color,
    val isCompleted: Boolean,
    val progress: Float = 1f
)

data class ChartData(
    val label: String,
    val value: Float
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressScreen() {
    val progressStats = remember {
        listOf(
            ProgressStat(
                "Weekly Workouts",
                "5",
                "3",
                "sessions",
                Icons.Default.FitnessCenter,
                FitnessBlue,
                66.7f
            ),
            ProgressStat(
                "Avg Duration",
                "42",
                "35",
                "min",
                Icons.Default.Timer,
                FitnessGreen,
                20f
            ),
            ProgressStat(
                "Total Calories",
                "2,450",
                "1,890",
                "kcal",
                Icons.Default.LocalFireDepartment,
                FitnessRed,
                29.6f
            ),
            ProgressStat(
                "Personal Records",
                "3",
                "1",
                "new",
                Icons.Default.EmojiEvents,
                FitnessOrange,
                200f
            )
        )
    }

    val achievements = remember {
        listOf(
            Achievement(
                "First Workout",
                "Complete your first workout session",
                Icons.Default.PlayArrow,
                FitnessGreen,
                true
            ),
            Achievement(
                "Week Warrior",
                "Work out 5 times in a week",
                Icons.Default.CalendarToday,
                FitnessBlue,
                true
            ),
            Achievement(
                "Calorie Crusher",
                "Burn 500+ calories in a session",
                Icons.Default.LocalFireDepartment,
                FitnessRed,
                true
            ),
            Achievement(
                "Consistency King",
                "Maintain a 30-day streak",
                Icons.Default.Timeline,
                FitnessOrange,
                false,
                0.6f
            )
        )
    }

    val weeklyData = remember {
        listOf(
            ChartData("Mon", 320f),
            ChartData("Tue", 450f),
            ChartData("Wed", 280f),
            ChartData("Thu", 520f),
            ChartData("Fri", 380f),
            ChartData("Sat", 600f),
            ChartData("Sun", 420f)
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Header
        item {
            ProgressHeader()
        }

        // Progress Stats
        item {
            Column {
                Text(
                    text = "This Week's Progress",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(12.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(progressStats) { stat ->
                        ProgressStatCard(stat = stat)
                    }
                }
            }
        }

        // Chart Section
        item {
            Column {
                Text(
                    text = "Weekly Calories Burned",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(12.dp))
                ProgressChart(
                    data = weeklyData,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }
        }

        // Achievements Section
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Achievements",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                TextButton(onClick = { /* Show all achievements */ }) {
                    Text("View All")
                }
            }
        }

        items(achievements) { achievement ->
            AchievementCard(achievement = achievement)
        }
    }
}

@Composable
fun ProgressHeader() {
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
                            FitnessRed,
                            FitnessOrange
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
                    text = "Your Progress 📈",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Keep up the great work!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }
        }
    }
}