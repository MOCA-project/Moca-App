package sptech.moca.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import sptech.moca.model.HomeInformationsModel

interface EndpointExtrato {

    // Requsição das informações do Extrato
    @Headers("Content-Type: application/json")
    @GET("extrato/{idUsuario}/{mes}/{ano}")
    fun getInformationEextract(
        @Path("idUsuario") idUsuario: Long,
        @Path("mes") mes: Int,
        @Path("ano") ano: Int
    ): Call<HomeInformationsModel>
}