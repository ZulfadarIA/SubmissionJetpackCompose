package com.dicoding.submissionjetpackcompose.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.submissionjetpackcompose.model.Clothes
import com.dicoding.submissionjetpackcompose.ui.ViewModelFactory
import com.dicoding.submissionjetpackcompose.ui.common.UiStates
import com.dicoding.submissionjetpackcompose.ui.component.ClothesItems

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    navigateToDetailPage: (Long) -> Unit
) {
    viewModel.statUi.collectAsState(initial = UiStates.Loading).value.let { stateUi ->
        when (stateUi) {
            is UiStates.Loading -> {
                viewModel.getAllClothes()
            }

            is UiStates.Success -> {
                ContentHomePage(
                    listClothes = stateUi.data,
                    navigateToDetailPage = navigateToDetailPage,
                    viewModel = viewModel,
                    modifier = modifier
                )
            }

            is UiStates.Error -> {}
        }
    }
}

@Composable
fun ContentHomePage(
    listClothes: List<Clothes>,
    navigateToDetailPage: (Long) -> Unit,
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    val scopes = rememberCompositionContext()
    val stateList = rememberLazyListState()
    val showingButtons: Boolean by remember {
        derivedStateOf { stateList.firstVisibleItemIndex > 0 }
    }

    Box(modifier = modifier) {
        LazyColumn(
            state = stateList,
            contentPadding = PaddingValues(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = modifier.testTag("HomeClothesList")
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
