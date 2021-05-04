package com.github.mag0716.composesamples.ui.androiddevchallenge3

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.github.mag0716.composesamples.ui.theme.AndroidDevChallenge3Theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {
    var searchText by remember { mutableStateOf("") }
    val themeList: List<Theme> by viewModel.themeList.observeAsState(emptyList())
    val gardenList: List<Garden> by viewModel.gardenList.observeAsState(emptyList())
    HomeScreen(
        searchText = searchText,
        onChangeSearchText = { searchText = it },
        themeList = themeList,
        gardenList = gardenList
    )
}

@Composable
fun HomeScreen(
    searchText: String,
    onChangeSearchText: (String) -> Unit,
    themeList: List<Theme>,
    gardenList: List<Garden>
) {
    LazyColumn(
        contentPadding = PaddingValues(start = 16.dp, top = 40.dp, bottom = 56.dp, end = 16.dp)
    ) {
        item {
            OutlinedTextField(
                value = searchText,
                onValueChange = onChangeSearchText,
                label = {
                    Text(
                        "Search",
                        style = MaterialTheme.typography.body1
                    )
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        item {
            Text(
                "Browse themes",
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .paddingFromBaseline(
                        top = 32.dp,
                        bottom = 16.dp
                    )
            )
        }
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(themeList) { theme ->
                    ThemeCard(theme = theme)
                }
            }
        }
        item {
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    "Design your home garden",
                    style = MaterialTheme.typography.h1,
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier
                        .weight(1f)
                        .paddingFromBaseline(
                            top = 40.dp,
                            bottom = 16.dp
                        )
                )
                // FIXME: アイコンのレイアウト位置は指示通りになってる？
                Image(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .size(24.dp)
                        .clickable { }
                )
            }
        }
        items(gardenList) { garden ->
            GardenCell(
                garden = garden,
                // TODO: 選択状態
                isSelected = false
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

// FIXME: 灰色の背景色が見えてしまう
@Composable
fun ThemeCard(theme: Theme) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier.size(136.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = theme.resourceId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp)
            )
            // FIXME: センタリングするためにBoxを利用したがもっとシンプルに実装できない？
            Box(
                modifier = Modifier.fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    theme.title,
                    style = MaterialTheme.typography.h2,
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)

                )
            }
        }
    }
}

@Composable
fun GardenCell(
    garden: Garden,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(64.dp)
    ) {
        Image(
            painter = painterResource(id = garden.resourceId),
            contentDescription = garden.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .clip(MaterialTheme.shapes.small)
        )
        ConstraintLayout(
            modifier = Modifier
        ) {
            val (titleText, descriptionText, checkBox, divider) = createRefs()
            Text(
                garden.title,
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .constrainAs(titleText) {
                        width = Dimension.fillToConstraints
                        top.linkTo(parent.top)
                        start.linkTo(parent.start, margin = 16.dp)
                        end.linkTo(checkBox.start)
                    }
                    .paddingFromBaseline(top = 24.dp)
            )
            Text(
                garden.description,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .constrainAs(descriptionText) {
                        width = Dimension.fillToConstraints
                        top.linkTo(titleText.bottom)
                        start.linkTo(titleText.start)
                        end.linkTo(checkBox.start)
                        bottom.linkTo(parent.bottom)
                    }
                    .paddingFromBaseline(bottom = 24.dp)
            )
            Checkbox(
                checked = isSelected,
                onCheckedChange = {},
                colors = CheckboxDefaults.colors(
                    checkmarkColor = MaterialTheme.colors.onSecondary
                ),
                modifier = Modifier.constrainAs(checkBox) {
                    start.linkTo(descriptionText.end)
                    end.linkTo(parent.end)
                    // FIXME: descriptionTextのbaselineとbottomを揃えたかったが型が異なるのでできなかったので、仕方なくmarginを指定
                    bottom.linkTo(parent.bottom, margin = 24.dp)
                }
            )
            Divider(
                modifier = Modifier.constrainAs(divider) {
                    start.linkTo(parent.start, margin = 8.dp)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    AndroidDevChallenge3Theme {
        HomeScreen(
            searchText = "",
            onChangeSearchText = {},
            themeList = Theme.values().toList(),
            gardenList = Garden.values().toList()
        )
    }
}

@Preview
@Composable
fun ThemeCardPreview() {
    AndroidDevChallenge3Theme {
        ThemeCard(Theme.DesertChick)
    }
}

@Preview
@Composable
fun GardenCellPreview() {
    AndroidDevChallenge3Theme {
        GardenCell(Garden.Monstera, true)
    }
}