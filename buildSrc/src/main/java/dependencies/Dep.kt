package dependencies

/**
 * Created by vegeta
 */

object Dep {
    object GradlePlugin {
        const val android = "com.android.tools.build:gradle:4.1.1"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.21"
        const val hilt = "com.google.dagger:hilt-android-gradle-plugin:2.30.1-alpha"
    }

    object AndroidX {
        const val appCompat = "androidx.appcompat:appcompat:1.2.0"
        const val recyclerView = "androidx.recyclerview:recyclerview:1.1.0"
        const val cardView = "androidx.cardview:cardview:1.0.0"
        const val constraint = "androidx.constraintlayout:constraintlayout:2.0.4"
        const val design = "com.google.android.material:material:1.2.1"
        const val fragment = "androidx.fragment:fragment:1.3.0-rc01"
        const val fragmentKtx = "androidx.fragment:fragment-ktx:1.3.0-rc01"
        const val activityKtx = "androidx.activity:activity-ktx:1.1.0"
        const val coreKtx = "androidx.core:core-ktx:1.3.2"
        const val annotation = "androidx.annotation:annotation:1.1.0"
        const val multidex = "androidx.multidex:multidex:2.0.1"
        const val preference = "androidx.preference:preference:1.1.1"

        const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:2.2.0"
        const val lifecycleReactiveStreams = "androidx.lifecycle:lifecycle-reactivestreams:2.2.0"
        const val lifecycleViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"
        const val lifecycleLiveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:2.2.0"
        const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:2.2.0"

        object Arch {
            const val coreRuntime = "androidx.arch.core:core-runtime:2.1.0"
        }

        object Room {
            const val compiler = "androidx.room:room-compiler:2.2.5"
            const val runtime = "androidx.room:room-runtime:2.2.5"
            const val rxjava = "androidx.room:room-rxjava2:2.2.5"
            const val ktx = "androidx.room:room-ktx:2.2.5"
            const val testing = "androidx.room:room-testing:2.2.5"
        }

        object Navigation {
            const val common = "androidx.navigation:navigation-common:2.3.2"
            const val commonKtx = "androidx.navigation:navigation-common-ktx:2.3.2"
            const val fragment = "androidx.navigation:navigation-fragment:2.3.2"
            const val fragmentKtx = "androidx.navigation:navigation-fragment-ktx:2.3.2"
            const val ui = "androidx.navigation:navigation-ui:2.3.2"
            const val uiKtx = "androidx.navigation:navigation-ui-ktx:2.3.2"
            const val runtime = "androidx.navigation:navigation-runtime:2.3.2"
            const val runtimeKtx = "androidx.navigation:navigation-runtime-ktx:2.3.2"
        }

        object Work {
            const val commonKtx = "androidx.work:work-runtime-ktx:2.3.4"
            const val rxjava = "androidx.work:work-rxjava2:2.3.4"
        }
    }

    object Kotlin {
        const val stdlibJvm = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.4.21"
        const val reflection = "org.jetbrains.kotlin:kotlin-reflect:1.4.21"

        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.0"
        const val androidCoroutinesDispatcher =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.0"
        const val coroutinesRx2 =
            "org.jetbrains.kotlinx:kotlinx-coroutines-rx2:1.4.0"
        const val coroutinesPlayServices =
            "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.4.0"
        const val coroutinesGuava =
            "org.jetbrains.kotlinx:kotlinx-coroutines-guava:1.4.0"
        const val coroutinesTest =
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.0"
    }

    object RxJava {
        const val core = "io.reactivex.rxjava2:rxjava:2.2.19"
        const val android = "io.reactivex.rxjava2:rxandroid:2.1.1"
        const val kotlin = "io.reactivex.rxjava2:rxkotlin:2.4.0"
        const val replayingShareKotlin = "com.jakewharton.rx2:replaying-share-kotlin:2.2.0"
        const val replay = "com.jakewharton.rxrelay2:rxrelay:2.1.1"
        const val androidBle = "com.polidea.rxandroidble2:rxandroidble:1.11.1"
        const val debug = "com.akaita.java:rxjava2-debug:1.4.0"
    }

    object Dagger {
        const val core = "com.google.dagger:dagger:2.28.1"
        const val compiler = "com.google.dagger:dagger-compiler:2.28.1"
        const val android = "com.google.dagger:dagger-android:2.28.1"
        const val androidSupport = "com.google.dagger:dagger-android-support:2.28.1"
        const val processor = "com.google.dagger:dagger-android-processor:2.28.1"

        const val assistedAnnotations =
            "com.squareup.inject:assisted-inject-annotations-dagger2:0.5.2"
        const val assistedProcessor = "com.squareup.inject:assisted-inject-processor-dagger2:0.5.2"

        const val hiltViewModel = "androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha02"
        const val hilt = "com.google.dagger:hilt-android:2.30.1-alpha"
        const val hiltAndroidCompiler = "androidx.hilt:hilt-compiler:1.0.0-alpha02"
        const val hiltCompiler = "com.google.dagger:hilt-android-compiler:2.30.1-alpha"
    }

    @Suppress("unused")
    object OkHttp {
        const val client = "com.squareup.okhttp3:okhttp:3.14.6"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:3.14.6"
    }

    @Suppress("unused")
    object Retrofit {
        const val core = "com.squareup.retrofit2:retrofit:2.9.0"
        const val converterGson = "com.squareup.retrofit2:converter-gson:2.9.0"
        const val converterMoshi = "com.squareup.retrofit2:converter-moshi:2.9.0"
        const val adapterRxJava2 = "com.squareup.retrofit2:adapter-rxjava2:2.9.0"
    }

    object File {
        const val imageWorker = "com.github.ihimanshurawat:ImageWorker:1.1.1"
    }

    @Suppress("unused")
    object PermissionsDispatcher {
        const val permissionsDispatcher = "org.permissionsdispatcher:permissionsdispatcher:4.8.0"
        const val processor = "org.permissionsdispatcher:permissionsdispatcher-processor:4.8.0"
    }

    object Amination {
        const val lottie = "com.airbnb.android:lottie:3.4.0"
    }

    object Test {
        object Mock {
            const val assertk = "com.willowtreeapps.assertk:assertk-jvm:0.22"
            const val mockk = "io.mockk:mockk:1.9.3"
            const val mockkAndroid = "io.mockk:mockk-android:1.10.0"
        }

        object JUnit {
            const val api = "org.junit.jupiter:junit-jupiter-api:5.6.2"
            const val params = "org.junit.jupiter:junit-jupiter-params:5.6.2"
            const val engine = "org.junit.jupiter:junit-jupiter-engine:5.6.2"
            const val vintageEngine = "org.junit.vintage:junit-vintage-engine:5.6.2"
            const val junit4 = "junit:junit:4.13"
            const val platformLauncher = "org.junit.platform:junit-platform-launcher:1.6.2"
        }

        object Spek {
            const val dsl = "org.spekframework.spek2:spek-dsl-jvm:2.0.9"
            const val runner = "org.spekframework.spek2:spek-runner-junit5:2.0.9"
        }
    }
}