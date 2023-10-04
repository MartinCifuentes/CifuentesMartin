package pe.edu.moviecompose.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import pe.edu.moviecompose.data.model.Hero

@Dao
interface HeroDao {

    @Query("SELECT * FROM heroes")
    fun getAll():List<HeroEntity>

    //@Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
    //fun findByName(first: String, last: String): User

    @Query("SELECT * FROM heroes where :id")
    fun getById(id: String): HeroEntity?

    @Insert
    fun save(hero: HeroEntity)

    @Delete
    fun delete(hero: HeroEntity)
}