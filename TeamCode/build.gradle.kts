import com.android.build.gradle.internal.tasks.factory.dependsOn
import java.util.*

//
// build.gradle.kts in TeamCode
//
// Most of the definitions for building your module reside in a common, shared
// file 'build.common.gradle'. Being factored in this way makes it easier to
// integrate updates to the FTC into your code. If you really need to customize
// the build definitions, you can place those customizations in this file, but
// please think carefully as to whether such customizations are really necessary
// before doing so.

// Include common definitions from above.
apply(from = "../build.common.gradle")
apply(from = "../build.dependencies.gradle")

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "org.firstinspires.ftc.teamcode"

    packagingOptions {
        jniLibs {
            useLegacyPackaging = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlinOptions {
        jvmTarget = "21"
    }

    // Add generated sources if needed
    sourceSets {
        getByName("main") {
            java.srcDir("${buildDir}/generated/main/java")
        }
    }
}

dependencies {
    implementation(project(":FtcRobotController"))
    // implementation("androidx.core:core-ktx:1.17.0")
    testImplementation(kotlin("test"))
}

// Helper to run `git describe`
fun gitDescribe(): String = try {
    val p = ProcessBuilder("git", "describe", "--tags", "--always")
        .directory(rootProject.rootDir)
        .redirectErrorStream(true)
        .start()
    p.waitFor()
    p.inputStream.bufferedReader().readText().trim()
} catch (_: Exception) {
    "unknown"
}

// Task to update gradle.properties with the current git description
tasks.register("updateVersionFromGit") {
    group = "versioning"
    description = "Writes GIT_DESCRIBE into gradle.properties"
    doLast {
        val propsFile = file("${rootProject.projectDir}/gradle.properties")
        val props = Properties()
        if (propsFile.exists()) {
            propsFile.inputStream().use { props.load(it) }
        }
        props.setProperty("GIT_DESCRIBE", gitDescribe())
        propsFile.outputStream().use { props.store(it, "Updated by Gradle task") }
        println("gradle.properties updated: GIT_DESCRIBE=${props.getProperty("GIT_DESCRIBE")}")
    }
}

val buildNumber: String = (project.findProperty("GIT_DESCRIBE") as String?) ?: ""

// Task to generate BuildConstants.java with build information
tasks.register("generateBuildConstants") {
    group = "versioning"
    println(buildNumber)
    val outputDir = "${projectDir}/src/main/java"
    inputs.property("GIT-DESCRIBE", buildNumber)
    outputs.dir(outputDir)
    doLast {
        val pkg = "org.cr"
        val file = file("${outputDir}/${pkg.replace('.', '/')}/BuildConstants.java")
        file.parentFile.mkdirs()
        file.writeText(
            """package $pkg;
public final class BuildConstants {
  private BuildConstants() {}
  public static final String BUILD_NUMBER = "$buildNumber" ;
  public static final String NAME = "Ball Thrower" + BUILD_NUMBER;
}
""".trimIndent()
        )
    }
}

//tasks.named("compileReleaseKotlin").dependsOn("generateBuildConstants")
tasks.named("generateBuildConstants").dependsOn("updateVersionFromGit")
