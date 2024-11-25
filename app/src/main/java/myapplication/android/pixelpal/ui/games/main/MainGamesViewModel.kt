package myapplication.android.pixelpal.ui.games.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import myapplication.android.pixelpal.di.DiContainer
import myapplication.android.pixelpal.ui.games.model.GenreUiDescription
import myapplication.android.pixelpal.ui.games.model.toUi

class MainGamesViewModel: ViewModel() {

    private val _genres: MutableSharedFlow<GenreUiDescription> = MutableSharedFlow()
    val genre get() = _genres.asSharedFlow()

    fun getGenreDescription(id: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val descriptionDomain = DiContainer.getGenresDescriptionUseCase.invoke(id)
                _genres.emit(descriptionDomain.toUi())
            }
        }
    }

}