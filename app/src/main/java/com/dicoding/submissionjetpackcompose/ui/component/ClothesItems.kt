package com.dicoding.submissionjetpackcompose.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dicoding.submissionjetpackcompose.R
import com.dicoding.submissionjetpackcompose.ui.theme.SubmissionJetpackComposeTheme


@Composable
fun ClothesItems(
    image: Int,
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(12.dp)
                .size(100.dp)
                .clip(RectangleShape)
        )
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = title, overflow = TextOverflow.Ellipsis, style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.ExtraBold
            ))

            Text(text = description, style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Normal
            ))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomClothesAppPreview() {
    SubmissionJetpackComposeTheme() {
        ClothesItems(
            image = R.drawable.card1,
            title = "Baju wanita 1",
            description = "Baju wanita dengan bahan yang halus dan lembut"
        )
    }
}