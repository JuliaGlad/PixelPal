package myapplication.android.pixelpal.ui.ktx

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.cancellation.CancellationException

inline fun <R> runCatchingNonCancellation(block: () -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.failure(e)
    }
}


suspend fun <T1, T2, T3, R> asyncAwait(
    s1: suspend CoroutineScope.() -> T1,
    s2: suspend CoroutineScope.() -> T2,
    s3: suspend CoroutineScope.() -> T3,
    transform: suspend (T1, T2, T3) -> R
): R {
    return coroutineScope {
        val result1 = async(block = s1)
        val result2 = async(block = s2)
        val result3 = async(block = s3)

        transform(
            result1.await(),
            result2.await(),
            result3.await()
        )
    }
}

suspend fun <T, R> asyncAwait(
    s1: suspend CoroutineScope.() -> T,
    transform: suspend (T) -> R
): R {
    return coroutineScope {
        val result1 = async(block = s1)
        transform(result1.await())
    }
}