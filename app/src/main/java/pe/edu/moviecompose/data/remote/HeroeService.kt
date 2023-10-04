package pe.edu.moviecompose.data.remote

import pe.edu.moviecompose.data.model.Hero
import pe.edu.moviecompose.data.model.HeroResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface HeroeService {
    @GET("{api_token}/search/{text}")
    fun searchByName(@Path("api_token") apiToken: String="10157703717092094",
                     @Path("text") text:String
    ):Call<HeroResponse>

    @GET("{api_token}/{id_superhero}")
    fun searchById(
        @Path("api_token") apiToken: String="10157703717092094",
        @Path("id_superhero") heroId: String
    ):Call<Hero>
}