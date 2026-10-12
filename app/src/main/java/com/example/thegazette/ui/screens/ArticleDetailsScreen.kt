package com.example.thegazette.ui.screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thegazette.R

data class ArticleDetailData(
    val title: String,
    val author: String,
    val date: String,
    val firstParagraph: String,
    val bodyParagraphs: List<String>
)

@Composable
fun ArticleDetailsScreen(
    onBackClick: () -> Unit = {},
    article: ArticleDetailData = ArticleDetailData(
        title = "Nasa heads to\nScottish Highlands\nin its search for life\non Mars",
        author = "By Lora Palmer",
        date = "October 11th, 2026 10:04",
        firstParagraph = "asa scientists are studying rocks in the Scottish Highlands to help them understand if there was once life on Mars.",
        bodyParagraphs = listOf(
            "The US space agency announced last year that its Perseverance rover had found potential biosignatures, markings on rocks possibly made by ancient microbes.",
            "This summer, scientists took samples from a geological feature called the Stoer Formation around Clachtoll, in Assynt, in the north-west Highlands.",
            "Nasa said the area's rocks formed in environments comparable to ancient Martian lakes and rivers, but could also hold evidence of microbial life from 1.2 billion years ago.",
            "Clachtoll is a small crofting community on the Sutherland coast about 100 miles north of Inverness, and is a popular destination along the North Coast 500 tourist route."
        )
    )
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            ArticleTopBar(
                onBackClick = onBackClick,
                onShareClick = {
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_SUBJECT, article.title)
                        putExtra(Intent.EXTRA_TEXT, "${article.title}\n\nRead more in The Gazette.")
                    }
                    context.startActivity(Intent.createChooser(shareIntent, "Share dispatch"))
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = article.title,
                fontSize = 24.sp,
                lineHeight = 30.sp,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            HorizontalDivider(
                modifier = Modifier.width(100.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = article.author,
                fontSize = 12.sp,
                color = Color(0xFF3E5A44)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = article.date,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.55f)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Буквиця (First Big Letter) та перший абзац
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "N",
                    fontSize = 46.sp,
                    lineHeight = 44.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(end = 6.dp)
                )

                Text(
                    text = article.firstParagraph,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Основний текст статті
            article.bodyParagraphs.forEach { paragraph ->
                Text(
                    text = paragraph,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(14.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun ArticleTopBar(
    onBackClick: () -> Unit,
    onShareClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Кнопка Back
        Row(
            modifier = Modifier
                .clip(CircleShape)
                .clickable { onBackClick() }
                .padding(vertical = 4.dp, horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back), // Замініть на: R.drawable.ic_arrow_back
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "Back",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        // Кругла кнопка Share
        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .clickable { onShareClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_share), // Замініть на: R.drawable.ic_share
                contentDescription = "Share dispatch",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(name = "Light Mode", showBackground = true)
@androidx.compose.ui.tooling.preview.Preview(name = "Dark Mode", uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ArticleDetailsScreenPreview() {
    com.example.thegazette.ui.theme.TheGazetteTheme {
        ArticleDetailsScreen()
    }
}