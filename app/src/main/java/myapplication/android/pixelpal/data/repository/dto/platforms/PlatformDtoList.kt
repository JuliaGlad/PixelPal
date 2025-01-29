package myapplication.android.pixelpal.data.repository.dto.platforms

class PlatformDtoList(
    val platforms: List<PlatformsDto>
)

class PlatformsDto(
    val id: Long,
    val name: String,
    val gamesCount: Int,
    val startYear: Int?,
    val image: String
)