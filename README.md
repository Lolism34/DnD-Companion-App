# DnD Companion App
 
    Introduction

The DnD Companion App is a comprehensive tool for players of Dungeons & Dragons to manage their character sheets, track health, update stats, and manage conditions during gameplay. It is built using Android's Jetpack Compose and adheres to Material Design principles, ensuring a clean and user-friendly interface.

    Features

Character Sheet Management: Easily input and update character information, including name, story, and stats.

Health Tracker: Adjust your character's health points dynamically, with validation to ensure values stay within allowable ranges.

Stat Management: Increment or decrement core stats like Strength, Dexterity, and more.

Conditions Management: Toggle various conditions (e.g., Paralyze, Poison, Burn) and view their descriptions.

Scroll to Top: Quickly return to the top of the screen using a floating action button.

Persistent Data Storage: Save character name and story using SharedPreferences.

    Technologies Used

Kotlin

Jetpack Compose

Material Design

SharedPreferences for local data persistence

Coroutines for smooth UI updates

    Installation

Clone the repository:

git clone <[repository-url](https://github.com/Lolism34/DnD-Companion-App.git)>

Open the project in Android Studio.

Build the project and install it on an Android device or emulator.

    How to Use

Launch the app on your Android device.

Use the Character Sheet to input your character's details.

Manage your health using the Health Tracker.

Update core stats in the Character Stats Section.

Track and manage conditions from the Conditions Management panel.

Save your progress, and enjoy seamless integration during your game sessions!

    Screens Overview

Character Sheet Screen

The main screen of the app, which includes sections for managing all character-related information, including stats, health, and conditions.

Health Tracker

Adjust your character's maximum and current HP.

Apply damage or healing dynamically with the provided buttons.

Input validation ensures current HP does not exceed maximum HP or drop below 0.

Character Stats Section

Manage six core stats: Strength, Dexterity, Constitution, Intelligence, Wisdom, and Charisma.

Increment or decrement stats with dedicated buttons, with values constrained between 0 and 99.

    Character Name and Story Panel

Input and save the character's name and story.

Persistent storage ensures data is saved between app sessions.

Updates are confirmed via Toast messages.

    Conditions Management

Track and toggle conditions like "Paralyze," "Poison," and "Burn."

View brief descriptions of each condition.

Use checkboxes to select active conditions.

    Future Enhancements


Implement a combat tracker for initiative and round management.

Sync data with cloud storage for multi-device support.

Expand conditions list with more customizable options.

    Contributing

Contributions are welcome! If you have ideas or bug fixes, please fork the repository and submit a pull request. For major changes, please open an issue first to discuss the proposed updates.

License

This project is licensed under the MIT License. See the LICENSE file for details.

