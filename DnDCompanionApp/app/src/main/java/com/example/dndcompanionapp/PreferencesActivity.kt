package com.example.dndcompanionapp

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.preference.PreferenceManager
import com.example.dndcompanionapp.ui.theme.DnDTheme

class PreferencesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve SharedPreferences
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)

        setContent {
            // Retrieve the current dark theme state
            val isDarkTheme = preferences.getBoolean("dark_theme", false)

            // Apply the theme
            DnDTheme(darkTheme = isDarkTheme) {
                PreferencesScreen(
                    preferences = preferences,
                    onBackClick = { finish() },
                    onDarkThemeChange = { darkMode ->
                        preferences.edit().putBoolean("dark_theme", darkMode).apply()
                    }
                )
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferencesScreen(
    preferences: SharedPreferences,
    onBackClick: () -> Unit,
    onDarkThemeChange: (Boolean) -> Unit
) {
    var isDarkTheme by remember { mutableStateOf(preferences.getBoolean("dark_theme", false)) }
    var soundEffectsEnabled by remember { mutableStateOf(preferences.getBoolean("sound_effects", true)) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Preferences") },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {  // This triggers the back function
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            // Theme Preference
            Text(text = "Themes and Visual Effects", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Dark Mode", modifier = Modifier.weight(1f))
                Switch(
                    checked = isDarkTheme,
                    onCheckedChange = {
                        isDarkTheme = it
                        onDarkThemeChange(it)  // Pass the new value to update the state in the parent
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sound Effects Preference
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Enable Sound Effects", modifier = Modifier.weight(1f))
                Switch(
                    checked = soundEffectsEnabled,
                    onCheckedChange = {
                        soundEffectsEnabled = it
                        preferences.edit().putBoolean("sound_effects", it).apply()
                    }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Your preferences will be saved automatically and applied when you return to the app.",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
}

