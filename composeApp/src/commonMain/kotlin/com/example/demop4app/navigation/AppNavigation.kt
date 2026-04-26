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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.demop4app.screens.SettingsScreen
import com.example.demop4app.settings.SettingsViewModel
import com.example.demop4app.viewmodel.NotesViewModel
import org.koin.compose.koinInject

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val notesViewModel: NotesViewModel = koinInject()
    val settingsViewModel: SettingsViewModel = koinInject()

    val sortOrder by settingsViewModel.sortOrder.collectAsState()

    LaunchedEffect(sortOrder) {
        notesViewModel.updateSortOrder(sortOrder)
    }

    val bottomItems = listOf(
        BottomNavItem.Notes,
        BottomNavItem.Favorites,
        BottomNavItem.Profile,
        BottomNavItem.Settings
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val selectedNote by notesViewModel.selectedNote.collectAsState()

    Scaffold(
        bottomBar = {
            NavigationBar {
                bottomItems.forEach { item ->
                    NavigationBarItem(
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(Screen.Notes.route) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
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
                    viewModel = notesViewModel,
                    onNoteClick = { noteId ->
                        navController.navigate(Screen.NoteDetail.createRoute(noteId))
                    }
                )
            }

            composable(Screen.Favorites.route) {
                FavoritesScreen(
                    viewModel = notesViewModel,
                    onNoteClick = { noteId ->
                        navController.navigate(Screen.NoteDetail.createRoute(noteId))
                    }
                )
            }

            composable(Screen.Profile.route) {
                ProfileScreen(
                    viewModel = notesViewModel
                )
            }

            composable(Screen.Settings.route) {
                SettingsScreen(viewModel = settingsViewModel)
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
                        type = NavType.LongType
                    }
                )
            ) { backStackEntry ->
                val noteId = backStackEntry.arguments?.getLong("noteId") ?: 0L

                LaunchedEffect(noteId) {
                    notesViewModel.selectNote(noteId)
                }

                val note = selectedNote
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
                            notesViewModel.toggleFavorite(
                                id = note.id,
                                isFavorite = note.is_favorite != 1L
                            )
                        }
                    )
                } else {
                    Text("Loading note...")
                }
            }

            composable(
                route = Screen.EditNote.route,
                arguments = listOf(
                    navArgument("noteId") {
                        type = NavType.LongType
                    }
                )
            ) { backStackEntry ->
                val noteId = backStackEntry.arguments?.getLong("noteId") ?: 0L

                LaunchedEffect(noteId) {
                    notesViewModel.selectNote(noteId)
                }

                val note = selectedNote
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
                } else {
                    Text("Loading note...")
                }
            }
        }
    }
}