package stockholm.makerspace.boxterminator.network

import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST


interface TerminatorApi {

    @POST("oauth/token")
    fun login(@Body request: LoginRequest): Observable<LoginResponse>

}