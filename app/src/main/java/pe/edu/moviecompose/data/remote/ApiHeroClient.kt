package pe.edu.moviecompose.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiHeroClient {
    private const val API_BASE_URL = "https://superheroapi.com/api.php/"

    private var heroInterface: HeroeService? = null

    fun getHeroeService():HeroeService {
        if (heroInterface == null){
            val retrofit = Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            heroInterface = retrofit.create(HeroeService::class.java)
        }
        return heroInterface as HeroeService
    }
}