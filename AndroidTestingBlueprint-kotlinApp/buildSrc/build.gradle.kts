plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

//apply(from = "groovy.gradle")

repositories {
    google()
    jcenter()
}

gradlePlugin {
    plugins {
        create("PocTest") {
            id = "com.github.pgreze.gradle.android-black-box-test"
            implementationClass = "com.github.pgreze.abbtest.AndroidBlackBoxTestPlugin"
        }
    }
}

dependencies {
    implementation("com.android.tools.build:gradle:3.6.1") // compileOnly
}
