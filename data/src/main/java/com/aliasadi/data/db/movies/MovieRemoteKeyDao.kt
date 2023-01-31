package com.aliasadi.data.db.movies

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aliasadi.data.entities.MovieRemoteKeyDbData

/**
 * @author by Ali Asadi on 30/01/2023
 */
@Dao
interface MovieRemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveRemoteKey(keys: MovieRemoteKeyDbData)

    @Query("SELECT * FROM movies_remote_keys WHERE id=:id")
    suspend fun getRemoteKeyByMovieId(id: Int): MovieRemoteKeyDbData?

    @Query("DELETE FROM movies_remote_keys")
    suspend fun clearRemoteKeys()

    @Query("SELECT * FROM movies_remote_keys WHERE id = (SELECT MAX(id) FROM movies_remote_keys)")
    suspend fun getLastRemoteKey(): MovieRemoteKeyDbData?
}