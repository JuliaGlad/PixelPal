package myapplication.android.pixelpal.ui.games.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import myapplication.android.pixelpal.data.repository.genres.GenresRepository
import myapplication.android.pixelpal.ui.games.model.GenreUiDescription
import myapplication.android.pixelpal.ui.games.model.toUi

class MainGamesViewModel @AssistedInject constructor(
    private val genresRepository: GenresRepository
): ViewModel() {

    @AssistedFactory
    interface MainGamesViewModelAssistedFactory{
        fun create(): MainGamesViewModel
    }

    class Factory(
        private val factory: MainGamesViewModelAssistedFactory
    ): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return factory.create() as T
        }
    }

    private val _genres: MutableSharedFlow<GenreUiDescription> = MutableSharedFlow()
    val genre get() = _genres.asSharedFlow()

    fun getGenreDescription(id: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val descriptionDomain = genresRepository.getGenresDescription(id)
                _genres.emit(descriptionDomain.toUi())
            }
        }
    }

}