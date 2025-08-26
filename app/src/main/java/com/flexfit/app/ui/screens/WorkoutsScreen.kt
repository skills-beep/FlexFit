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
import com.flexfit.app.ui.components.WorkoutTypeCard
import com.flexfit.app.ui.components.WorkoutPlanCard
import com.flexfit.app.ui.theme.*

data class WorkoutType(
    val name: String,
    val description: String,
    val icon: ImageVector,
    val color: Color,
    val exercises: Int,
    val duration: String,
    val difficulty: String
)

data class WorkoutPlan(
    val name: String,
    val description: String,
    val duration: String,
    val exercises: Int,
    val calories: String,
    val difficulty: String,
    val type: String,
    val color: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutsScreen() {
    val workoutTypes = remember {
        listOf(
            WorkoutType(
                "Strength Training",
                "Build muscle and increase power",
                Icons.Default.FitnessCenter,
                FitnessBlue,
                12,
                "45-60 min",
                "Intermediate"
            ),
            WorkoutType(
                "Cardio",
                "Improve cardiovascular health",
                Icons.Default.DirectionsRun,
                FitnessGreen,
                8,
                "30-45 min",
                "Beginner"
            ),
            WorkoutType(
                "HIIT",
                "High-intensity interval training",
                Icons.Default.LocalFireDepartment,
                FitnessRed,
                6,
                "20-30 min",
                "Advanced"
            ),
            WorkoutType(
                "Flexibility",
                "Improve flexibility and mobility",
                Icons.Default.SelfImprovement,
                FitnessOrange,
                10,
                "15-30 min",
                "Beginner"
            )
        )
    }

    val workoutPlans = remember {
        listOf(
            WorkoutPlan(
                "Morning Power Session",
                "Start your day with energy",
                "45 min",
                8,
                "400 kcal",
                "Intermediate",
                "Strength",
                FitnessBlue
            ),
            WorkoutPlan(
                "Fat Burn Cardio",
                "Maximize calorie burn",
                "35 min",
                6,
                "350 kcal",
                "Beginner",
                "Cardio",
                FitnessGreen
            ),
            WorkoutPlan(
                "HIIT Blast",
                "Quick intense workout",
                "25 min",
                5,
                "300 kcal",
                "Advanced",
                "HIIT",
                FitnessRed
            ),
            WorkoutPlan(
                "Evening Stretch",
                "Relax and recover",
                "20 min",
                8,
                "120 kcal",
                "Beginner",
                "Flexibility",
                FitnessOrange
            )
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
            WorkoutsHeader()
        }

        // Workout Types
        item {
            Column {
                Text(
                    text = "Workout Types",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(12.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(workoutTypes) { type ->
                        WorkoutTypeCard(workoutType = type)
                    }
                }
            }
        }

        // Featured Workouts
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Featured Workouts",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                TextButton(onClick = { /* Show all workouts */ }) {
                    Text("View All")
                }
            }
        }

        items(workoutPlans) { plan ->
            WorkoutPlanCard(workoutPlan = plan)
        }
    }
}

@Composable
fun WorkoutsHeader() {
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
                            FitnessGreen,
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
                    text = "Ready to Sweat? 💪",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Choose your workout and let's get started!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }
        }
    }
}