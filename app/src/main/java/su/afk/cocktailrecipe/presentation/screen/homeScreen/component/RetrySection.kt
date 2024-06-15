package su.afk.cocktailrecipe.presentation.screen.homeScreen.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import su.afk.cocktailrecipe.R

@Composable
fun RetrySection(
    error: String,
    onRetry: () -> Unit,
) {
    Column(modifier = Modifier.padding(top = 60.dp)) {
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