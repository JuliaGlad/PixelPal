package myapplication.android.pixelpal.data.repository.favorite_games

import android.util.Log
import com.google.firebase.firestore.FieldValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import myapplication.android.pixelpal.data.models.gamesNews.GamesMainInfoList
import myapplication.android.pixelpal.data.repository.dto.game.GameMainInfoDtoList
import myapplication.android.pixelpal.data.repository.mapper.game.toDto
import myapplication.android.pixelpal.data.repository.user.FirebaseService
import myapplication.android.pixelpal.data.source.favorite_games.FavoriteGamesLocalSource
import myapplication.android.pixelpal.data.source.favorite_games.FavoriteGamesRemoteSource
import myapplication.android.pixelpal.data.source.games.GamesRemoteSource
import javax.inject.Inject

class FavoriteGamesRepositoryImpl @Inject constructor(
    private val gamesRemoteSource: GamesRemoteSource,
    private val remoteSource: FavoriteGamesRemoteSource,
    private val localSource: FavoriteGamesLocalSource
): FavoriteGamesRepository {
    private suspend fun getFavoriteGamesIds(): List<Long> =
        withContext(Dispatchers.IO){
            val uid = FirebaseService.auth.currentUser!!.uid
            val result = FirebaseService.fireStore
                .collection(USER_COLLECTION)
                .document(uid)
                .get()
                .await()
            val list = result.get(FAVORITE_GAMES) as List<Long>
            for (i in list){
                Log.i("Game id favs", i.toString())
            }
            list
        }

    override suspend fun getFavoriteGames(): GameMainInfoDtoList {
        val local = localSource.getFavoriteGames()
        val result =
            if (local != null) local
            else {
                val remote = withContext(Dispatchers.IO) {
                    val ids = getFavoriteGamesIds()
                    if (ids.isNotEmpty()) remoteSource.getGamesById(ids)
                    else GamesMainInfoList(emptyList())
                }
                localSource.insertFavoriteGames(remote)
                remote
            }.toDto()
        return result
    }

    override suspend fun removeGame(gameId: Long) {
        withContext(Dispatchers.IO){
            val uid = FirebaseService.auth.currentUser!!.uid
            val document = FirebaseService.fireStore
                .collection(USER_COLLECTION)
                .document(uid)
            document.update(FAVORITE_GAMES, FieldValue.arrayRemove(gameId)).await()
            localSource.deleteGame(gameId)
        }
    }

    override suspend fun addGame(gameId: Long) {
        withContext(Dispatchers.IO) {
            updateFirebaseDocument(gameId)
            updateLocalDatabase(gameId)
        }
    }

    private suspend fun updateLocalDatabase(gameId: Long) {
        val game = gamesRemoteSource.getGameById(gameId)
        localSource.insertGame(game)
    }

    private suspend fun updateFirebaseDocument(gameId: Long) {
        val uid = FirebaseService.auth.currentUser!!.uid
        val document = FirebaseService.fireStore
            .collection(USER_COLLECTION)
            .document(uid)
       document.update(FAVORITE_GAMES, FieldValue.arrayUnion(gameId)).await()
    }

    companion object{
        const val FAVORITE_GAMES = "FavoriteGames"
        const val USER_COLLECTION = "UserCollection"
    }
}