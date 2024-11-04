package com.example.bachelorv1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bachelorv1.ui.add_book.AddBookScreen
import com.example.bachelorv1.ui.book_details.BookDetailsScreen
import com.example.bachelorv1.ui.book_list.BookListScreen
import com.example.bachelorv1.ui.room_list.RoomListScreen
import com.example.bachelorv1.ui.theme.BachelorV1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BachelorV1Theme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "book_list_screen"
                ) {
                    composable("book_list_screen") {
                        BookListScreen(navController = navController)
                    }
                    composable("book_details_screen") {
                        BookDetailsScreen(navController = navController)
                    }
                    composable("room_list_screen") {
                        RoomListScreen(navController = navController)
                    }
                    composable("add_book_screen") {
                        AddBookScreen(navController = navController)
                    }
                }
            }
        }
    }
}