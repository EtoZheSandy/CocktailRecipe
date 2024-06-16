package su.afk.cocktailrecipe.presentation.screen.detailScreen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import su.afk.cocktailrecipe.domain.model.DrinkDetail
import java.util.Locale


@Composable
fun DrinkTypeSection(
    type: DrinkDetail,
) {
    Row(
        modifier = Modifier
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.onSurface)
                .height(36.dp)
                .size(180.dp)
        ) {
            Text(
                text = type.categoryAlcoholic.capitalize(Locale.ROOT),
                color = MaterialTheme.colorScheme.surface,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }

    }
}