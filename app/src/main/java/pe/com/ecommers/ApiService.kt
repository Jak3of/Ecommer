package pe.com.ecommers

import retrofit2.http.GET
import retrofit2.Call

interface ApiService {
    @GET("/product")
    fun getProducts(): Call<List<ProductModel>>
}