package pe.edu.moviecompose.ui.heroes

import android.transition.Slide
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import pe.edu.moviecompose.data.local.AppDatabase
import pe.edu.moviecompose.data.model.Hero
import pe.edu.moviecompose.repository.HeroRepository
import pe.edu.moviecompose.data.utils.Result

@Composable
fun HeroDetail(id: String="599"){

    val context = LocalContext.current
    val heroDao = AppDatabase.getInstance(context).heroDao()
    val repository = HeroRepository(heroDao)
    val hero = remember{
        mutableStateOf<Hero?>(null)
    }
    repository.searchById(id= id){result->
        hero.value = result.data!!
    }

    if(hero.value != null){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            GlideImage(
                imageModel = {hero.value!!.image.url},
                imageOptions = ImageOptions(contentScale = ContentScale.Fit),
                modifier = Modifier
                    .size(256.dp)
                    .clip(shape = RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = hero.value!!.name,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            HeroBiography(hero.value!!)
            Spacer(modifier = Modifier.height(10.dp))
            HeroPowerStats(hero.value!!)
        }
    }
}

@Composable
fun HeroBiography(hero: Hero){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
            .shadow(elevation = 4.dp)
    ) {
        Column {
            Text(text = "Biography:",
                style= MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold)
            Text(text = hero.biography.fullName)
        }
    }
}

@Composable
fun HeroPowerStats(hero: Hero){
    val power = hero.powerStats.power
    power.toFloatOrNull()?.let {
        Slider(value = it, onValueChange = {}, valueRange = 0f..100f)
    }
    val combat = hero.powerStats.combat
    combat.toFloatOrNull()?.let {
        Slider(value = it, onValueChange = {}, valueRange = 0f..100f)
    }
    val durability = hero.powerStats.durability
    durability.toFloatOrNull()?.let {
        Slider(value = it, onValueChange = {}, valueRange = 0f..100f)
    }
    val speed = hero.powerStats.speed
    speed.toFloatOrNull()?.let {
        Slider(value = it, onValueChange = {}, valueRange = 0f..100f)
    }
    val intelligence = hero.powerStats.intelligence
    intelligence.toFloatOrNull()?.let {
        Slider(value = it, onValueChange = {}, valueRange = 0f..100f)
    }
}