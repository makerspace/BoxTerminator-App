package stockholm.makerspace.boxterminator.network

import io.reactivex.Observable
import retrofit2.http.*


interface TerminatorApi {

    @POST("oauth/token")
    fun login(@Body request: LoginRequest): Observable<LoginResponse>

    @POST("multiaccess/box-terminator/validate-box")
    fun getMember(
        @Header("Authorization") token: String,
        @Body request: ValidateBoxRequest
    ): Observable<MemberResponse>

    @POST("multiaccess/box-terminator/nag")
    fun nag(
        @Header("Authorization") token: String,
        @Body request: NagRequest
    ): Observable<Unit>

    @GET("multiaccess/box-terminator/session-list")
    fun getSessionList(@Header("Authorization") token: String): Observable<Unit>
}