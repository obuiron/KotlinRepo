object App {
    const val id = "io.github.obuiron.kotlinrepo"

    object Version {
        const val code = 1
        const val name = "1.0"
    }
}

object AndroidSDK {
    const val min = 24
    const val compile = 30
    const val target = compile
}

object Kotlin {
    const val version = "1.4.21-2"
}

object Koin {
    const val version = "2.2.2"

    const val core = "org.koin:koin-core:$version"
    const val android = "org.koin:koin-android:$version"
    const val viewmodel = "org.koin:koin-androidx-viewmodel:$version"
    const val compose = "org.koin:koin-androidx-compose:$version"
}

object Jetpack {
    object Core {
        const val ktx = "androidx.core:core-ktx:1.3.2"
    }

    object AppCompat {
        const val appcompat = "androidx.appcompat:appcompat:1.2.0"
    }

    object Compose {
        const val version = "1.0.0-alpha11"

        const val ui = "androidx.compose.ui:ui:$version"
        const val graphics = "androidx.compose.ui:ui-graphics:$version"
        const val material = "androidx.compose.material:material:$version"
        const val tooling = "androidx.compose.ui:ui-tooling:$version"
    }

    object Lifecycle {
        const val runtime_ktx = "androidx.lifecycle:lifecycle-runtime-ktx:2.3.0-rc01"
    }

    object Material {
        const val material = "com.google.android.material:material:1.2.1"
    }

    object Navigation {
        const val compose = "androidx.navigation:navigation-compose:1.0.0-alpha06"
    }

    object Paging {
        const val runtime = "androidx.paging:paging-runtime:3.0.0-alpha13"
        const val compose = "androidx.paging:paging-compose:1.0.0-alpha06"
    }

    object Security {
        const val crypto = "androidx.security:security-crypto:1.1.0-alpha03"
    }
}

object Apollo {
    const val version = "2.5.3"

    const val runtime = "com.apollographql.apollo:apollo-runtime:$version"
    const val android = "com.apollographql.apollo:apollo-android-support:$version"
    const val coroutine = "com.apollographql.apollo:apollo-coroutines-support:$version"
}

object KotlinX {
    const val datetime = "org.jetbrains.kotlinx:kotlinx-datetime:0.1.1"
}

object OpenId {
    const val appauth = "net.openid:appauth:0.7.1"
}

object Accompanist {
    const val coil = "dev.chrisbanes.accompanist:accompanist-coil:0.5.0"
}

object Tehras {
    const val common = "com.github.tehras.charts:common:d34308d718"
    const val line_chart = "com.github.tehras.charts:line:d34308d718"
}

object Test {
    const val junit = "junit:junit:4.13.1"
    const val mockk = "io.mockk:mockk:1.10.5"

    object Kluent {
        const val version = "1.64"

        const val kluent = "org.amshove.kluent:kluent:$version"
        const val android = "org.amshove.kluent:kluent-android:$version"
    }
}
