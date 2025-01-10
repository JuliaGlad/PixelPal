package myapplication.android.pixelpal.ui.all_games.fragment_argument

interface AllArgument {

    data class CurrentReleasesAllArgument(
        val startDate: String,
        val currentDate: String
    ): AllArgument

    data class NextReleasesAllArgument(
        val endDate: String,
        val currentDate: String
    ): AllArgument

}