package com.github.mag0716.composesamples.ui.androiddevchallenge3

import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
import com.github.mag0716.composesamples.R
import com.github.mag0716.composesamples.ui.theme.AndroidDevChallenge3Theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

enum class Theme(
    val title: String,
    @DrawableRes val resourceId: Int
) {
    DesertChick("Desert chic", R.drawable.desert_chic),
    TinyTerrariums("Tiny terrariums", R.drawable.tiny_terrariums),
    JungleVibes("Jungle vibes", R.drawable.jungle_vibes),
    EasyCare("Easy care", R.drawable.easy_care),
    Statements("Statements", R.drawable.statements)
}

enum class Garden(
    val title: String,
    val description: String,
    @DrawableRes val resourceId: Int
) {
    Monstera("Monstera", "This is a description", R.drawable.monstera),
    Aglaonema("Aglaonema", "This is a description", R.drawable.aglaonema),
    PeachLily("Peace lily", "This is a description", R.drawable.peach_lily),
    FiddleLeafTree("Fiddle leaf tree", "This is a description", R.drawable.fiddle_leaf_tree),
    SnakePlant("Snake plant", "This is a description", R.drawable.snake_plant),
    Pothos("Pothos", "This is a description", R.drawable.pothos)
}

@Composable
fun HomeScreen() {
    var searchText by remember { mutableStateOf("") }
    HomeScreen(
        searchText = searchText,
        onChangeSearchText = { searchText = it }
    )
}

@Composable
fun HomeScreen(
    searchText: String,
    onChangeSearchText: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(
                top = 40.dp,
                // BottomNavigation分の高さをセットしておく必要がある
                bottom = 56.dp
            )
    ) {
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
                .padding(horizontal = 16.dp),
        )
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
                .padding(horizontal = 16.dp)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            for (theme in Theme.values()) {
                ThemeCard(theme = theme)
            }
        }
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
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
        for ((index, garden) in Garden.values().withIndex()) {
            GardenCell(
                garden = garden,
                isSelected = index == 0,
                modifier = Modifier.padding(
                    horizontal = 16.dp
                )
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
        HomeScreen()
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