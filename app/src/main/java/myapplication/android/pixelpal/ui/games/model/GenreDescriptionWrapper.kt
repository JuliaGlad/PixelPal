package myapplication.android.pixelpal.ui.games.model

import myapplication.android.pixelpal.domain.model.genres.GenreDescriptionDomain

fun GenreDescriptionDomain.toUi() = GenreUiDescription(description)