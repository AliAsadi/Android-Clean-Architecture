package com.aliasadi.data.repository.movie

import com.aliasadi.data.api.MovieApi
import com.aliasadi.domain.entities.MovieEntity
import com.aliasadi.domain.util.Result

/**
 * Created by Ali Asadi on 24/05/2024
 */
class MovieRemoteDataSource(
    private val movieApi: MovieApi
) : MovieDataSource.Remote {

    override suspend fun getMovies(page: Int, limit: Int): Result<List<MovieEntity>> {
        return if (page == 2) {
            Result.Success(emptyList())
        } else {
            Result.Success(
                listOf(
                    MovieEntity(0, "The Shawshank Redemption", "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.", "https://image.tmdb.org/t/p/original//z8onk7LV9Mmw6zKz4hT6pzzvmvl.jpg", "Drama", ""),
                    MovieEntity(1, "The Godfather", "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.", "https://upload.wikimedia.org/wikipedia/en/1/1c/Godfather_ver1.jpg", "Crime", ""),
                    MovieEntity(2, "The Dark Knight", "When the menace known as the Joker emerges from his mysterious past, he wreaks havoc and chaos on the people of Gotham.", "https://upload.wikimedia.org/wikipedia/en/8/8a/Dark_Knight.jpg", "Action", ""),
                    MovieEntity(3, "Pulp Fiction", "The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.", "https://upload.wikimedia.org/wikipedia/en/8/82/Pulp_Fiction_cover.jpg", "Crime", ""),
                    MovieEntity(4, "Schindler's List", "In German-occupied Poland during World War II, industrialist Oskar Schindler gradually becomes concerned for his Jewish workforce after witnessing their persecution by the Nazis.", "https://upload.wikimedia.org/wikipedia/en/3/38/Schindler%27s_List_movie.jpg", "Biography", ""),
                )
            )
        }
    }

    override suspend fun getMovies(movieIds: List<Int>): Result<List<MovieEntity>> =
        Result.Success(
            movieIds.map { id ->
                when (id) {
                    0 -> MovieEntity(0, "The Shawshank Redemption", "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.", "https://upload.wikimedia.org/wikipedia/en/8/81/ShawshankRedemptionMoviePoster.jpg", "Drama", "")
                    1 -> MovieEntity(1, "The Godfather", "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.", "https://upload.wikimedia.org/wikipedia/en/1/1c/Godfather_ver1.jpg", "Crime", "")
                    2 -> MovieEntity(2, "The Dark Knight", "When the menace known as the Joker emerges from his mysterious past, he wreaks havoc and chaos on the people of Gotham.", "https://upload.wikimedia.org/wikipedia/en/8/8a/Dark_Knight.jpg", "Action", "")
                    3 -> MovieEntity(3, "Pulp Fiction", "The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.", "https://upload.wikimedia.org/wikipedia/en/8/82/Pulp_Fiction_cover.jpg", "Crime", "")
                    4 -> MovieEntity(4, "Schindler's List", "In German-occupied Poland during World War II, industrialist Oskar Schindler gradually becomes concerned for his Jewish workforce after witnessing their persecution by the Nazis.", "https://upload.wikimedia.org/wikipedia/en/3/38/Schindler%27s_List_movie.jpg", "Biography", "")
                    else -> MovieEntity(id, "Mock Movie $id", "Description for Mock Movie $id", "https://via.placeholder.com/200x300.jpg", "Category $id", "")
                }
            }
        )

    override suspend fun getMovie(movieId: Int): Result<MovieEntity> {
        val movie = when (movieId) {
            0 -> MovieEntity(0, "The Shawshank Redemption", "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.", "https://upload.wikimedia.org/wikipedia/en/8/81/ShawshankRedemptionMoviePoster.jpg", "Drama", "")
            1 -> MovieEntity(1, "The Godfather", "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.", "https://upload.wikimedia.org/wikipedia/en/1/1c/Godfather_ver1.jpg", "Crime", "")
            2 -> MovieEntity(2, "The Dark Knight", "When the menace known as the Joker emerges from his mysterious past, he wreaks havoc and chaos on the people of Gotham.", "https://upload.wikimedia.org/wikipedia/en/8/8a/Dark_Knight.jpg", "Action", "")
            3 -> MovieEntity(3, "Pulp Fiction", "The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.", "https://upload.wikimedia.org/wikipedia/en/8/82/Pulp_Fiction_cover.jpg", "Crime", "")
            4 -> MovieEntity(4, "Schindler's List", "In German-occupied Poland during World War II, industrialist Oskar Schindler gradually becomes concerned for his Jewish workforce after witnessing their persecution by the Nazis.", "https://upload.wikimedia.org/wikipedia/en/3/38/Schindler%27s_List_movie.jpg", "Biography", "")
            else -> MovieEntity(movieId, "Mock Movie $movieId", "Description for Mock Movie $movieId", "https://via.placeholder.com/200x300.jpg", "Category $movieId", "")
        }
        return Result.Success(movie)
    }
    override suspend fun search(query: String, page: Int, limit: Int): Result<List<MovieEntity>> =
        if (page == 2) {
            Result.Success(emptyList())
        } else {
            val movies = listOf(
                MovieEntity(0, "The Shawshank Redemption", "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.", "https://upload.wikimedia.org/wikipedia/en/8/81/ShawshankRedemptionMoviePoster.jpg", "Drama", ""),
                MovieEntity(1, "The Godfather", "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.", "https://upload.wikimedia.org/wikipedia/en/1/1c/Godfather_ver1.jpg", "Crime", ""),
                MovieEntity(2, "The Dark Knight", "When the menace known as the Joker emerges from his mysterious past, he wreaks havoc and chaos on the people of Gotham.", "https://upload.wikimedia.org/wikipedia/en/8/8a/Dark_Knight.jpg", "Action", ""),
                MovieEntity(3, "Pulp Fiction", "The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.", "https://upload.wikimedia.org/wikipedia/en/8/82/Pulp_Fiction_cover.jpg", "Crime", ""),
                MovieEntity(4, "Schindler's List", "In German-occupied Poland during World War II, industrialist Oskar Schindler gradually becomes concerned for his Jewish workforce after witnessing their persecution by the Nazis.", "https://upload.wikimedia.org/wikipedia/en/3/38/Schindler%27s_List_movie.jpg", "Biography", ""),
            )

            val filteredMovies = movies.filter {
                it.title.contains(query, ignoreCase = true) || it.description.contains(query, ignoreCase = true) }

            Result.Success(filteredMovies)
        }
}
