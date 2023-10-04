package pe.edu.moviecompose.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val API_BASE_URL = "https://api.themoviedb.org/3/movie/"

    private var movieInterface: MovieInterface? = null

    fun getMovieInterface():MovieInterface {
      val retrofit = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

      movieInterface = retrofit.create(MovieInterface::class.java)
      return movieInterface as MovieInterface
   }
}