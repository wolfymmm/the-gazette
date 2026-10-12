package com.example.thegazette.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thegazette.R

data class SavedArticleItem(
    val id: Int,
    val title: String,
    val date: String,
    val category: String,
    @DrawableRes val categoryIconRes: Int
)

@Composable
fun ArchivesScreen(
    onArticleClick: (Int) -> Unit = {},
    onRemoveBookmark: (Int) -> Unit = {}
) {
    var savedArticles by remember {
        mutableStateOf(
            listOf(
                SavedArticleItem(
                    id = 1,
                    title = "Nasa heads to Scottish Highlands in its search for life on...",
                    date = "Oct 7, 2026",
                    category = "Science",
                    categoryIconRes = R.drawable.ic_science
                ),
                SavedArticleItem(
                    id = 2,
                    title = "Hamilton Revives the 1920s with the American Classic Cushion",
                    date = "Oct 6, 2026",
                    category = "Fashion",
                    categoryIconRes = R.drawable.ic_fashion
                ),
                SavedArticleItem(
                    id = 3,
                    title = "Australian university coatings boost sensitivity for gra...",
                    date = "Oct 5, 2026",
                    category = "Innovations",
                    categoryIconRes = R.drawable.ic_science
                ),
                SavedArticleItem(
                    id = 4,
                    title = "Ronaldo says Jesus broke his word, apologizes to Portugal team...",
                    date = "Oct 4, 2026",
                    category = "Sports",
                    categoryIconRes = R.drawable.ic_sport
                ),
                SavedArticleItem(
                    id = 5,
                    title = "2026 Nissan X-Trail Rock Creek: Less rock, more easy listening?",
                    date = "Oct 3, 2026",
                    category = "Commerce",
                    categoryIconRes = R.drawable.ic_commerce
                )
            )
        )
    }

    val dynamicCategories = remember(savedArticles) {
        listOf("All topics") + savedArticles.map { it.category }.distinct()
    }
    var selectedCategory by remember { mutableStateOf("All topics") }

    val filteredArticles = remember(selectedCategory, savedArticles) {
        if (selectedCategory == "All topics") savedArticles
        else savedArticles.filter { it.category == selectedCategory }
    }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 18.dp, bottom = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Archives",
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Рядок фільтрів (категорії)
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(dynamicCategories) { categoryName ->
                    val isSelected = categoryName == selectedCategory
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedCategory = categoryName },
                        label = {
                            Text(
                                text = categoryName,
                                fontSize = 12.sp,
                            )
                        },
                        shape = RoundedCornerShape(20.dp),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFF9E7B4F),
                            selectedLabelColor = Color(0xFFF6F2E9),
                            containerColor = MaterialTheme.colorScheme.surface,
                            labelColor = MaterialTheme.colorScheme.tertiary // Зелений колір неактивного тексту фільтра
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = isSelected,
                            borderColor = if (isSelected) Color(0xFF9E7B4F) else MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f),
                            borderWidth = 1.dp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Сітка збережених карток (2 колонки)
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredArticles, key = { it.id }) { article ->
                    SavedArticleCard(
                        article = article,
                        onCardClick = { onArticleClick(article.id) },
                        onBookmarkClick = {
                            onRemoveBookmark(article.id)
                            savedArticles = savedArticles.filter { it.id != article.id }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SavedArticleCard(
    article: SavedArticleItem,
    onCardClick: () -> Unit,
    onBookmarkClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                shape = RoundedCornerShape(18.dp)
            )
            .clickable { onCardClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = article.categoryIconRes),
                    contentDescription = article.category,
                    tint = MaterialTheme.colorScheme.tertiary, // Автоматично обирає зелений для світлої або темної теми
                    modifier = Modifier.size(20.dp)
                )

                IconButton(
                    onClick = onBookmarkClick,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_saved_light_brown),
                        contentDescription = "Remove bookmark",
                        tint = Color(0xFF9E7B4F),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Text(
                text = article.title,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )

            Column(modifier = Modifier.fillMaxWidth()) {
                DottedDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.6f))
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = article.date,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                )
            }
        }
    }
}

@Composable
fun DottedDivider(
    modifier: Modifier = Modifier,
    color: Color
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(1.dp)
            .drawBehind {
                drawLine(
                    color = color,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = 2f,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 6f), 0f)
                )
            }
    )
}