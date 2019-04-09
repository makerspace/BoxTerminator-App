package stockholm.makerspace.boxterminator.network

import io.reactivex.Observable
import retrofit2.http.*


interface TerminatorApi {

    @POST("oauth/token")
    fun login(@Body request: LoginRequest): Observable<LoginResponse>

    @GET("multiaccess/box-terminator/member")
    fun getMember(@Header("Bearer") token : String, @Query("member_number") memberNumber: String)

}