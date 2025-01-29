package myapplication.android.pixelpal.domain.mapper.user

import myapplication.android.pixelpal.data.repository.dto.user.UserDataDto
import myapplication.android.pixelpal.domain.model.user.UserDomainMainModel

fun UserDataDto.toMainDomain() =
    UserDomainMainModel(
        id,
        name,
        email
    )