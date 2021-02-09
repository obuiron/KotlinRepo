plugins {
    id("com.android.application")
    kotlin("android")
    id("com.apollographql.apollo").version(Apollo.version)
}

android {
    compileSdkVersion(AndroidSDK.compile)

    defaultConfig {
        applicationId = App.id
        minSdkVersion(AndroidSDK.min)
        targetSdkVersion(AndroidSDK.target)

        versionCode = App.Version.code
        versionName = App.Version.name

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        manifestPlaceholders["appAuthRedirectScheme"] = App.id
        buildConfigField("OAUTH_AUTH_ENDPOINT", "https://github.com/login/oauth/authorize")
        buildConfigField("OAUTH_TOKEN_ENDPOINT", "https://github.com/login/oauth/access_token")
        buildConfigField("OAUTH_REDIRECT", "${App.id}://oauth")
        buildConfigField("OAUTH_CLIENT_ID", "6fa919136a5ca12ee808")
        buildConfigField("OAUTH_SECRET", "745866000848b5f497ffe3ec857605f4ae3508f9")

        buildConfigField("GITHUB_GRAPHQL", "https://api.github.com/graphql")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true
        freeCompilerArgs = listOf("-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi")
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerVersion = Kotlin.version
        kotlinCompilerExtensionVersion = Jetpack.Compose.version
    }
}

dependencies {
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.1")

    implementation(Jetpack.Core.ktx)
    implementation(Jetpack.AppCompat.appcompat)
    implementation(Jetpack.Compose.ui)
    implementation(Jetpack.Compose.graphics)
    implementation(Jetpack.Compose.material)
    implementation(Jetpack.Compose.tooling)
    implementation(Jetpack.Lifecycle.runtime_ktx)
    implementation(Jetpack.Material.material)
    implementation(Jetpack.Navigation.compose)
    implementation(Jetpack.Paging.runtime)
    implementation(Jetpack.Paging.compose)
    implementation(Jetpack.Security.crypto)

    implementation(KotlinX.datetime)

    implementation(Apollo.runtime)
    implementation(Apollo.android)
    implementation(Apollo.coroutine)

    implementation(OpenId.appauth)

    implementation(Koin.core)
    implementation(Koin.android)
    implementation(Koin.viewmodel)
    implementation(Koin.compose)

    implementation(Accompanist.coil)

    testImplementation(Test.junit)
    testImplementation(Test.mockk)
    testImplementation(Test.Kluent.kluent)
    testImplementation(Test.Kluent.android)

    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}

apollo {
    generateKotlinModels.set(true)
    customTypeMapping.set(
        mapOf(
            "DateTime" to "kotlinx.datetime.LocalDateTime",
            "URI" to "kotlin.String"
        )
    )
}

fun com.android.build.api.dsl.BaseFlavor.buildConfigField(name: String, value: String) =
    buildConfigField("String", name, "\"$value\"")
