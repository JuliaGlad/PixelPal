package myapplication.android.pixelpal.domain.model.platform

class PlatformDomainList(
    val platforms: List<PlatformsDomain>
)

class PlatformsDomain(
    val id: Long,
    val name: String,
    val gamesCount: Int,
    val startYear: Int?,
    val image: String
)