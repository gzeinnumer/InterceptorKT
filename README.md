# Interceptor

Please Enable [Retrofit](https://square.github.io/retrofit/) on your project, follow this step and [OkHttp](https://square.github.io/okhttp/interceptors/) on your project, follow this step.

- Add dependencies
```gradle
    implementation 'com.squareup.retrofit2:retrofit:2.6.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.5.0'
    implementation 'com.squareup.okhttp3:logging-interceptor:3.10.0'
```

- Make Class TokenInterceptor
```kotlin
class TokenInterceptor {
    val client: OkHttpClient
        get() {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Authorization", "Bearer Token")
                        .build()
                    chain.proceed(request)
                }
                .readTimeout(90, TimeUnit.SECONDS)
                .writeTimeout(90, TimeUnit.SECONDS)
                .connectTimeout(90, TimeUnit.SECONDS)
                .build()
        }
}
```

- Add configuration to RetrofitServer
```kotlin
object RetroServer {
    private const val base_url = "http://192.168.0.121/retrofit/"
    private fun setInit(): Retrofit {
        val client: OkHttpClient = TokenInterceptor().client
        return Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    val instance: ApiService
        get() = setInit().create(ApiService::class.java)
}

interface ApiService {
}
```
