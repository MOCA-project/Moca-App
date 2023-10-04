package sptech.moca.service

import retrofit2.http.GET
import sptech.moca.response.RecipesResponse

interface RecipeService {

    @GET("asd")
    suspend fun getRandomRecipes(): RecipesResponse
}