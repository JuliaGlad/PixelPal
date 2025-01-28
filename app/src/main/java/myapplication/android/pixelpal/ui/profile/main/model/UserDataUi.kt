package myapplication.android.pixelpal.ui.profile.main.model

import myapplication.android.pixelpal.domain.model.user.UserDomainMainModel

class UserDataUi(
    var name: String?,
    val email: String?,
    val uri: String? = null
)

fun UserDomainMainModel.toUi() = UserDataUi(name, email)