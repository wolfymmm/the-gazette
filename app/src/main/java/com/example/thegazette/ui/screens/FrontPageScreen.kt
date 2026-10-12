package com.example.thegazette.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thegazette.R

// Модель новини з локальним ресурсом картинки
data class ArticleMock(
    val id: Int,
    val title: String,
    val description: String = "",
    @DrawableRes val imageRes: Int
)

@Composable
fun FrontPageScreen(
    onArticleClick: (Int) -> Unit = {}
) {
    val categories = listOf("Front Page", "World", "Commerce", "Innovations", "Science", "Culture", "Sports")
    var selectedCategory by remember { mutableStateOf("Front Page") }

    val featuredArticle = remember {
        ArticleMock(
            id = 1,
            title = "Nasa heads to Scottish Highlands in its search for life on Mars",
            description = "Nasa scientists are studying rocks in the Scottish Highlands to help them understand if there was once life on Mars.",
            imageRes = R.drawable.img_mars
        )
    }

    val subArticles = remember {
        listOf(
            ArticleMock(
                id = 2,
                title = "Hamilton Revives the 1920s with the American Classic Cushion",
                imageRes = R.drawable.img_watch
            ),
            ArticleMock(
                id = 3,
                title = "Australian university coatings boost sensitivity for gravitational wave hunts",
                imageRes = R.drawable.img_space
            )
        )
    }

    Scaffold(
        topBar = {
            GazetteTopAppBar(
                title = "The Gazette",
                onMenuClick = { /* відкрити меню */ },
                onSearchClick = { /* відкрити пошук */ }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = innerPadding
        ) {
            // 1. Горизонтальні таби категорій
            item {
                CategoryTabs(
                    categories = categories,
                    selectedCategory = selectedCategory,
                    onCategorySelected = { selectedCategory = it }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            // 2. Головна картка статті (Featured News)
            item {
                FeaturedArticleCard(
                    article = featuredArticle,
                    onClick = { onArticleClick(featuredArticle.id) }
                )
                Spacer(modifier = Modifier.height(20.dp))
            }

            // 3. Розділювач
            item {
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    thickness = 0.8.dp,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            // 4. Дві колонки новин у рядку
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    subArticles.forEach { article ->
                        Box(modifier = Modifier.weight(1f)) {
                            SubArticleCard(
                                article = article,
                                onClick = { onArticleClick(article.id) }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun GazetteTopAppBar(
    title: String,
    onMenuClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = onMenuClick) {
            Icon(
                painter = painterResource(id = R.drawable.ic_menu),
                contentDescription = "Menu",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.size(24.dp)
            )
        }

        Text(
            text = title,
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        IconButton(onClick = onSearchClick) {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Search",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun CategoryTabs(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        items(categories) { category ->
            val isSelected = category == selectedCategory
            Column(
                modifier = Modifier
                    .clickable { onCategorySelected(category) }
                    .padding(vertical = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = category,
                    fontSize = 12.sp,
                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .height(1.5.dp)
                        .width(if (isSelected) 36.dp else 0.dp)
                        .background(if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent)
                )
            }
        }
    }
}

@Composable
fun FeaturedArticleCard(
    article: ArticleMock,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.6f), RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable { onClick() }
            .padding(14.dp)
    ) {
        Image(
            painter = painterResource(id = article.imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(14.dp))
        )

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = article.title,
            fontSize = 18.sp,
            lineHeight = 23.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        if (article.description.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = article.description,
                fontSize = 12.sp,
                lineHeight = 17.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun SubArticleCard(
    article: ArticleMock,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.6f), RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable { onClick() }
            .padding(10.dp)
    ) {
        Image(
            painter = painterResource(id = article.imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = article.title,
            fontSize = 13.sp,
            lineHeight = 17.sp,
            maxLines = 4,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@androidx.compose.ui.tooling.preview.Preview(name = "Light Mode", showBackground = true)
@androidx.compose.ui.tooling.preview.Preview(name = "Dark Mode", uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun FrontPageScreenPreview() {
    com.example.thegazette.ui.theme.TheGazetteTheme {
        FrontPageScreen()
    }
}