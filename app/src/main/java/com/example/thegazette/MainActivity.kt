package com.example.thegazette

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thegazette.ui.screens.AccountScreen
import com.example.thegazette.ui.screens.ArchivesScreen
import com.example.thegazette.ui.screens.ArticleDetailsScreen
import com.example.thegazette.ui.screens.FrontPageScreen
import com.example.thegazette.ui.screens.WorldMapScreen
import com.example.thegazette.ui.theme.TheGazetteTheme

enum class BottomNavItem(val title: String, val iconRes: Int) {
    FRONT("FRONT", R.drawable.ic_front),
    WORLD("WORLD", R.drawable.ic_world),
    SAVED("SAVED", R.drawable.ic_saved),
    ACCOUNT("ACCOUNT", R.drawable.ic_account)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TheGazetteTheme {
                MainRootScreen()
            }
        }
    }
}

@Composable
fun MainRootScreen() {
    val navItems = remember { BottomNavItem.entries.toTypedArray() }
    var currentItem by remember { mutableStateOf(BottomNavItem.FRONT) }

    // Стан перегляду конкретної статті (null = відображається стрічка)
    var selectedArticleId by remember { mutableStateOf<Int?>(null) }

    // Обробка системного жесту/кнопки "Назад" на пристрої
    BackHandler(enabled = selectedArticleId != null) {
        selectedArticleId = null
    }

    if (selectedArticleId != null) {
        // Екран детального читання статті на повний екран
        ArticleDetailsScreen(
            onBackClick = { selectedArticleId = null }
        )
    } else {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.background,
            bottomBar = {
                NavigationBar(
                    modifier = Modifier
                        .navigationBarsPadding() // 1. Безпечна зона системних кнопок Android знизу
                        .fillMaxWidth()
                        .height(56.dp), // 2. Компактна фіксована висота самого меню без роздутих верхів
                    containerColor = MaterialTheme.colorScheme.background,
                    tonalElevation = 0.dp,
                    windowInsets = WindowInsets(0, 0, 0, 0) // 3. Прибираємо дефолтний відступ Material 3 вгору
                ) {
                    navItems.forEach { item ->
                        val isSelected = currentItem == item
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { currentItem = item },
                            icon = {
                                Icon(
                                    painter = painterResource(id = item.iconRes),
                                    contentDescription = item.title,
                                    modifier = Modifier.size(20.dp)
                                )
                            },
                            label = {
                                Text(
                                    text = item.title,
                                    fontSize = 10.sp,
                                    lineHeight = 12.sp
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.onBackground,
                                selectedTextColor = MaterialTheme.colorScheme.onBackground,
                                indicatorColor = MaterialTheme.colorScheme.surface,
                                unselectedIconColor = MaterialTheme.colorScheme.secondary,
                                unselectedTextColor = MaterialTheme.colorScheme.secondary
                            )
                        )
                    }
                }
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                when (currentItem) {
                    BottomNavItem.FRONT -> {
                        FrontPageScreen(
                            onArticleClick = { articleId ->
                                selectedArticleId = articleId
                            }
                        )
                    }
                    BottomNavItem.WORLD -> {
                        WorldMapScreen(
                            onArticleClick = { articleId ->
                                selectedArticleId = articleId
                            }
                        )
                    }
                    BottomNavItem.SAVED -> {
                        ArchivesScreen(
                            onArticleClick = { articleId ->
                                selectedArticleId = articleId
                            }
                        )
                    }
                    BottomNavItem.ACCOUNT -> {
                        AccountScreen()
                    }
                }
            }
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(name = "Main Screen - Light", showBackground = true)
@androidx.compose.ui.tooling.preview.Preview(name = "Main Screen - Dark", uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MainRootScreenPreview() {
    com.example.thegazette.ui.theme.TheGazetteTheme {
        MainRootScreen()
    }
}