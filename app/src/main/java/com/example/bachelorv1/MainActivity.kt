package com.example.bachelorv1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.bachelorv1.data.Database
import com.example.bachelorv1.ui.add_book.AddBookScreen
import com.example.bachelorv1.ui.book_details.BookDetailsScreen
import com.example.bachelorv1.ui.book_list.BookListScreen
import com.example.bachelorv1.ui.room_list.RoomListScreen
import com.example.bachelorv1.ui.theme.BachelorV1Theme

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            Database::class.java,
            "database.db"
        ).build()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BachelorV1Theme {
                BookDetailsScreen()
            }
        }
    }
}