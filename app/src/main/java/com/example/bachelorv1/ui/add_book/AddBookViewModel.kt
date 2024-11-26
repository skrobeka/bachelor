package com.example.bachelorv1.ui.add_book

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.bachelorv1.data.Book
import com.example.bachelorv1.data.BookDao
import com.example.bachelorv1.data.BookGenreCrossRef
import com.example.bachelorv1.data.Genre
import com.example.bachelorv1.data.GenreDao
import com.example.bachelorv1.data.Location
import com.example.bachelorv1.data.LocationDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
class AddBookViewModel(
    private val bookDao: BookDao,
    private val locationDao: LocationDao,
    private val genreDao: GenreDao
) : ViewModel() {
    val bookTitle: MutableState<String> = mutableStateOf("")
    val authorName: MutableState<String> = mutableStateOf("")

    val genres: List<Genre> = genreDao.getAllGenresOrderedByName()
    val selectedGenres: MutableList<String> = mutableStateListOf<String>()
    val isGenreExpanded: MutableState<Boolean> = mutableStateOf(false)

    val locations: List<Location> = locationDao.getAllLocationsOrderedByName()
    val selectedLocation: MutableState<String> = mutableStateOf("")
    val isLocationExpanded: MutableState<Boolean> = mutableStateOf(false)

    fun toggleGenreDropdown() {
        isGenreExpanded.value = !isGenreExpanded.value
    }

    fun toggleLocationDropdown() {
        isLocationExpanded.value = !isLocationExpanded.value
    }

    fun checkGenreCheckbox(genreName: String) {
        if (selectedGenres.contains(genreName) == false) {
            selectedGenres.add(genreName)
        }
        else {
            selectedGenres.remove(genreName)
        }
    }

    fun checkLocationRadio(locationName: String) {
        selectedLocation.value = locationName
    }

    fun getGenresNames(): List<String> {
        return genres.map { it.genreName }
    }

    fun getLocationsNames(): List<String> {
        return locations.map { it.locationName }
    }

    fun getAddedDate(): String {
        val date = LocalDate.now()
        return date.toString()
    }

    /*
    fun addLocation(locationName: String) {
        locationDao.insertLocation(Location(locationName = locationName))
    }

    fun addGenre(genreName: String) {
        genreDao.insertGenre(Genre(genreName = genreName))
    }
     */


    fun saveBookToLibrary() {
        val title = bookTitle.value
        val author = authorName.value
        val location = locationDao.getLocationIdByName(selectedLocation.value)

        for (genre in selectedGenres) {
            genreDao.getGenreIdByName(genre)
        }

        locationDao.updateLocation(Location(locationName = selectedLocation.value, locationBookCount = locationDao.getLocationBookCountById(location) + 1))
        bookDao.insertBook(Book(bookTitle = title, bookAuthor = author, locationId = location, bookAddedDate = getAddedDate()))

        val bookId = bookDao.getBookIdByTitleAuthorLocation(title, author, location)
        for (genre in selectedGenres) {
            bookDao.insertBookGenreCrossRef(BookGenreCrossRef(bookId, genreDao.getGenreIdByName(genre)))
        }
    }

}