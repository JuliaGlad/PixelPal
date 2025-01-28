package myapplication.android.pixelpal.domain.wrapper.user

import myapplication.android.pixelpal.data.repository.user.UserDataModel
import myapplication.android.pixelpal.domain.model.user.UserDomainMainModel

fun UserDataModel.toMainDomain() =
    UserDomainMainModel(
        id,
        name,
        email
    )