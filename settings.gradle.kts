@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        // added to download PRDownloader library
        @Suppress("DEPRECATION")
        //noinspection JcenterRepositoryObsolete
        jcenter()
    }
}

rootProject.name = "ApkDownloader"
include(":app")
