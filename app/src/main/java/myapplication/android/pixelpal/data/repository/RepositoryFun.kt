package myapplication.android.pixelpal.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T> getAndCheckData(
    localFunc: () -> T?,
    remoteFunc: suspend () -> T?,
    addToLocal: (value: T) -> Unit
): T {
    val result =
        localFunc.invoke()
            ?: withContext(Dispatchers.IO) {
                val remote = remoteFunc.invoke()
                addToLocal(remote!!)
                remote
            }
    return result
}