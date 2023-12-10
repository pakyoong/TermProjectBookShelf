package kr.ac.kumoh.ce.s20910531.TermProjectBookShelf

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val rating: Int,
    val summary: String?,
    val imageUrl: String  // 이미지 URL 추가
)