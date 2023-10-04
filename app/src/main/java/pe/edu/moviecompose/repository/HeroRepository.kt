package pe.edu.moviecompose.repository

import pe.edu.moviecompose.data.local.HeroDao
import pe.edu.moviecompose.data.local.HeroEntity
import pe.edu.moviecompose.data.model.Hero
import pe.edu.moviecompose.data.model.HeroResponse
import pe.edu.moviecompose.data.remote.ApiHeroClient
import pe.edu.moviecompose.data.remote.HeroeService
import pe.edu.moviecompose.data.utils.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HeroRepository(
    private val heroDao: HeroDao,
    private val heroService: HeroeService = ApiHeroClient.getHeroeService()
) {

    fun searchByName(name: String, callback: (Result<List<Hero>>)->Unit){
        val searchByName = heroService.searchByName(text=name)
        searchByName.enqueue(object: Callback<HeroResponse>{
            override fun onResponse(call: Call<HeroResponse>, response: Response<HeroResponse>) {
                if (response.isSuccessful){
                    try{
                        val heroes = response.body()!!.heroes
                        heroes.forEach{hero->
                            hero.isFavorite = heroDao!!.getById(hero.id) !=null
                        }
                        callback(Result.Success(response.body()!!.heroes))
                    }catch (e: Exception){
                        callback(Result.Success(listOf<Hero>()))
                    }
                }
            }

            override fun onFailure(call: Call<HeroResponse>, t: Throwable) {
                callback(Result.Error(t.localizedMessage as String))
            }
        })
    }

    fun searchById(id: String, callback: (Result<Hero>)->Unit){
        val searchById = heroService.searchById(heroId = id)

        searchById.enqueue(object: Callback<Hero>{
            override fun onResponse(call: Call<Hero>, response: Response<Hero>) {
                if(response.isSuccessful){
                    callback(Result.Success(response.body()!!))
                }
            }

            override fun onFailure(call: Call<Hero>, t: Throwable) {
                callback(Result.Error(t.localizedMessage as String))
            }
        })
    }

    fun save(hero: Hero){
        heroDao!!.save(HeroEntity(hero.id))
        hero.isFavorite = true
    }

    fun delete(hero: Hero){
        heroDao!!.delete(HeroEntity(hero.id))
        hero.isFavorite = true
    }
}