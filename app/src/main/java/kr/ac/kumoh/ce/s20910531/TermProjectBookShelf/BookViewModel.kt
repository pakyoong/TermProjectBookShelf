package kr.ac.kumoh.ce.s20910531.TermProjectBookShelf

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BookViewModel() : ViewModel() {
    // 서버 URL을 여러분의 백엔드 서비스 URL로 변경하세요
    private val SERVER_URL = "https://port-0-termprojectbackend-1drvf2llomgg2g2.sel5.cloudtype.app/"
    private val bookApi: BookApi
    private val _bookList = MutableLiveData<List<Book>>()
    val bookList: LiveData<List<Book>>
        get() = _bookList

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        bookApi = retrofit.create(BookApi::class.java)
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                val response = bookApi.getBooks()
                _bookList.value = response
            } catch (e: Exception) {
                Log.e("fetchData()", e.toString())
            }
        }
    }
}
