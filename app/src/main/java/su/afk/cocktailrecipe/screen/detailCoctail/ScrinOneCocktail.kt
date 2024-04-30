package su.afk.cocktailrecipe.screen.detailCoctail

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import su.afk.cocktailrecipe.data.models.Drink
import su.afk.cocktailrecipe.data.models.ThecocktaildbModels
import su.afk.cocktailrecipe.util.Resource
import java.util.Locale


//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ScreenDetailCocktail(
    dominantColor: Color,
    drinkId: String,
    navController: NavController,
    topPadding: Dp = 70.dp,
    drinkImageSize: Dp = 300.dp,
    viewModel: OneCocktailViewModel = hiltViewModel()
) {
    val drinkInfo = produceState<Resource<ThecocktaildbModels>>(
        initialValue = Resource.Loading()
    ) {
        value = viewModel.getDrinkInfo(drinkId)
    }.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(dominantColor)
            .padding(bottom = 0.dp)
    ) {
        DrinkDetailTopSection(
            navController = navController,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
                .align(Alignment.TopCenter)
        )
        DrinkDetailStateWrapper(
            drinkInfo = drinkInfo,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = topPadding + drinkImageSize / 2f,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 8.dp
                )
                .shadow(10.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(12.dp)
                .align(Alignment.BottomCenter),
            loadingModifier = Modifier
                .size(100.dp)
                .align(Alignment.Center)
                .padding(
                    top = topPadding + drinkImageSize / 2f,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
        )

        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (drinkInfo is Resource.Success) {
                drinkInfo.data?.drinks?.firstOrNull()?.let {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(it.strDrinkThumb)
                            .crossfade(true)
                            .build(),
                        contentDescription = it.strDrink,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(drinkImageSize)
                            .offset(y = topPadding)
                            .clip(RoundedCornerShape(8.dp))
                    )
                }
            }
        }
    }
}

@Composable
fun DrinkDetailTopSection(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color.Black,
                        Color.Transparent
                    )
                )
            ),
        contentAlignment = Alignment.TopStart
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(36.dp)
                .offset(x = 16.dp, y = 24.dp)
                .clickable {
                    navController.popBackStack()
                }
        )
    }
}

@Composable
fun DrinkDetailStateWrapper(
    drinkInfo: Resource<ThecocktaildbModels>,
    modifier: Modifier = Modifier,
    loadingModifier: Modifier = Modifier
) {
    when (drinkInfo) {
        is Resource.Success -> {
            DrinkDetailSection(
                drinkInfo = drinkInfo.data?.drinks?.firstOrNull()!!,
                modifier = modifier
                    .offset(y = (-20).dp)
            )
        }

        is Resource.Error -> {
            Text(
                text = drinkInfo.message!!,
                color = Color.Red,
                modifier = modifier
            )
        }

        is Resource.Loading -> {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = loadingModifier
            )
        }
    }
}

@Composable
fun DrinkDetailSection(
    drinkInfo: Drink,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .offset(y = 170.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = drinkInfo.strDrink,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 22.sp
        )
        DrinkTypeSection(type = drinkInfo)
        RecipeDrink(
            ingridientsDrink = drinkInfo
        )
    }
}

@Composable
fun DrinkTypeSection(
    type: Drink
) {
    Row(
        modifier = Modifier
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
//                .weight(1f) // если будет 1 тип то он займет все место, если 2 то они его разделят между собой
                .padding(horizontal = 8.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.onSurface)
                .height(36.dp)
                .size(180.dp) // размер кнопки типа
        ) {
            Text(
                text = type.strAlcoholic.capitalize(Locale.ROOT),
                color = MaterialTheme.colorScheme.surface,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }

    }
}

@Composable
fun RecipeDrink(
    ingridientsDrink: Drink,
    height: Dp = 280.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0
) {
//    var animationPlayed by remember {
//        mutableStateOf(false)
//    }
//    val curPercent = animateFloatAsState(
//        targetValue = if (animationPlayed) {
//            1f
//        } else {
//            0f
//        },
//        animationSpec = tween(
//            animDuration,
//            animDelay
//        )
//    )
//
//    LaunchedEffect(key1 = true) {
//        animationPlayed = true
//    }
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(height)
//            .clip(CircleShape)
//            .background(Color.LightGray)
//    ) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxHeight()
//                .fillMaxWidth(curPercent.value)
//                .clip(CircleShape)
//                .background(Color.Cyan)
            .padding(horizontal = 8.dp)
    ) {
        Text(
            text = ingridientsDrink.strInstructions,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 18.sp,
            modifier = Modifier.padding(10.dp)
        )

        // API конечно...
        ingridientsDrink.apply {
            Log.d("MyLog", "${ingridientsDrink}")
            strIngredient1?.let { OneIngredient(it, strMeasure1) }
            strIngredient2?.let { OneIngredient(it.toString(), strMeasure2) }
            strIngredient3?.let { OneIngredient(it.toString(), strMeasure3) }
            strIngredient4?.let { OneIngredient(it.toString(), strMeasure4) }
            strIngredient5?.let { OneIngredient(it.toString(), strMeasure5) }
            strIngredient6?.let { OneIngredient(it.toString(), strMeasure6) }
            strIngredient7?.let { OneIngredient(it.toString(), strMeasure7) }
            strIngredient8?.let { OneIngredient(it.toString(), strMeasure8) }
            strIngredient9?.let { OneIngredient(it.toString(), strMeasure9) }
            strIngredient10?.let { OneIngredient(it.toString(), strMeasure10) }
            strIngredient11?.let { OneIngredient(it.toString(), strMeasure11) }
            strIngredient12?.let { OneIngredient(it.toString(), strMeasure12) }
            strIngredient13?.let { OneIngredient(it.toString(), strMeasure13) }
            strIngredient14?.let { OneIngredient(it.toString(), strMeasure14) }
            strIngredient15?.let { OneIngredient(it.toString(), strMeasure15) }
        }
        Spacer(modifier = Modifier.height(150.dp))
    }

}


@Composable
fun OneIngredient(
    strIngredient: Any?,
    strMeasure: Any?
) {
    Log.d("MyLog", "$strMeasure | $strIngredient")
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(
                        "https://www.thecocktaildb.com/images/ingredients/$strIngredient-Medium.png"
                    )
                    .crossfade(true)
                    .build(),
                contentDescription = strIngredient.toString(),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(160.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Text(
                text = "${if (strMeasure is String) strMeasure else ""} $strIngredient",
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                fontSize = 18.sp
            )
        }
        Divider(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 10.dp),
            thickness = 1.dp)
    }
}