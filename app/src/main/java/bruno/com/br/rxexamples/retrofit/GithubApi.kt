package bruno.com.br.rxexamples.retrofit

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {

    /** See https://developer.github.com/v3/repos/#list-contributors */

    @GET("/repos/{owner}/{repo}/contributors")
    fun contributors(@Path("owner") owner: String, @Path("repo") repo: String)

    @GET("/repos/{owner}/{repo}/contributors")
    abstract fun getContributors(@Path("owner") owner: String, @Path("repo") repo: String): List<Contributor>

    /** See https://developer.github.com/v3/users/  */
    @GET("/users/{user}")
    abstract fun user(@Path("user") user: String): Observable<User>

    /** See https://developer.github.com/v3/users/  */
    @GET("/users/{user}")
    abstract fun getUser(@Path("user") user: String): User
}