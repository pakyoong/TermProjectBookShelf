package kr.ac.kumoh.ce.s20910531.TermProjectBookShelf
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavType
import androidx.navigation.navArgument

enum class BookScreen {
    List,
    Detail
}


@Composable
fun BookApp(bookList: List<Book>) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = BookScreen.List.name
    ) {
        composable(route = BookScreen.List.name) {
            BookList(bookList) {
                // "Detail/$index" 형태의 문자열로 네비게이션
                navController.navigate(it)
            }
        }
        composable(
            route = BookScreen.Detail.name + "/{index}",
            arguments = listOf(navArgument("index") {
                type = NavType.IntType
            })
        ) {
            val index = it.arguments?.getInt("index") ?: -1
            if (index >= 0)
                BookDetail(bookList[index])
        }
    }
}

@Composable
fun BookList(list: List<Book>, onNavigateToDetail: (String) -> Unit) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        itemsIndexed(list) { index, book ->
            BookItem(index, book, onNavigateToDetail)
        }
    }
}

@Composable
fun BookItem(index: Int, book: Book, onNavigateToDetail: (String) -> Unit) {
    Card(
        modifier = Modifier.clickable {
            onNavigateToDetail(BookScreen.Detail.name + "/$index")
        },
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            AsyncImage(
                model = book.imageUrl,
                contentDescription = "책 표지",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(percent = 10))
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                TextTitle(book.title)
                TextAuthor("저자: ${book.author}")
            }
        }
    }
}

@Composable
fun BookDetail(book: Book) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RatingBar(book.rating)
        Spacer(modifier = Modifier.height(16.dp))
        // 여기에 책의 상세 정보를 표시
        Text(
            book.title,
            fontSize = 40.sp,
            textAlign = TextAlign.Center,
            lineHeight = 45.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        AsyncImage(
            model = book.imageUrl,
            contentDescription = "책 표지",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(200.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(book.author, fontSize = 30.sp)
        Spacer(modifier = Modifier.height(16.dp))
        book.summary?.let {
            Text(
                it,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                lineHeight = 25.sp
            )
        }
        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://m.yes24.com/Search?query=+${book.title}")
            )
            startActivity(context, intent, null)
        }) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Yes24Icon()
                Spacer(modifier = Modifier.width(16.dp))
                Text("도서 검색", fontSize = 40.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun TextTitle(title: String) {
    Text(title, fontSize = 30.sp)
}

@Composable
fun TextAuthor(author: String) {
    Text(author, fontSize = 20.sp)
}

@Composable
fun RatingBar(stars: Int) {
    Row {
        repeat(stars) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "stars",
                modifier = Modifier.size(48.dp),
                tint = Color.Red)
        }
    }
}

@Composable
fun Yes24Icon() {
    Box(
        modifier = Modifier
            .background(Color(0xFF0080FF)) // 배경색 설정
            .size(100.dp), // 아이콘 크기 설정
        contentAlignment = Alignment.Center // 내용을 중앙에 위치시킴
    ) {
        Text(
            text = "yes24", // 텍스트 설정
            color = Color.White, // 텍스트 색상 설정
            fontSize = 30.sp, // 텍스트 크기 설정
            fontWeight = FontWeight.Bold // 텍스트 굵기 설정
        )
        Canvas(modifier = Modifier.size(80.dp)) {
            // 'Y' 위에 타원형 눈모양 그리기
            drawOval(
                color = Color(0xFF001826), // 눈 색상 설정
                topLeft = Offset(x =  size.width * 0.01f, y = size.height * 0.34f),
                size = Size(width = size.width * 0.06f, height = size.height * 0.1f)
            )
            drawOval(
                color = Color(0xFF001826), // 눈 색상 설정
                topLeft = Offset(x = size.width * 0.08f, y = size.height * 0.34f),
                size = Size(width = size.width * 0.06f, height = size.height * 0.1f)
            )
        }
    }
}
