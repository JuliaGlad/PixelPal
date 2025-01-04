package myapplication.android.pixelpal.ui.delegates.delegates.subtitle_textview

import myapplication.android.pixelpal.ui.delegates.main.DelegateItem

class SubtitleTextViewDelegateItem(
    val model: SubtitleTextViewModel
) : DelegateItem{
    override fun content(): Any = model

    override fun id(): Int = model.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        content() == other.content()
}