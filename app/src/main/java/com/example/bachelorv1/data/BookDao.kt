package com.example.bachelorv1.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertBook(book: Book)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertBookGenreCrossRef(bookGenres: List<BookGenreCrossRef>)

    @Delete
    fun deleteBook(book: Book)

    //Queries
    @Query("UPDATE book SET bookPhoto = :newPhoto WHERE bookId = :bookId")
    fun updateBookPhoto(bookId: Int, newPhoto: String)

    @Query("UPDATE book SET bookIsFavorite = :isFavorite WHERE bookId = :bookId")
    fun updateBookFavoriteStatus(bookId: Int, isFavorite: Boolean)

    @Query("UPDATE book SET bookIsOnReadingList = :isOnReadingList WHERE bookId = :bookId")
    fun updateBookReadingListStatus(bookId: Int, isOnReadingList: Boolean)

    @Query("UPDATE book SET bookIsRead = :isRead WHERE bookId = :bookId")
    fun updateBookReadStatus(bookId: Int, isRead: Boolean)

    @Query("UPDATE book SET bookTitle = :newTitle WHERE bookId = :bookId")
    fun updateBookTitle(bookId: Int, newTitle: String)

    @Query("UPDATE book SET bookAuthor = :newAuthor WHERE bookId = :bookId")
    fun updateBookAuthor(bookId: Int, newAuthor: String)

    @Query("UPDATE book SET bookNote = :newNote WHERE bookId = :bookId")
    fun updateBookNote(bookId: Int, newNote: String)

    @Query("UPDATE book SET bookCost = :newCost WHERE bookId = :bookId")
    fun updateBookCost(bookId: Int, newCost: String)

    @Query("UPDATE book SET locationId = :newLocationId WHERE bookId = :bookId")
    fun updateBookLocation(bookId: Int, newLocationId: Int)

    @Query("DELETE FROM bookgenre WHERE bookId = :bookId")
    fun deleteBookGenreCrossRef(bookId: Int)

    @Query("SELECT * FROM book ORDER BY bookTitle ASC")
    fun getAllBooksOrderedByTitle(): Flow<List<Book>>

    @Query("SELECT * FROM book ORDER BY bookAddedDate ASC")
    fun getAllBooksOrderedByAddedDate(): Flow<List<Book>>

    @Query("SELECT * FROM book ORDER BY bookAuthor ASC")
    fun getAllBooksOrderedByAuthor(): Flow<List<Book>>

    @Query("SELECT * FROM book WHERE bookTitle LIKE '%' || :bookTitle || '%' ORDER BY bookTitle ASC")
    fun getBookByTitle(bookTitle: String): List<Book>

    @Query("SELECT * FROM book WHERE bookTitle LIKE '%' || :bookTitle || '%' AND bookIsFavorite = 1 ORDER BY bookTitle ASC")
    fun getFavoriteBookByTitle(bookTitle: String): List<Book>

    @Query("SELECT * FROM book WHERE bookTitle LIKE '%' || :bookTitle || '%' AND locationId = :locationId ORDER BY bookTitle ASC")
    fun getLocationBookByTitle(locationId: Int, bookTitle: String): List<Book>

    @Query("SELECT * FROM book WHERE bookAuthor = :bookAuthor ORDER BY bookAuthor ASC")
    fun getBookByAuthor(bookAuthor: String): Book

    @Query("SELECT * FROM book WHERE locationId = :locationId ORDER BY bookTitle ASC")
    fun getBooksByLocation(locationId: Int): Flow<List<Book>>

    @Query("SELECT bookId FROM book WHERE bookTitle = :bookTitle AND bookAuthor = :bookAuthor AND locationId = :locationId")
    fun getBookIdByTitleAuthorLocation(bookTitle: String, bookAuthor: String, locationId: Int): Int

    @Query("SELECT * FROM book WHERE bookId = :bookId")
    fun getBookById(bookId: Int): Book

    @Query("SELECT bookTitle FROM book WHERE bookId = :bookId")
    fun getBookTitleById(bookId: Int): String

    @Query("SELECT bookIsFavorite FROM book WHERE bookId = :bookId")
    fun isBookFavorite(bookId: Int): Flow<Boolean>

    @Query("SELECT bookIsOnReadingList FROM book WHERE bookId = :bookId")
    fun isBookOnReadingList(bookId: Int): Flow<Boolean>

    @Query("SELECT bookIsRead FROM book WHERE bookId = :bookId")
    fun isBookRead(bookId: Int): Flow<Boolean>

    @Query("SELECT * FROM book WHERE bookIsFavorite = 1 ORDER BY bookTitle ASC")
    fun getFavoriteBooks(): Flow<List<Book>>

    @Query("SELECT * FROM book WHERE bookIsOnReadingList = 1 ORDER BY bookTitle ASC")
    fun getReadingListBooks(): Flow<List<Book>>

    @Transaction
    @Query("SELECT genreId FROM bookgenre WHERE bookId = :bookId")
    fun getBookWithGenres(bookId: Int): List<Int>
}