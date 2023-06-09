package com.dicoding.submissionjetpackcompose.ui.screen.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.submissionjetpackcompose.R
import com.dicoding.submissionjetpackcompose.model.Clothes
import com.dicoding.submissionjetpackcompose.ui.ViewModelFactory
import com.dicoding.submissionjetpackcompose.ui.common.UiStates
import com.dicoding.submissionjetpackcompose.ui.theme.SubmissionJetpackComposeTheme

@Composable
fun DetailPage(
    idClothes: Long,
    viewModel: DetailProductViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    navigateToBack: () -> Unit
) {
    viewModel.updateFavoriteClothesStatus(idClothes)
    val statusFavorited by viewModel.statusFavoriteClothes.collectAsState(initial = false)
    
    viewModel.stateUi.collectAsState(initial = UiStates.Loading).value.let { stateUi ->
        when (stateUi) {
            is  UiStates.Loading -> {
                viewModel.getClothesById(idClothes)
            }
            
            is  UiStates.Success -> {
                val datas = stateUi.data
                ContentDetailProduct(
                    image = datas.image,
                    titleProd = datas.titleProd,
                    descriptionProd = datas.descriptionProd,
                    detailProd = datas.detailProd,
                    onBackClick = navigateToBack,
                    statusFavorited = statusFavorited,
                    favoritedStatusUpdates = { viewModel.favoriteChanged(datas) }
                )
            }

            is UiStates.Error -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentDetailProduct(
    @DrawableRes image: Int,
    titleProd: String,
    descriptionProd: String,
    detailProd: String,
    onBackClick: () -> Unit,
    statusFavorited: Boolean,
    favoritedStatusUpdates: () -> Unit,
    modifier: Modifier = Modifier
){
    Box(modifier = modifier) {
        Scaffold(
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(
                            onClick = { onBackClick() }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = stringResource(id = R.string.backButton)
                            )
                        }
                    },
                    title = {
                        Text(
                            text = stringResource(id = R.string.detailTitle)
                        )
                    }
                )
            }
        ) { paddingInner ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = modifier
                    .padding(paddingInner)
                    .padding(20.dp)
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
            ) {
                Image(
                    painter = painterResource(image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .fillMaxWidth()
                        .clip(RectangleShape)
                )

                Text(
                    text = titleProd,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )

                Text(
                    text = stringResource(R.string.descText, descriptionProd),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = detailProd,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        }

        FloatingActionButton(
            modifier = modifier
                .align(Alignment.BottomEnd)
                .padding(35.dp),
            onClick = favoritedStatusUpdates,
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(
                imageVector = if (statusFavorited) {
                    Icons.Default.Favorite
                } else {
                    Icons.Default.FavoriteBorder
                },

                contentDescription = if (statusFavorited) {
                    stringResource(id = R.string.deleteFavorite, titleProd)
                } else {
                    stringResource(id = R.string.addToFavorite, titleProd)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailProductContentPreview() {
    SubmissionJetpackComposeTheme {
        ContentDetailProduct(
            image = R.drawable.card1,
            titleProd = "Baju Wanita 1",
            descriptionProd = "Baju wanita yang terbuat dari bahan yang lembut dan nyaman dipakai.",
            detailProd = "Warna: Merah/kuning/hijau \n Ukuran: S/M/L/XL",
            onBackClick = { },
            statusFavorited = false,
            favoritedStatusUpdates = {}
        )
    }
}

