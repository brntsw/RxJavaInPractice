package bruno.com.br.rxexamples.retrofit

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GithubService {

    companion object {
        fun createGithubService(githubToken: String): GithubApi{
            var builder = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.github.com")

            if(githubToken.isNotEmpty()){
                val client = OkHttpClient.Builder()
                    .addInterceptor {
                        val request: Request = it.request()
                        val newReq = request
                            .newBuilder()
                            .addHeader("Authorization", String.format("token %s", githubToken))
                            .build()

                        it.proceed(newReq)
                    }.build()

                builder.client(client)
            }

            return builder.build().create(GithubApi::class.java)
        }
    }

}