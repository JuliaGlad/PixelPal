package myapplication.android.pixelpal.ui.all_games.fragment_argument

interface AllArgument {

    data class CurrentReleasesAllArgument(
        val startDate: String,
        val currentDate: String
    ): AllArgument

    data class StoreGamesArgument(
        val storeId: Int
    ): AllArgument

    data class NextReleasesAllArgument(
        val endDate: String,
        val currentDate: String
    ): AllArgument

    data class GameDetailsArgument(
        val genre: String,
        val gameId: Long
    ): AllArgument

    data class CreatorGamesArgument(
        val creatorId: Long
    ): AllArgument

    data class PublisherGamesArgument(
        val publisherId: Long
    ): AllArgument

    data class PlatformGamesArgument(
        val platformId: Long
    ): AllArgument
}
