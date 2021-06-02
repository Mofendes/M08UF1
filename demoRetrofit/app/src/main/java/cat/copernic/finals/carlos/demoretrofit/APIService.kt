package cat.copernic.finals.carlos.demoretrofit

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {
    /*
    @GET
    fun getCursos(@Url url:String): Response<CursoResponse> {

    }
    */
    @GET
    //fun getCursos(@Url url:String): Deferred<Response<List<Curso>>>
    suspend fun getCursos(@Url url:String): Response<List<Curso>>
}