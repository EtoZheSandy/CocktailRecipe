package su.afk.cocktailrecipe.presentation.screen.homeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import su.afk.cocktailrecipe.R

@Composable
fun ScreenHome(
    navController: NavController,
    viewModel: HomeListViewModel
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier,
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Image(
                    painter = painterResource(id = R.drawable.logo), contentDescription = "Logo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .height(50.dp)
                )
                SearchBar(
                    hint = stringResource(R.string.search_hint),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    viewModel = viewModel
                ) {
                    viewModel.searchDrinkName(it)
                }
                Spacer(modifier = Modifier.height(16.dp))
                DrinkList(navController = navController, viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable()
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    viewModel: HomeListViewModel,
    onSearch: (String) -> Unit = {},
) {
    val searchText by viewModel.searchText.collectAsState()

    Box(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = searchText,
                onValueChange = {
                    viewModel.setSearchText(it)
                    onSearch(it)
                },
                maxLines = 1,
                singleLine = true,
                textStyle = TextStyle(color = Color.Black),
                modifier = Modifier
                    .weight(1f)
                    .shadow(5.dp, CircleShape)
                    .background(Color.White, CircleShape)
                    .padding(horizontal = 5.dp, vertical = 0.dp),
                label = { Text(hint) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                )
            )

            // Кнопка для выбора фильтров
//            Box(
//                modifier = Modifier
//                    .clickable { expanded = true }
//            ) {
//                Box {
//                    IconButton(onClick = { expanded = true }) {
//                        Icon(Icons.Default.Menu, contentDescription = "Показать меню")
//                    }
//                    DropdownMenu(
//                        expanded = expanded,
//                        onDismissRequest = { expanded = false }
//                    ) {
//                        filterOptions.forEach{ filterDrink ->
//                            DropdownMenuItem(
//                                text = { Text(
//                                    filterDrink, fontSize=16.sp,
//                                    modifier = Modifier.padding(2.dp)
//                                ) },
//                                onClick = {
//                                    onFilterSelected(filterDrink)
//                                    expanded = false // Закрыть меню после выбора
//                                },
//                                modifier = if (filterDrink == selectedFilter) {
//                                    Modifier.background(Color.LightGray)
//                                } else {
//                                    Modifier
//                                }
//                            )
//                        }
//                    }
//                }
//            }
        }
    }
}

@Composable
fun DrinkList(
    navController: NavController,
    viewModel: HomeListViewModel = hiltViewModel(),
) {
    val cocktailList by remember { viewModel.cocktailList }
    val loadError by remember { viewModel.loadError }
    val isLoading by remember { viewModel.isLoading }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp), // content padding
    ) {
        items(cocktailList.size) { index ->
            LazyItem(cocktail = cocktailList[index], navController = navController)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.size(50.dp)
            )
        }
        if (loadError.isNotEmpty()) {
            RetrySection(error = loadError) {
                viewModel.loadCocktailPaginated()
            }
        }
    }
}

@Composable
fun RetrySection(
    error: String,
    onRetry: () -> Unit,
) {
    Column {
        Text(
            text = error,
            color = Color.Red,
            fontSize = 18.sp
        )
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = { onRetry() },
            modifier = Modifier.align(alignment = CenterHorizontally)
        ) {
            Text(text = stringResource(R.string.new_retry))
        }
    }
}