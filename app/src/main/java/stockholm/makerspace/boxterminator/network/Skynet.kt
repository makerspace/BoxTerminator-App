package stockholm.makerspace.boxterminator.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import stockholm.makerspace.boxterminator.utils.SkynetDatastore
import java.util.concurrent.TimeUnit

const val makerAdminUrl = "https://api.dev.makerspace.se/"
const val localMakerAdminUrl = "http://10.20.0.223:8010/"

class Skynet : KoinComponent {

    private val skynetDatastore : SkynetDatastore by inject()

    fun getClient(): TerminatorApi {
        skynetDatastore.refreshSession()

        val gson = GsonBuilder()
            .create()

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .baseUrl(localMakerAdminUrl)
            .build()
        return retrofit.create(TerminatorApi::class.java)
    }
}