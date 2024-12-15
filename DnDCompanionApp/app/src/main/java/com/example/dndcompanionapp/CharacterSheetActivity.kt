package com.example.dndcompanionapp

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dndcompanionapp.ui.theme.DnDTheme
import kotlinx.coroutines.launch

class CharacterSheetActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DnDTheme {
                CharacterSheetScreen1(onBackClick = { finish() })
            }
        }
    }
}

@Composable
fun CharacterSheetScreen1(onBackClick: () -> Unit) {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(MaterialTheme.colors.background)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            HealthTrackerSection()
            CharacterNameAndHPPanel()
            CharacterStatsSection()
            ConditionsManagementSection()

            Spacer(modifier = Modifier.weight(1f))

            // Back button with a more vibrant design
            Button(
                onClick = onBackClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clip(RoundedCornerShape(16.dp)),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                elevation = ButtonDefaults.elevation(8.dp)
            ) {
                Text(
                    "Back to Main Screen",
                    fontSize = 20.sp,
                    color = MaterialTheme.colors.onPrimary
                )
            }

        }

        // Floating Action Button with a cool animation effect
        FloatingActionButton(
            onClick = {
                coroutineScope.launch { scrollState.animateScrollTo(0) }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .graphicsLayer {
                    scaleX = 1.2f
                    scaleY = 1.2f
                },
            shape = CircleShape,
            backgroundColor = MaterialTheme.colors.primary
        ) {
            Icon(imageVector = Icons.Filled.KeyboardArrowUp, contentDescription = "Scroll to Top")
        }
    }
}
@Composable
fun ConditionsManagementSection() {
    var expanded by remember { mutableStateOf(false) }
    var showDropdown by remember { mutableStateOf(false) }
    val conditions = listOf(
        "Paralyze" to "Unable to move",
        "Poison" to "Disadvantage on attacks",
        "Burn" to "Takes fire damage over time",
        "Bleeding" to "Takes damage over time",
        "Stunned" to "Cannot take actions"
    )
    val selectedConditions = remember { mutableStateListOf<String>() }

    // State for the selected condition from the dropdown
    var selectedCondition by remember { mutableStateOf<String?>(null) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 12.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(MaterialTheme.colors.surface)
        ) {
            // Title with clickable effect for expanding the section
            Text(
                text = "Conditions Management",
                fontSize = 32.sp,
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .padding(bottom = 8.dp),
                style = TextStyle(textAlign = TextAlign.Start)
            )

            // Dropdown to select a condition
            if (expanded) {
                // Dropdown menu for selecting a condition
                Box(modifier = Modifier.fillMaxWidth()) {
                    TextButton(onClick = { showDropdown = !showDropdown }) {
                        Text(
                            text = "Select Condition",
                            fontSize = 18.sp,
                            color = MaterialTheme.colors.primary
                        )
                    }

                    DropdownMenu(
                        expanded = showDropdown,
                        onDismissRequest = { showDropdown = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        conditions.forEach { (condition, _) ->
                            DropdownMenuItem(onClick = {
                                selectedCondition = condition
                                showDropdown = false
                                if (!selectedConditions.contains(condition)) {
                                    selectedConditions.add(condition)
                                }
                            }) {
                                Text(text = condition)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Display the selected conditions in a table-like structure
                selectedConditions.forEach { condition ->
                    val description = conditions.find { it.first == condition }?.second ?: ""
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colors.onSurface.copy(alpha = 0.1f)) // Background for each row
                            .padding(12.dp)  // Padding for better spacing
                    ) {
                        // Display the condition and description
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = condition,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colors.primary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = description,
                                fontSize = 14.sp,
                                color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
                                style = TextStyle(textAlign = TextAlign.Start)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CharacterStatsSection() {
    var expanded by remember { mutableStateOf(false) }
    var strength by remember { mutableIntStateOf(10) }
    var dexterity by remember { mutableIntStateOf(12) }
    var constitution by remember { mutableIntStateOf(14) }
    var intelligence by remember { mutableIntStateOf(13) }
    var wisdom by remember { mutableIntStateOf(11) }
    var charisma by remember { mutableIntStateOf(15) }

    fun updateStat(stat: String, increment: Boolean) {
        val maxStatValue = 99
        when (stat) {
            "Strength" -> strength = if (increment) minOf(strength + 1, maxStatValue) else maxOf(strength - 1, 0)
            "Dexterity" -> dexterity = if (increment) minOf(dexterity + 1, maxStatValue) else maxOf(dexterity - 1, 0)
            "Constitution" -> constitution = if (increment) minOf(constitution + 1, maxStatValue) else maxOf(constitution - 1, 0)
            "Intelligence" -> intelligence = if (increment) minOf(intelligence + 1, maxStatValue) else maxOf(intelligence - 1, 0)
            "Wisdom" -> wisdom = if (increment) minOf(wisdom + 1, maxStatValue) else maxOf(wisdom - 1, 0)
            "Charisma" -> charisma = if (increment) minOf(charisma + 1, maxStatValue) else maxOf(charisma - 1, 0)
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 8.dp,
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(MaterialTheme.colors.surface)
        ) {
            Text(
                text = "Character Stats",
                fontSize = 28.sp,
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .padding(bottom = 8.dp)
            )

            if (expanded) {
                StatRow(statName = "Strength", statValue = strength, onIncrement = { updateStat("Strength", true) }, onDecrement = { updateStat("Strength", false) })
                StatRow(statName = "Dexterity", statValue = dexterity, onIncrement = { updateStat("Dexterity", true) }, onDecrement = { updateStat("Dexterity", false) })
                StatRow(statName = "Constitution", statValue = constitution, onIncrement = { updateStat("Constitution", true) }, onDecrement = { updateStat("Constitution", false) })
                StatRow(statName = "Intelligence", statValue = intelligence, onIncrement = { updateStat("Intelligence", true) }, onDecrement = { updateStat("Intelligence", false) })
                StatRow(statName = "Wisdom", statValue = wisdom, onIncrement = { updateStat("Wisdom", true) }, onDecrement = { updateStat("Wisdom", false) })
                StatRow(statName = "Charisma", statValue = charisma, onIncrement = { updateStat("Charisma", true) }, onDecrement = { updateStat("Charisma", false) })
            }
        }
    }
}

@Composable
fun StatRow(statName: String, statValue: Int, onIncrement: () -> Unit, onDecrement: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colors.onSurface.copy(alpha = 0.1f))
    ) {
        Text(
            text = "$statName: $statValue",
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colors.primary
        )

        IconButton(onClick = onDecrement) {
            Icon(
                imageVector = Icons.Filled.Clear,
                contentDescription = "Decrement",
                tint = MaterialTheme.colors.primary
            )
        }

        IconButton(onClick = onIncrement) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Increment",
                tint = MaterialTheme.colors.primary
            )
        }
    }
}

@Composable
fun CharacterNameAndHPPanel() {
    val context = LocalContext.current
    val sharedPreferences = remember {
        context.getSharedPreferences("CharacterData", Context.MODE_PRIVATE)
    }

    var characterName by remember {
        mutableStateOf(sharedPreferences.getString("characterName", "Character Name") ?: "Character Name")
    }
    var characterStory by remember {
        mutableStateOf(sharedPreferences.getString("characterStory", "Write your story here...") ?: "Write your story here...")
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 12.dp,
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(MaterialTheme.colors.surface)
        ) {
            TextField(
                value = characterName,
                onValueChange = {
                    characterName = it
                    sharedPreferences.edit().putString("characterName", characterName).apply()
                    Toast.makeText(context, "Character Name Saved", Toast.LENGTH_SHORT).show()
                },
                label = { Text("Character Name", fontSize = 18.sp) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.body1.copy(fontSize = 28.sp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Character Story",
                fontSize = 20.sp,
                color = MaterialTheme.colors.primary
            )

            TextField(
                value = characterStory,
                onValueChange = {
                    characterStory = it
                    sharedPreferences.edit().putString("characterStory", characterStory).apply()
                    Toast.makeText(context, "Story Saved", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                textStyle = MaterialTheme.typography.body1.copy(fontSize = 16.sp),
                placeholder = { Text("Write your story here...", fontStyle = FontStyle.Italic) }
            )
        }
    }
}

@Composable
fun HealthTrackerSection() {
    var expanded by remember { mutableStateOf(false) }
    val maxHp by remember { mutableIntStateOf(100) }
    var currentHp by remember { mutableIntStateOf(100) }

    fun updateHealth(increase: Boolean) {
        val maxHealthValue = 999
        if (increase) {
            currentHp = minOf(currentHp + 10, maxHp)
        } else {
            currentHp = maxOf(currentHp - 10, 0)
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 8.dp,
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(MaterialTheme.colors.surface)
        ) {
            Text(
                text = "Health Tracker",
                fontSize = 28.sp,
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .padding(bottom = 8.dp)
            )

            if (expanded) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "HP: $currentHp / $maxHp",
                        fontSize = 20.sp,
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(onClick = { updateHealth(false) }) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "Decrease Health",
                            tint = MaterialTheme.colors.primary
                        )
                    }

                    IconButton(onClick = { updateHealth(true) }) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Increase Health",
                            tint = MaterialTheme.colors.primary
                        )
                    }
                }
            }
        }
    }
}
