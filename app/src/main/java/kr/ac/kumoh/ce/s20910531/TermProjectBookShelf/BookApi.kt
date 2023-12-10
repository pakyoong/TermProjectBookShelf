package kr.ac.kumoh.ce.s20910531.TermProjectBookShelf
import retrofit2.http.GET

interface BookApi {
    @GET("book")
    suspend fun getBooks(): List<Book>
}