package com.example.dndcompanionapp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dndcompanionapp.ui.theme.DnDTheme

class HelpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DnDTheme {
                HelpScreen(onBackClick = { finish() })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpScreen(onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Help & Info") },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
            Text(
                text = "App Purpose",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "This app enhances your Dungeons & Dragons gameplay by simplifying character management. " +
                        "With features such as character stats tracking, a dice roller, health management, and condition tracking, " +
                        "you can focus more on the game and less on logistics.",
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Features:",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            BasicText("- Dice Roller: Roll various dice easily.\n" +
                    "- Character Stats: Track Strength, Dexterity, and more.\n" +
                    "- Health Manager: Manage HP with ease.\n" +
                    "- Conditions Overview: Track active effects like Poison or Burn.",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
