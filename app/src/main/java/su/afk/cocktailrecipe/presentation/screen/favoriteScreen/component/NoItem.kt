package su.afk.cocktailrecipe.presentation.screen.favoriteScreen.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import su.afk.cocktailrecipe.R

@Composable
fun NoItem(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
        contentAlignment = Center
    ) {
        Text(
            text = stringResource(R.string.favorite_not_item),
            textAlign = TextAlign.Center
        )
    }
}