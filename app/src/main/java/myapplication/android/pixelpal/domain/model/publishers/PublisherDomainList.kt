package myapplication.android.pixelpal.domain.model.publishers

class PublisherDomainList(
   val publishers: List<PublisherDomain>
)
class PublisherDomain(
    val id: Long,
    val name: String,
    val gamesCount: Int,
    val image: String?
)