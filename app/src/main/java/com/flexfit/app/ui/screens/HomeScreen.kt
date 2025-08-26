package com.flexfit.app.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.flexfit.app.ui.components.AnimatedStatCard
import com.flexfit.app.ui.components.QuickActionCard
import com.flexfit.app.ui.components.RecentWorkoutCard
import com.flexfit.app.ui.theme.FitnessBlue
import com.flexfit.app.ui.theme.FitnessGreen
import com.flexfit.app.ui.theme.FitnessOrange
import com.flexfit.app.ui.theme.FitnessRed

data class StatItem(
    val title: String,
    val value: String,
    val unit: String,
    val icon: ImageVector,
    val color: Color,
    val change: String? = null
)

data class QuickAction(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val color: Color
)

data class RecentWorkout(
    val name: String,
    val type: String,
    val duration: String,
    val calories: String,
    val date: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val stats = remember {
        listOf(
            StatItem("Calories", "425", "kcal", Icons.Default.LocalFireDepartment, FitnessRed, "+12%"),
            StatItem("Duration", "45", "min", Icons.Default.Timer, FitnessBlue, "+5%"),
            StatItem("Exercises", "8", "", Icons.Default.FitnessCenter, FitnessOrange, "+2"),
            StatItem("Streak", "7", "days", Icons.Default.EmojiEvents, FitnessGreen, "+1")
        )
    }

    val quickActions = remember {
        listOf(
            QuickAction("Start Workout", "Begin your session", Icons.Default.PlayArrow, FitnessGreen),
            QuickAction("Track Progress", "View your stats", Icons.Default.TrendingUp, FitnessBlue),
            QuickAction("Set Goals", "Plan your journey", Icons.Default.Flag, FitnessOrange),
            QuickAction("Log Food", "Track nutrition", Icons.Default.Restaurant, FitnessRed)
        )
    }

    val recentWorkouts = remember {
        listOf(
            RecentWorkout("Upper Body Strength", "Strength", "45 min", "320 kcal", "Today"),
            RecentWorkout("Morning Cardio", "Cardio", "30 min", "250 kcal", "Yesterday"),
            RecentWorkout("Core & Abs", "Strength", "25 min", "180 kcal", "2 days ago"),
            RecentWorkout("HIIT Session", "HIIT", "20 min", "300 kcal", "3 days ago")
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Welcome Header
        item {
            WelcomeHeader()
        }

        // Today's Stats
        item {
            Column {
                Text(
                    text = "Today's Activity",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(12.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(stats) { stat ->
                        AnimatedStatCard(stat = stat)
                    }
                }
            }
        }

        // Quick Actions
        item {
            Column {
                Text(
                    text = "Quick Actions",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    quickActions.take(2).forEach { action ->
                        QuickActionCard(
                            action = action,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    quickActions.drop(2).forEach { action ->
                        QuickActionCard(
                            action = action,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }

        // Recent Workouts
        item {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Recent Workouts",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    TextButton(onClick = { /* Navigate to workouts */ }) {
                        Text("View All")
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        items(recentWorkouts) { workout ->
            RecentWorkoutCard(workout = workout)
        }
    }
}

@Composable
fun WelcomeHeader() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
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
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
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
                    text = "Welcome back! 👋",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Ready to crush your fitness goals today?",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f)
                )
            }
            
            Icon(
                imageVector = Icons.Default.FitnessCenter,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(48.dp),
                tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
            )
        }
    }
}