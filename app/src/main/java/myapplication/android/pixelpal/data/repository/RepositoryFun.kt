package myapplication.android.pixelpal.data.repository

import myapplication.android.pixelpal.data.models.gamesNews.GamesNewsList
import kotlin.reflect.KSuspendFunction1

suspend fun <T> getAndCheckData(
    localFunc: () -> T?,
    remoteFunc: suspend () -> T?,
    addToLocal: (value: T) -> Unit
): T {
    val local = localFunc.invoke()
    val result =
        if (local != null) local
        else {
            val remote = remoteFunc.invoke()
            addToLocal(remote!!)
            remote
        }
    return result
}