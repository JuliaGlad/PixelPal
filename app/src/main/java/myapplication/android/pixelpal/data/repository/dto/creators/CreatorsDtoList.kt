package myapplication.android.pixelpal.data.repository.dto.creators

class CreatorsDtoList(
    val items: List<CreatorDto>
)

class CreatorDto(
    val id: Long,
    val name: String,
    val role: List<CreatorRoleDto>,
    val gamesCount: Int,
    val image: String?
)