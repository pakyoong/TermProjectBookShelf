package kr.ac.kumoh.ce.s20910531.TermProjectBookShelf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

// 커스텀 테마 정의
private val BookShelfThemeColors = lightColorScheme(
    primary = Color(0xFF8C7B75), // 진한 갈색
    secondary = Color(0xFFD7C0AE), // 연한 베이지색
    background = Color(0xFFFDF6E3), // 아이보리색
    surface = Color(0xFFFAF3E0), // 연한 아이보리색
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

@Composable
fun BookShelfTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = BookShelfThemeColors,
        typography = MaterialTheme.typography,
        shapes = MaterialTheme.shapes,
        content = content
    )
}


class MainActivity : ComponentActivity() {
    private val viewModel: BookViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookShelfTheme {
                MainScreen(viewModel)
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: BookViewModel) {
    val bookList by viewModel.bookList.observeAsState(emptyList())

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        BookApp(bookList)
    }
}
