package myapplication.android.pixelpal.ui.home.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import myapplication.android.pixelpal.domain.model.games.GamesNewsListDomain
import myapplication.android.pixelpal.ui.home.model.GamesNewsListUi
import myapplication.android.pixelpal.ui.home.model.toUi
import java.util.stream.Collectors

object HomeExtensions {

    fun List<GamesNewsListDomain>.toList(): List<GamesNewsListUi> =
        stream()
            .map { item -> item.toUi() }
            .collect(Collectors.toList())

    fun <T1, T2, T3> List<Triple<T1, T2, T3>>.flatten(): Triple<List<T1>, List<T2>, List<T3>> {
        val accumulatorFirst = mutableListOf<T1>()
        val accumulatorSecond = mutableListOf<T2>()
        val accumulatorThird = mutableListOf<T3>()
        for (i in this) {
            if (!accumulatorFirst.contains(i.first)) accumulatorFirst.add(i.first)
            if (!accumulatorSecond.contains(i.second)) accumulatorSecond.add(i.second)
            if (!accumulatorThird.contains(i.third)) accumulatorThird.add(i.third)
        }
        return Triple(accumulatorFirst, accumulatorSecond, accumulatorThird)
    }

    fun <T1, T2, T3, R> combine(
        first: Flow<T1>,
        second: Flow<T2>,
        third: Flow<T3>,
        transform: suspend (T1, T2, T3) -> R
    ): Flow<R> =
        first.combine(second) { a, b ->
            a to b
        }.combine(third) { (a, b), c ->
            transform(a, b, c)
        }
}



