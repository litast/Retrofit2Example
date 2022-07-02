package lv.stauvere.lita.retrofit2jsonarray

import retrofit2.Response
import retrofit2.http.GET

interface TodoApi {

    @GET("cat/stamps")
    suspend fun getItems(): Response<List<Countries>>

}