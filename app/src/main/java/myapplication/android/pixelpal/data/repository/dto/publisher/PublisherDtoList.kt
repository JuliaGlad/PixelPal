package myapplication.android.pixelpal.data.repository.dto.publisher

class PublisherDtoList(
    val publishers: List<PublisherDto>
)
class PublisherDto(
    val id: Long,
    val name: String,
    val gamesCount: Int,
    val image: String?
)