package com.example.bachelorv1.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertBook(book: Book)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertBookGenreCrossRef(crossRef: BookGenreCrossRef)

    @Delete
    fun deleteBook(book: Book)

    //Queries
    @Query("SELECT * FROM book ORDER BY bookTitle ASC")
    fun getAllBooksOrderedByTitle(): List<Book>

    @Query("SELECT * FROM book ORDER BY bookAddedDate ASC")
    fun getAllBooksOrderedByAddedDate(): Flow<List<Book>>

    @Query("SELECT * FROM book ORDER BY bookAuthor ASC")
    fun getAllBooksOrderedByAuthor(): Flow<List<Book>>

    @Query("SELECT * FROM book WHERE bookTitle LIKE '%' || :bookTitle || '%' ORDER BY bookTitle ASC")
    fun getBookByTitle(bookTitle: String): List<Book>

    @Query("SELECT * FROM book WHERE bookAuthor = :bookAuthor ORDER BY bookAuthor ASC")
    fun getBookByAuthor(bookAuthor: String): Book

    @Query("SELECT * FROM book WHERE locationId = :locationId ORDER BY bookTitle ASC")
    fun getBooksByLocation(locationId: Int): Flow<List<Book>>

    @Query("SELECT bookId FROM book WHERE bookTitle = :bookTitle AND bookAuthor = :bookAuthor AND locationId = :locationId")
    fun getBookIdByTitleAuthorLocation(bookTitle: String, bookAuthor: String, locationId: Int): Int

    @Query("SELECT * FROM book WHERE bookId = :bookId")
    fun getBookById(bookId: Int): Book

    @Transaction
    @Query("SELECT genreId FROM bookgenre WHERE bookId = :bookId")
    fun getBookWithGenres(bookId: Int): List<Int>
}