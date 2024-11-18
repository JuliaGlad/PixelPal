package myapplication.android.pixelpal.ui.delegates.main

interface DelegateItem {
    fun content(): Any

    fun id(): Int

    fun compareToOther(other: DelegateItem): Boolean
}
