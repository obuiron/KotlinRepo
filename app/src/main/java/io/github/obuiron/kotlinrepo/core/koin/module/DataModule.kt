package io.github.obuiron.kotlinrepo.core.koin.module

import com.apollographql.apollo.ApolloClient
import io.github.obuiron.kotlinrepo.BuildConfig
import io.github.obuiron.kotlinrepo.core.apollo.StringAdapter
import io.github.obuiron.kotlinrepo.data.local.EncryptedSharedPrefsDataSource
import io.github.obuiron.kotlinrepo.data.repository.AuthRepository
import io.github.obuiron.kotlinrepo.data.repository.GithubRepository
import io.github.obuiron.kotlinrepo.type.CustomType
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module

val dataModule = module {
    single { AuthRepository(get(), get()) }
    single { GithubRepository(get()) }

    single { EncryptedSharedPrefsDataSource(get()) }
    single { provideApollo(get()) }
}

fun provideApollo(repository: AuthRepository): ApolloClient {
    val authorization = Interceptor { chain ->
        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer ${repository.token()}")
            .build()
        chain.proceed(request)
    }

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authorization)
        .build()

    return ApolloClient.builder()
        .serverUrl(BuildConfig.GITHUB_GRAPHQL)
        .okHttpClient(okHttpClient)
        .addCustomTypeAdapter(CustomType.URI, StringAdapter)
        .build()
}
