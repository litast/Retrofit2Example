package lv.stauvere.lita.retrofit2jsonarray

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val api: TodoApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.colnect.net/en/api/" + BuildConfig.COLNECT_KEY + "/countries/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TodoApi::class.java)
    }

}