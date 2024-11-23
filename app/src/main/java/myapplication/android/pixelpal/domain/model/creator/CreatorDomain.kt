package myapplication.android.pixelpal.domain.model.creator

class CreatorDomain(
    val id: Long,
    val name: String,
    val role: List<RoleDomain>,
    val gamesCount: Int,
    val image: String
)