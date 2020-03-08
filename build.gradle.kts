
val kotlinVersion = "1.3.70"
val kotlinHtmlVersion = "0.7.1"


group = "org.dicthub"
version = "1.0-SNAPSHOT"

plugins {
    id("kotlin2js") version "1.3.70"
     `maven-publish`
}

repositories {
    jcenter()
}

dependencies {
    compileOnly(kotlin("stdlib-js"))
    compileOnly("org.jetbrains.kotlinx:kotlinx-html-js:$kotlinHtmlVersion")

    testCompileOnly("org.jetbrains.kotlin:kotlin-test-js:$kotlinVersion")
}


tasks {
    compileKotlin2Js {
        kotlinOptions {
            sourceMap = true
        }
    }
}