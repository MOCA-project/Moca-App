package sptech.moca.api

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import sptech.moca.model.PorquinhoModel

interface EndpointPorquinho {

    @GET("porquinhos/{idUsuario}")
    fun getPorquinhos(@Path("idUsuario") idUsuario: Int): Call<List<PorquinhoModel>>

    @GET("porquinhos/mostrarPorcentagem/{idUsuario}/{idPorquinho}")
    fun mostrarPorcentagem(
        @Path("idUsuario") idUsuario: Long,
        @Path("idPorquinho") idPorquinho: Long
    ): Call<Double>

    @GET("porquinhos/{idUsuario}/{idPorquinho}")
    fun mostrarPorquinhoEscolhido(
        @Path("idUsuario") idUsuario: Long,
        @Path("idPorquinho") idPorquinho: Long
    ): Call<PorquinhoModel>

    @GET("porquinhos/historico/{idPorquinho}")
    fun mostrarExtratoPorquinho(@Path("idPorquinho") idPorquinho: Long): Call<PorquinhoModel>

    @Headers("Content-Type: application/json")
    @POST("porquinhos/")
    fun cadastrarPorquinho(@Body requestBody: RequestBody): Call<PorquinhoModel>
}