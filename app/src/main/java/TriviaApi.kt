import io.appwrite.starterkit.data.models.CategoryResponse
import retrofit2.Response
import retrofit2.http.GET

interface TriviaApi {
    @GET("api_category.php")
    suspend fun getCategories(): Response<CategoryResponse>
}

