plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.ksp)
    alias(libs.plugins.dagger.hilt)
}

android {
    namespace = "com.loanmanagementapp.network"
    compileSdk = 36

    kotlin {
        jvmToolchain(17)
    }
}

dependencies {
    implementation(libs.hilt.android)
    implementation(libs.google.gson)
    api(libs.bundles.network)
    implementation(projects.contract)
    ksp(libs.hilt.compiler)
}
