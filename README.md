# DatabaseRecyclerView

## Description

This is an Android application designed to manage user details. The app allows users to input, update, and display user information such as name, age, and ID. The application uses a SQLite database to store user information and employs a RecyclerView to display the list of users.

## Features

- Add new users with name, age, and ID.
- Update existing user details.
- Display all users in a RecyclerView.

## Project Structure

The project consists of the following key components:

### MainActivity.kt
This is the main activity that handles user interactions and displays the user list.

### DatabaseAdapter.kt
This file contains the adapter for the RecyclerView to bind user data to the view.

### DatabaseConstants.kt
This file holds constant values related to the database, such as table and column names.

### DatabaseHelper.kt
This file contains the helper class for managing the SQLite database, including methods for adding, updating, and retrieving user data.

### MyUser.kt
This file defines the data model for a user.

### activity_main.xml
This layout file defines the user interface for the main activity, including input fields and buttons for saving and updating user details, and a RecyclerView for displaying the user list.

### user_item.xml
This layout file defines the user interface for individual items in the RecyclerView.

## Installation

1. Clone the repository.
2. Open the project in Android Studio.
3. Build and run the project on an Android device or emulator.

## Usage

1. Enter the user's name, age, and ID in the respective input fields.
2. Click "Save" to add the user to the database.
3. To update a user, modify the details and click "Update".
4. View the list of users in the RecyclerView.

