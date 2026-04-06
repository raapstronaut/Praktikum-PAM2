package com.example.demop4app.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.demop4app.screens.AddNoteScreen
import com.example.demop4app.screens.EditNoteScreen
import com.example.demop4app.screens.FavoritesScreen
import com.example.demop4app.screens.NoteDetailScreen
import com.example.demop4app.screens.NotesScreen
import com.example.demop4app.screens.ProfileScreen
import com.example.demop4app.viewmodel.NotesViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val notesViewModel = remember { NotesViewModel() }

    val bottomItems = listOf(
        BottomNavItem.Notes,
        BottomNavItem.Favorites,
        BottomNavItem.Profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                bottomItems.forEach { item ->
                    NavigationBarItem(
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label
                            )
                        },
                        label = {
                            Text(item.label)
                        }
                    )
                }
            }
        },
        floatingActionButton = {
            if (currentRoute == Screen.Notes.route) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(Screen.AddNote.route)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Note"
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Notes.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Notes.route) {
                NotesScreen(
                    notes = notesViewModel.notes,
                    onNoteClick = { noteId ->
                        navController.navigate(Screen.NoteDetail.createRoute(noteId))
                    },
                    onToggleFavorite = { noteId ->
                        notesViewModel.toggleFavorite(noteId)
                    }
                )
            }

            composable(Screen.Favorites.route) {
                FavoritesScreen(
                    notes = notesViewModel.getFavoriteNotes(),
                    onNoteClick = { noteId ->
                        navController.navigate(Screen.NoteDetail.createRoute(noteId))
                    },
                    onToggleFavorite = { noteId ->
                        notesViewModel.toggleFavorite(noteId)
                    }
                )
            }

            composable(Screen.Profile.route) {
                ProfileScreen(
                    totalNotes = notesViewModel.notes.size,
                    totalFavorites = notesViewModel.getFavoriteNotes().size
                )
            }

            composable(Screen.AddNote.route) {
                AddNoteScreen(
                    onSave = { title, content ->
                        notesViewModel.addNote(title, content)
                        navController.popBackStack()
                    },
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable(
                route = Screen.NoteDetail.route,
                arguments = listOf(
                    navArgument("noteId") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0
                val note = notesViewModel.getNoteById(noteId)

                if (note != null) {
                    NoteDetailScreen(
                        note = note,
                        onBack = {
                            navController.popBackStack()
                        },
                        onEditClick = {
                            navController.navigate(Screen.EditNote.createRoute(noteId))
                        },
                        onDeleteClick = {
                            notesViewModel.deleteNote(noteId)
                            navController.popBackStack()
                        },
                        onToggleFavorite = {
                            notesViewModel.toggleFavorite(noteId)
                        }
                    )
                }
            }

            composable(
                route = Screen.EditNote.route,
                arguments = listOf(
                    navArgument("noteId") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0
                val note = notesViewModel.getNoteById(noteId)

                if (note != null) {
                    EditNoteScreen(
                        note = note,
                        onSave = { title, content ->
                            notesViewModel.updateNote(noteId, title, content)
                            navController.popBackStack()
                        },
                        onBack = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}