import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-compiler-embeddable")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-script-runtime")
    implementation("org.jetbrains.kotlin:kotlin-script-util")
    implementation("org.jetbrains.kotlin:kotlin-scripting-compiler-embeddable")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-test")
    implementation("org.jetbrains.kotlin:kotlin-util-io")
    implementation("org.junit.platform:junit-platform-launcher")
    implementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springframework.ws:spring-ws-core")
    implementation("org.springframework:spring-context")
    implementation("org.springframework.cloud:spring-cloud-function-web:3.1.5")
    implementation("org.apache.jmeter:ApacheJMeter_core")
    implementation("org.apache.jmeter:ApacheJMeter_http")
    implementation("kg.apc:jmeter-plugins-casutg")
    implementation(project(":capsa-it"))
    implementation("com.willowtreeapps.assertk:assertk-jvm")
}

tasks.getByName<BootJar>("bootJar") {
    enabled = true
}

tasks.named<BootJar>("bootJar") {
    archiveVersion.set("latest")
}