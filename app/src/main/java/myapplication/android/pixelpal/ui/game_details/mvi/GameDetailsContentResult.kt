package myapplication.android.pixelpal.ui.game_details.mvi

import myapplication.android.pixelpal.ui.game_details.model.CreatorsGameUiList
import myapplication.android.pixelpal.ui.game_details.model.GameDescriptionUi
import myapplication.android.pixelpal.ui.game_details.model.GameDetailsResult
import myapplication.android.pixelpal.ui.game_details.model.ScreenshotsUiList
import myapplication.android.pixelpal.ui.game_details.model.StoresSellingGameUiList
import myapplication.android.pixelpal.ui.games.games.model.GamesShortDataUiList

class GameDetailsContentResult(
    val storeLinks: StoresSellingGameUiList,
    val parentGames: GamesShortDataUiList,
    val additions: GamesShortDataUiList,
    val sameSeries: GamesShortDataUiList,
    val creators: CreatorsGameUiList,
    val screenshotsUiList: ScreenshotsUiList,
    val gameDetails: GameDescriptionUi
): GameDetailsResult