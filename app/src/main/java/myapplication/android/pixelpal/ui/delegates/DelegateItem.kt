package myapplication.android.pixelpal.ui.delegates

interface DelegateItem {
    fun content(): Any

    fun id(): Int

    fun compareToOther(other: DelegateItem): Boolean
}
