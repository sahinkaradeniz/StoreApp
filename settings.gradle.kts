pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
rootProject.name = "FakeStoreApp"
include(":app")
include(":core")
include(":data")
include(":domain")
include(":presentation:home")
include(":navigation")
include(":presentation:detail")
include(":core-ui")
include(":presentation:favorites")
include(":presentation:basket")
include(":presentation:checkout")
