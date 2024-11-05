package com.example.bachelorv1.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertBook(book: Book)

    @Delete
    suspend fun deleteBook(book: Book)

    //Queries
    @Query("SELECT * FROM book ORDER BY bookTitle ASC")
    fun getAllBooksOrderedByTitle(): LiveData<List<Book>>

    @Query("SELECT * FROM book ORDER BY bookAddedDate ASC")
    fun getAllBooksOrderedByAddedDate(): LiveData<List<Book>>

    @Query("SELECT * FROM book WHERE bookTitle = :bookTitle ORDER BY bookTitle ASC")
    fun getBookByTitle(bookTitle: String): LiveData<Book>

    @Query("SELECT * FROM book WHERE bookAuthor = :bookAuthor ORDER BY bookAuthor ASC")
    fun getBookByAuthor(bookAuthor: String): LiveData<Book>

    @Query("SELECT * FROM book WHERE locationId = :locationId ORDER BY bookTitle ASC")
    fun getBooksByLocation(locationId: Int): LiveData<List<Book>>

}