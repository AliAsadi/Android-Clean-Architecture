package com.aliasadi.data.db.favoritemovies

import androidx.room.*
import com.aliasadi.data.db.basedao.BaseDao
import com.aliasadi.data.model.FavoriteMovieDataEntity

/**
 * @author by Ali Asadi on 22/08/2022
 */
@Dao
interface FavoriteMovieDao : BaseDao<FavoriteMovieDataEntity> {

    @Query("SELECT * FROM favorite_movies")
    fun getAll(): List<FavoriteMovieDataEntity>

    @Query("SELECT * FROM favorite_movies where movieId=:movieId")
    fun get(movieId: Int): FavoriteMovieDataEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(favoriteMovieDbData: FavoriteMovieDataEntity)

    @Delete
    fun remove(favoriteMovieDbData: FavoriteMovieDataEntity)

}