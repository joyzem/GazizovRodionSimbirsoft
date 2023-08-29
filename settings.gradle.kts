pluginManagement {
    includeBuild("buildLogic")
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
    }
}
rootProject.name = "AndroidPractice"
include(":app")
include(":buildlogic:convention")
include(":mvi")
include(":core:model")
include(":core:domain")
include(":feature:auth")
include(":core:ui")
include(":core:design-system")
include(":core:di")
include(":core:data")
include(":core:network")
include(":core:database")
include(":utils")
include(":feature:help")
include(":feature:profile")
include(":feature:search")
include(":feature:news")
include(":feature:news-details-api")
include(":feature:news-details-impl")
