package com.flexfit.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.flexfit.app.ui.components.ProfileStatCard
import com.flexfit.app.ui.components.SettingsItem
import com.flexfit.app.ui.theme.*

data class ProfileStat(
    val title: String,
    val value: String,
    val unit: String,
    val icon: ImageVector,
    val color: Color
)

data class SettingsSection(
    val title: String,
    val items: List<SettingsItemData>
)

data class SettingsItemData(
    val title: String,
    val subtitle: String? = null,
    val icon: ImageVector,
    val action: SettingsAction = SettingsAction.Navigate
)

enum class SettingsAction {
    Navigate,
    Toggle,
    Dialog
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {
    val profileStats = remember {
        listOf(
            ProfileStat(
                "Total Workouts",
                "127",
                "sessions",
                Icons.Default.FitnessCenter,
                FitnessBlue
            ),
            ProfileStat(
                "Time Exercised",
                "89",
                "hours",
                Icons.Default.Schedule,
                FitnessGreen
            ),
            ProfileStat(
                "Calories Burned",
                "24.5K",
                "kcal",
                Icons.Default.LocalFireDepartment,
                FitnessRed
            ),
            ProfileStat(
                "Current Streak",
                "12",
                "days",
                Icons.Default.EmojiEvents,
                FitnessOrange
            )
        )
    }

    val settingsSections = remember {
        listOf(
            SettingsSection(
                "Fitness",
                listOf(
                    SettingsItemData(
                        "Workout Preferences",
                        "Customize your exercise routines",
                        Icons.Default.Tune
                    ),
                    SettingsItemData(
                        "Goals & Targets",
                        "Set and manage your fitness goals",
                        Icons.Default.TrackChanges
                    ),
                    SettingsItemData(
                        "Progress Tracking",
                        "View detailed analytics",
                        Icons.Default.Analytics
                    )
                )
            ),
            SettingsSection(
                "App Settings",
                listOf(
                    SettingsItemData(
                        "Notifications",
                        "Manage workout reminders",
                        Icons.Default.Notifications
                    ),
                    SettingsItemData(
                        "Dark Mode",
                        "Toggle dark theme",
                        Icons.Default.DarkMode,
                        SettingsAction.Toggle
                    ),
                    SettingsItemData(
                        "Units",
                        "Metric or Imperial",
                        Icons.Default.Straighten
                    )
                )
            ),
            SettingsSection(
                "Account",
                listOf(
                    SettingsItemData(
                        "Personal Information",
                        "Update your profile",
                        Icons.Default.Person
                    ),
                    SettingsItemData(
                        "Privacy & Security",
                        "Data and privacy settings",
                        Icons.Default.Security
                    ),
                    SettingsItemData(
                        "Help & Support",
                        "Get help or contact us",
                        Icons.Default.Help
                    )
                )
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
        // Profile Header
        item {
            ProfileHeader()
        }

        // Stats Overview
        item {
            Column {
                Text(
                    text = "Your Statistics",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(12.dp))
                
                // Stats grid
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    profileStats.take(2).forEach { stat ->
                        ProfileStatCard(
                            stat = stat,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    profileStats.drop(2).forEach { stat ->
                        ProfileStatCard(
                            stat = stat,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }

        // Settings Sections
        items(settingsSections) { section ->
            Column {
                Text(
                    text = section.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column {
                        section.items.forEachIndexed { index, item ->
                            SettingsItem(
                                item = item,
                                showDivider = index < section.items.size - 1
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileHeader() {
    Card(
        modifier = Modifier.fillMaxWidth(),
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
                            FitnessOrange,
                            FitnessRed
                        )
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(24.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Profile Picture
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Profile Info
                Column {
                    Text(
                        text = "Alex Johnson",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Text(
                        text = "Fitness Enthusiast • Member since 2023",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Quick stats
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.EmojiEvents,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = Color.White.copy(alpha = 0.9f)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Level 8 • 12 day streak",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }
                }
            }
        }
    }
}