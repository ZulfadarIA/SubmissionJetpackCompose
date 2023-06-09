package com.dicoding.submissionjetpackcompose.ui.screen.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.submissionjetpackcompose.model.Clothes
import com.dicoding.submissionjetpackcompose.ui.ViewModelFactory
import com.dicoding.submissionjetpackcompose.ui.common.UiStates
import com.dicoding.submissionjetpackcompose.ui.component.ClothesItems

@Composable
fun FavoritePage(
    modifier: Modifier = Modifier,
    viewModel: FavoriteProductViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    navigateToDetailPage: (Long) -> Unit
) {
    viewModel.stateUi.collectAsState(initial = UiStates.Loading).value.let { stateUi ->
        when (stateUi) {
            is UiStates.Loading -> {
                viewModel.getAllFavoritedClothes()
            }

            is UiStates.Success -> {
                ContentFavoriteProduct(
                    listClothes = stateUi.data,
                    navigateToDetailPage = navigateToDetailPage,
                    modifier = modifier
                )
            }

            is UiStates.Error -> {}
        }
    }
}

@Composable
fun ContentFavoriteProduct(
    listClothes: List<Clothes>,
    navigateToDetailPage: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val stateList = rememberLazyListState()
    val scopes = rememberCoroutineScope()
    val showingButton: Boolean by remember {
        derivedStateOf { stateList.firstVisibleItemIndex > 0 }
    }

    Box(modifier = modifier) {
        Column {
            LazyColumn(
                state = stateList,
                contentPadding = PaddingValues(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = modifier.testTag("ListFavorited")
            ) {
                items(listClothes, key = { it.id }) { item ->
                    ClothesItems(
                        title = item.titleProd,
                        image = item.image,
                        description = item.descriptionProd,
                        modifier = Modifier.clickable {navigateToDetailPage(item.id)}
                    )
                }
            }
        }
    }
}
