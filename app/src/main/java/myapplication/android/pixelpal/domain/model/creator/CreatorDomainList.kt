package myapplication.android.pixelpal.domain.model.creator

class CreatorDomainList(
    val items: List<CreatorDomain>
)

class CreatorDomain(
    val id: Long,
    val name: String,
    val role: List<RoleDomain>,
    val gamesCount: Int,
    val image: String?
)