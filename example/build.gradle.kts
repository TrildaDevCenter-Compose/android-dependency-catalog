val versionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")
plugins {
    alias(libs.plugins.android.library)

    alias(libs.plugins.android.application).apply(false)
    alias(libs.plugins.automattic.configure).apply(false)
    alias(libs.plugins.automattic.fetchstyle).apply(false)
    alias(libs.plugins.automattic.publishToS3).apply(false)
    alias(libs.plugins.kotlin.android).apply(false)
    alias(libs.plugins.kotlin.detekt).apply(false)
    alias(libs.plugins.kotlin.jvm).apply(false)
    alias(libs.plugins.kotlin.kapt).apply(false)
    alias(libs.plugins.kotlin.parcelize).apply(false)
    alias(libs.plugins.dependency.analysis).apply(false)
}

android {
    namespace = "com.automattic.androidDependencyCatalog.example"

    defaultConfig {
        compileSdk = 33
        minSdk = 24
    }
    lint {
        lintConfig = file("config/lint.xml")
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidx.compose.compiler.get()
    }
}

dependencies {
    versionCatalog.getLibraryAliases().forEach { libraryAlias ->
        versionCatalog.findLibrary(libraryAlias).ifPresent {
            if (libraryAlias.contains("bom")) {
                implementation(platform(it))
            } else {
                implementation(it)
            }
        }
    }
}
