package myapplication.android.pixelpal.ui.delegates.delegates.user_avatar

import myapplication.android.pixelpal.ui.delegates.main.DelegateItem

class AvatarDelegateItem(
    private val avatarModel: AvatarModel
): DelegateItem {
    override fun content(): Any = avatarModel

    override fun id(): Int = avatarModel.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        content() == other.content()
}