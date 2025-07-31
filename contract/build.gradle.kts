plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.hilt.ksp)
    alias(libs.plugins.dagger.hilt)
}

android {
    namespace = "com.loanmanagementapp.contract"
    compileSdk = 36

    kotlin {
        jvmToolchain(17)
    }
}

dependencies {
    implementation(libs.javax.inject)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.google.gson)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}
