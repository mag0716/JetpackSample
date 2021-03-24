package com.github.mag0716.composesamples.ui.androiddevchallenge3

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.mag0716.composesamples.R

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

@Composable
fun HomeScreen() {
    Scaffold(
        bottomBar = {
            BottomNavigation {
                BottomNavigationItem(
                    icon = {
                        Icon(
                            Icons.Filled.Home,
                            contentDescription = "Home",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = {
                        Text(
                            "Home",
                            style = MaterialTheme.typography.caption
                        )
                    },
                    selected = true,
                    onClick = {}
                )
                BottomNavigationItem(
                    icon = {
                        Icon(
                            Icons.Filled.FavoriteBorder,
                            contentDescription = "Favorites",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = {
                        Text(
                            "Favorites",
                            style = MaterialTheme.typography.caption
                        )
                    },
                    selected = false,
                    onClick = {}
                )
                BottomNavigationItem(
                    icon = {
                        Icon(
                            Icons.Filled.AccountCircle,
                            contentDescription = "Account",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = {
                        Text(
                            "Account",
                            style = MaterialTheme.typography.caption
                        )
                    },
                    selected = false,
                    onClick = {}
                )
                BottomNavigationItem(
                    icon = {
                        Icon(
                            Icons.Filled.ShoppingCart,
                            contentDescription = "Cart",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = {
                        Text(
                            "Cart",
                            style = MaterialTheme.typography.caption
                        )
                    },
                    selected = false,
                    onClick = {}
                )
            }
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(
                    top = 40.dp
                )
        ) {
            OutlinedTextField(
                value = "",
                onValueChange = {},
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

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}

@Preview
@Composable
fun ThemeCardPreview() {
    ThemeCard(Theme.DesertChick)
}