package pe.edu.moviecompose.ui.heroes

import android.widget.Toast
import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import pe.edu.moviecompose.data.local.AppDatabase
import pe.edu.moviecompose.data.model.Hero
import pe.edu.moviecompose.data.utils.Result
import pe.edu.moviecompose.repository.HeroRepository

@Composable
fun HeroList(
    heroes: MutableState<List<Hero>>,
    selectHero: (String)->Unit
){
    val context = LocalContext.current
    val heroDao = AppDatabase.getInstance(context).heroDao()
    val heroRepository = HeroRepository(heroDao)

    LazyColumn {
        items(heroes.value){hero->
            HeroItem(hero, selectHero,
                deleteHero ={
                    heroRepository.delete(hero)
                    hero.isFavorite = false
                },
                insertHero ={
                    heroRepository.save(hero)
                    hero.isFavorite = false
                })
        }
    }
}

@Composable
fun HeroItem(hero: Hero,
             selectHero: (String)->Unit,
             deleteHero: () -> Unit,
             insertHero: () -> Unit
){
    val isFavorite = remember {
        mutableStateOf(false)
    }
    isFavorite.value = hero.isFavorite
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable {
                selectHero(hero.id)
            }
    ){
        Row(verticalAlignment = Alignment.CenterVertically) {
            GlideImage(
                imageModel = {hero.image.url},
                imageOptions = ImageOptions(contentScale = ContentScale.Fit),
                modifier = Modifier.size(92.dp).weight(3f)
            )
            Column(
                modifier = Modifier.weight(5f)
            ) {
                Text(text = hero.name, fontWeight = FontWeight.Bold)
                Text(text = hero.biography.fullName)
            }
            IconButton(onClick = {
                if (isFavorite.value) {
                    deleteHero()
                } else {
                    insertHero()
                }
                isFavorite.value = !isFavorite.value
            },
                modifier = Modifier.weight(2f)
            ){
                Icon(Icons.Default.Favorite,
                    contentDescription = null,
                    tint = if (isFavorite.value) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
fun HeroSearch(selectHero: (String) -> Unit){
    val text = remember{
        mutableStateOf("")
    }
    val heroes = remember{
        mutableStateOf(listOf<Hero>())
    }
    Column {
        Search(text, heroes)
        HeroList(heroes, selectHero)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(text: MutableState<String>,
           heroes: MutableState<List<Hero>>){
    val context = LocalContext.current
    val heroDao = AppDatabase.getInstance(context).heroDao()

    val repository = HeroRepository(heroDao)
    OutlinedTextField(
        value = text.value,
        placeholder={Text("Search")},
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        onValueChange ={newText->
            text.value = newText
        },
        leadingIcon ={
            Icon(Icons.Filled.Search, null)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                repository.searchByName(text.value){result->
                    if(result is Result.Success){
                        heroes.value = result.data!!
                    }else{
                        Toast.makeText(context, result.message!!, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )
    )
}