package pe.edu.moviecompose.data.remote


import pe.edu.moviecompose.data.model.Movie
import pe.edu.moviecompose.data.model.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieInterface {
    @GET("popular")
    fun getMovies(@Query("api_key") apiKey:String="3cae426b920b29ed2fb1c0749f258325"): Call<MovieResponse>
}