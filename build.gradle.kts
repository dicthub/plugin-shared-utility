
val kotlinVersion = "1.3.20"
val kotlinHtmlVersion = "0.6.12"


group = "org.dicthub"
version = "1.0-SNAPSHOT"

plugins {
    id("kotlin2js") version "1.3.20"
     `maven-publish`
}

repositories {
    jcenter()
}

dependencies {
    compile(kotlin("stdlib-js"))
    compile("org.jetbrains.kotlinx:kotlinx-html-js:$kotlinHtmlVersion")

    testCompile("org.jetbrains.kotlin:kotlin-test-js:$kotlinVersion")
}


tasks {
    compileKotlin2Js {
        kotlinOptions {
            sourceMap = true
        }
    }
}