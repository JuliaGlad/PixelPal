package myapplication.android.pixelpal.ui.home.model

import myapplication.android.pixelpal.domain.model.games.GamesNewsDomain

fun GamesNewsDomain.toUi() =
    GamesNewsUi(
        gameId = gameId,
        name = name,
        releaseDate = releaseDate,
        rating = rating,
        genre = genre,
        uri = uri
    )