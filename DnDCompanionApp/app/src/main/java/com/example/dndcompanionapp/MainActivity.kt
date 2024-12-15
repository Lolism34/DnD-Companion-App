package com.example.dndcompanionapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Button
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ButtonDefaults
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Card
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Checkbox
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.IconButton
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.LocalTextStyle
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.MaterialTheme
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.OutlinedTextField
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Surface
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.preference.PreferenceManager
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.dndcompanionapp.ui.theme.DnDTheme
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlin.random.Random


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve SharedPreferences
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)

        setContent {
            // Retrieve the user's dark mode setting
            val isDarkTheme = preferences.getBoolean("dark_theme", false)

            // Wrap your app in the DnDTheme
            DnDTheme(darkTheme = isDarkTheme) {
                MainActivityScreen()
            }
        }
    }
}

@Composable

fun AppBackground(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.redragon),
            contentDescription = "Red Dragon Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // Ensures the image covers the screen without distortion
        )

        // Your content goes here, overlaid on top of the background
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp), // Adjust the padding for your content
            color = Color.Black.copy(alpha = 0.5f) // Optional: You can add a transparent overlay to improve readability
        ) {
            content()
        }
    }
}

@Composable

fun MainActivityScreen() {
    var navigateToCharacterSheet by remember { mutableStateOf(false) }
    val context = LocalContext.current // Get the context for launching HelpActivity

    if (navigateToCharacterSheet) {
        CharacterSheetScreen1(onBackClick = { navigateToCharacterSheet = false })
    } else {
        // Wrap the Column with verticalScroll to make it scrollable
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(MaterialTheme.colors.background)
                .verticalScroll(rememberScrollState()), // Make it scrollable
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            DiceRollerSection()
            InventorySection()
            SkillTreeSection()
            SpellbookSection()

            Spacer(modifier = Modifier.weight(1f)) // Push buttons to the bottom

            // Export Data to CSV button with improved styling
            Button(
                onClick = {
                    val csvData = convertDataToCSV(
                        inventory = listOf("Sword", "Health Potion", "Shield"), // Replace with actual data
                        skills = mapOf("Swordsmanship" to 1, "Magic" to 0, "Stealth" to 3), // Replace with actual data
                        spells = mapOf("Fireball" to 20, "Heal" to 10, "Ice Blast" to 25), // Replace with actual data
                        characterStats = mapOf("Strength" to 10, "Dexterity" to 10, "Constitution" to 10) // Replace with actual data
                    )
                    writeCSVToFile(context, csvData)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp) // Adjust button height for better usability
                    .clip(RoundedCornerShape(16.dp)) // Rounded corners for modern look
                    .background(MaterialTheme.colors.primary) // Button background color
                    .padding(8.dp), // Internal padding
                elevation = ButtonDefaults.elevation(8.dp) // Elevation for a shadow effect
            ) {
                Text("Export Data to CSV", fontSize = 18.sp, color = Color.White) // White text color for contrast
            }

            // Go to Character Sheet button with improved styling
            Button(
                onClick = { navigateToCharacterSheet = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colors.secondary)
                    .padding(8.dp),
                elevation = ButtonDefaults.elevation(8.dp)
            ) {
                Text("Go to Character Sheet", fontSize = 18.sp, color = Color.White)
            }

            // Help & Info button with icon and improved styling
            Button(
                onClick = {
                    context.startActivity(Intent(context, HelpActivity::class.java))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colors.secondary)
                    .padding(8.dp),
                elevation = ButtonDefaults.elevation(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Info, // Icon for Help & Info
                    contentDescription = "Help & Info",
                    modifier = Modifier.padding(end = 8.dp), // Space between icon and text
                    tint = Color.White
                )
                Text("Help & Info", fontSize = 18.sp, color = Color.White)
            }

            // Preferences button with icon and improved styling
            Button(
                onClick = {
                    context.startActivity(Intent(context, PreferencesActivity::class.java))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colors.secondary)
                    .padding(8.dp),
                elevation = ButtonDefaults.elevation(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Settings, // Icon for Preferences
                    contentDescription = "Preferences",
                    modifier = Modifier.padding(end = 8.dp),
                    tint = Color.White
                )
                Text("Preferences", fontSize = 18.sp, color = Color.White)
            }
        }
    }
}



@Composable
fun CharacterSheetScreen() {
    // Your character sheet UI components go here
    Card(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .background(MaterialTheme.colors.surface)
        ) {
            Text(
                text = "Character Sheet",
                fontSize = 30.sp,
                color = MaterialTheme.colors.primary
            )

            // Add other character sheet components here
        }
    }
}

@Composable
fun AnimatedImage() {
    // Remembering the image painter for animated .webp or .gif
    rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(R.drawable.dice) // Replace with your animated image file name
            .build()
    )
}

@Composable
fun DiceRollerSection() {
    val diceTypes = listOf("D4", "D6", "D8", "D10", "D12", "D20", "D100")
    var selectedDice by remember { mutableStateOf(diceTypes[0]) }
    var numberOfDice by remember { mutableIntStateOf(1) }
    var rollResults by remember { mutableStateOf<List<Int>>(emptyList()) }
    var rollHistory by remember { mutableStateOf<List<List<Int>>>(emptyList()) }

    // Function to roll dice
    fun rollDice(sides: Int, numberOfRolls: Int): List<Int> {
        return List(numberOfRolls) { Random.nextInt(1, sides + 1) }
    }

    // Mapping dice types to number of sides
    val diceSides = mapOf(
        "D4" to 4,
        "D6" to 6,
        "D8" to 8,
        "D10" to 10,
        "D12" to 12,
        "D20" to 20,
        "D100" to 100
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), // Increased padding for better spacing
        elevation = 8.dp, // A higher elevation for better card shadow
        shape = RoundedCornerShape(12.dp) // Rounded corners for a softer look
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp) // Added more internal padding for breathing space
                .background(MaterialTheme.colors.surface)
        ) {
            // Title with a larger font size and bold text
            Text(
                text = "Roll The Die",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.primaryVariant,
                modifier = Modifier.padding(bottom = 16.dp) // Added space between title and content
            )

            // Dice type selection with an updated design (clickable)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        selectedDice = diceTypes[(diceTypes.indexOf(selectedDice) + 1) % diceTypes.size]
                    }
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.dice), // Reference to groku.png
                    contentDescription = "Dice Icon",
                    modifier = Modifier.size(100.dp) // Set image size
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = selectedDice,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary
                )
            }

            // Number of dice input field with padding and larger text size
            OutlinedTextField(
                value = numberOfDice.toString(),
                onValueChange = { numberOfDice = it.toIntOrNull() ?: 1 },
                label = {
                    Text(
                        text = "Number of Dice",
                        fontSize = 20.sp,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                    )
                },
                textStyle = LocalTextStyle.current.copy(fontSize = 20.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )

            // Roll button with a large, colorful background and rounded corners
            Button(
                onClick = {
                    val diceSidesCount = diceSides[selectedDice] ?: 6
                    val results = rollDice(diceSidesCount, numberOfDice)
                    rollResults = results

                    // Save the roll result into history (keep last 5 rolls)
                    rollHistory = (listOf(results) + rollHistory).take(5)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
            ) {
                Text("Roll Dice", fontSize = 20.sp, color = MaterialTheme.colors.onPrimary)
            }

            // Display Roll Results with custom text size
            if (rollResults.isNotEmpty()) {
                Text(
                    text = "Roll Results: ${rollResults.joinToString(", ")}",
                    fontSize = 24.sp, // Adjusted font size for the roll results
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.secondary,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            // Display Roll History with a separator line
            DiceRollHistory(rollHistory)
        }
    }
}

@Composable
fun DiceRollHistory(rollHistory: List<List<Int>>) {
    Column(
        modifier = Modifier.padding(top = 16.dp)
    ) {
        Text(
            text = "Roll History",
            fontSize = 24.sp, // You can adjust this as needed
            color = MaterialTheme.colors.primary
        )

        // Display each roll result with a smaller font size of 20.sp
        rollHistory.forEach { roll ->
            Text(
                text = "Roll: ${roll.joinToString(", ")}",
                fontSize = 20.sp, // Set font size of 20.sp for history text
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}


@Composable
fun CharacterStatsSummarySection() {
    var expanded by remember { mutableStateOf(false) }

    var strength by remember { mutableIntStateOf(10) }
    var dexterity by remember { mutableIntStateOf(10) }
    var constitution by remember { mutableIntStateOf(10) }
    var intelligence by remember { mutableIntStateOf(10) }
    var wisdom by remember { mutableIntStateOf(10) }
    var charisma by remember { mutableIntStateOf(10) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .background(MaterialTheme.colors.surface)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
            ) {
                Text(
                    text = "Character Stats Summary",
                    fontSize = 30.sp,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.weight(1f)
                )
               // Icon(
                   // imageVector = if (expanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
                   // contentDescription = "Expand/Collapse",
                   // tint = MaterialTheme.colors.primary
                //)
            }

            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                // Example character stats, could be dynamically loaded
                StatRow(statName = "Strength", statValue = strength, onIncrement = { if (strength < 99) strength++ }, onDecrement = { if (strength > 1) strength-- })
                StatRow(statName = "Dexterity", statValue = dexterity, onIncrement = { if (dexterity < 99) dexterity++ }, onDecrement = { if (dexterity > 1) dexterity-- })
                StatRow(statName = "Constitution", statValue = constitution, onIncrement = { if (constitution < 99) constitution++ }, onDecrement = { if (constitution > 1) constitution-- })
                StatRow(statName = "Intelligence", statValue = intelligence, onIncrement = { if (intelligence < 99) intelligence++ }, onDecrement = { if (intelligence > 1) intelligence-- })
                StatRow(statName = "Wisdom", statValue = wisdom, onIncrement = { if (wisdom < 99) wisdom++ }, onDecrement = { if (wisdom > 1) wisdom-- })
                StatRow(statName = "Charisma", statValue = charisma, onIncrement = { if (charisma < 99) charisma++ }, onDecrement = { if (charisma > 1) charisma-- })
            }
        }
    }
}

@Composable
fun ConditionsOverviewSection() {
    var expanded by remember { mutableStateOf(false) }
    val conditions = listOf("Paralyze", "Poison", "Burn")
    val selectedConditions = remember { mutableStateListOf<String>() }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .background(MaterialTheme.colors.surface)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
            ) {
                Text(
                    text = "Conditions Overview",
                    fontSize = 30.sp,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.weight(1f)
                )
               // Icon(
                    //imageVector = if (expanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
                   // contentDescription = "Expand/Collapse",
                    //tint = MaterialTheme.colors.primary
                //)
            }

            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))

                // List of conditions with checkboxes
                conditions.forEach { condition ->
                    val isSelected = selectedConditions.contains(condition)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Checkbox(
                            checked = isSelected,
                            onCheckedChange = {
                                if (isSelected) {
                                    selectedConditions.remove(condition)
                                } else {
                                    selectedConditions.add(condition)
                                }
                            }
                        )
                        Text(condition, fontSize = 24.sp)
                    }
                }
            }
        }
    }
}
@Composable
fun InventorySection() {
    var inventory by remember { mutableStateOf(listOf("Sword", "Health Potion", "Shield")) }
    var newItem by remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 6.dp,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(Brush.linearGradient(listOf(Color(0xFF8E44AD), Color(0xFF9B59B6))))

        ) {
            Text(
                text = "Inventory",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            inventory.forEachIndexed { index, item ->
                var editedItem by remember { mutableStateOf(item) }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextField(
                        value = editedItem,
                        onValueChange = { editedItem = it },
                        label = { Text("Item Name") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    )

                    IconButton(onClick = {
                        inventory = inventory.toMutableList().apply { set(index, editedItem) }
                    }) {
                        Icon(imageVector = Icons.Default.Done, contentDescription = "Save", tint = Color.White)
                    }

                    IconButton(onClick = {
                        inventory = inventory - item
                    }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Remove", tint = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Add a new item section
            TextField(
                value = newItem,
                onValueChange = { newItem = it },
                label = { Text("New Item") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (newItem.isNotBlank()) {
                        inventory = inventory + newItem
                        newItem = "" // Clear the input field after adding
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF9B59B6))
            ) {
                Text("Add Item", color = Color.White)
            }
        }
    }
}
@SuppressLint("AutoboxingStateCreation")
@Composable
fun SkillTreeSection() {
    var skills by remember { mutableStateOf(mapOf("Swordsmanship" to 1, "Magic" to 0, "Stealth" to 3)) }
    var newSkillName by remember { mutableStateOf("") }
    var newSkillLevel by remember { mutableIntStateOf(1) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 6.dp,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(Brush.linearGradient(listOf(Color(0xFF16A085), Color(0xFF1ABC9C))))

        ) {
            Text(
                text = "Skill Tree",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            skills.forEach { (skill, level) ->
                var editedSkillName by remember { mutableStateOf(skill) }
                var editedSkillLevel by remember { mutableIntStateOf(level) }

                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    TextField(
                        value = editedSkillName,
                        onValueChange = { editedSkillName = it },
                        label = { Text("Skill Name") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    )

                    TextField(
                        value = editedSkillLevel.toString(),
                        onValueChange = { editedSkillLevel = it.toIntOrNull() ?: level },
                        label = { Text("Level") },
                        modifier = Modifier.width(80.dp),
                        shape = RoundedCornerShape(8.dp)
                    )

                    IconButton(onClick = {
                        skills = skills + (editedSkillName to editedSkillLevel)
                    }) {
                        Icon(imageVector = Icons.Default.Done, contentDescription = "Save", tint = Color.White)
                    }

                    IconButton(onClick = {
                        skills = skills - skill
                    }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Remove", tint = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Add a new skill section
            TextField(
                value = newSkillName,
                onValueChange = { newSkillName = it },
                label = { Text("New Skill Name") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            )
            TextField(
                value = newSkillLevel.toString(),
                onValueChange = { newSkillLevel = it.toIntOrNull() ?: 1 },
                label = { Text("Starting Level") },
                modifier = Modifier.width(100.dp),
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (newSkillName.isNotBlank()) {
                        skills = skills + (newSkillName to newSkillLevel)
                        newSkillName = "" // Clear input fields after adding
                        newSkillLevel = 1
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1ABC9C))
            ) {
                Text("Add Skill", color = Color.White)
            }
        }
    }
}
@Composable
fun SpellbookSection() {
    var spells by remember { mutableStateOf(mapOf("Fireball" to 20, "Heal" to 10, "Ice Blast" to 25)) }
    var newSpellName by remember { mutableStateOf("") }
    var newSpellManaCost by remember { mutableIntStateOf(10) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 6.dp,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(Brush.linearGradient(listOf(Color(0xFFEE5F5B), Color(0xFFFC4A3F))))

        ) {
            Text(
                text = "Spellbook",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            spells.forEach { (spell, manaCost) ->
                var editedSpellName by remember { mutableStateOf(spell) }
                var editedSpellManaCost by remember { mutableIntStateOf(manaCost) }

                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    TextField(
                        value = editedSpellName,
                        onValueChange = { editedSpellName = it },
                        label = { Text("Spell Name") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    )

                    TextField(
                        value = editedSpellManaCost.toString(),
                        onValueChange = { editedSpellManaCost = it.toIntOrNull() ?: manaCost },
                        label = { Text("Mana Cost") },
                        modifier = Modifier.width(80.dp),
                        shape = RoundedCornerShape(8.dp)
                    )

                    IconButton(onClick = {
                        spells = spells + (editedSpellName to editedSpellManaCost)
                    }) {
                        Icon(imageVector = Icons.Default.Done, contentDescription = "Save", tint = Color.White)
                    }

                    IconButton(onClick = {
                        spells = spells - spell
                    }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Remove", tint = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Add a new spell section
            TextField(
                value = newSpellName,
                onValueChange = { newSpellName = it },
                label = { Text("New Spell Name") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            )
            TextField(
                value = newSpellManaCost.toString(),
                onValueChange = { newSpellManaCost = it.toIntOrNull() ?: 10 },
                label = { Text("Mana Cost") },
                modifier = Modifier.width(100.dp),
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (newSpellName.isNotBlank()) {
                        spells = spells + (newSpellName to newSpellManaCost)
                        newSpellName = "" // Clear input fields after adding
                        newSpellManaCost = 10
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFC4A3F))
            ) {
                Text("Add Spell", color = Color.White)
            }
        }
    }
}
fun writeCSVToFile(context: Context, csvData: String) {
    try {
        // Scoped storage: Get the app's directory for storing documents
        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "character_data.csv")

        // Create the file if it doesn't exist
        if (!file.exists()) {
            file.createNewFile()
        }

        // Write the CSV data to the file
        val fileOutputStream = FileOutputStream(file)
        fileOutputStream.write(csvData.toByteArray())
        fileOutputStream.close()

        // Optionally, notify the user that the file has been saved
        Toast.makeText(context, "Data exported to ${file.absolutePath}", Toast.LENGTH_LONG).show()

    } catch (e: IOException) {
        e.printStackTrace()
        Toast.makeText(context, "Error exporting data", Toast.LENGTH_LONG).show()
    }
}
fun convertDataToCSV(
    inventory: List<String>,
    skills: Map<String, Int>,
    spells: Map<String, Int>,
    characterStats: Map<String, Int>
): String {
    val stringBuilder = StringBuilder()

    // Add headers
    stringBuilder.append("Category,Name,Value\n")

    // Inventory Data
    inventory.forEach {
        stringBuilder.append("Inventory,$it,0\n")
    }

    // Skills Data
    skills.forEach { (skill, level) ->
        stringBuilder.append("Skill,$skill,$level\n")
    }

    // Spells Data
    spells.forEach { (spell, manaCost) ->
        stringBuilder.append("Spell,$spell,$manaCost\n")
    }

    // Character Stats Data
    characterStats.forEach { (stat, value) ->
        stringBuilder.append("CharacterStat,$stat,$value\n")
    }

    return stringBuilder.toString()
}
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    DnDTheme {
        MainActivityScreen()
    }
}
