plugins {
    id 'java'
    id 'war'
}

group 'com.kurtis_project'
version '1.0-SNAPSHOT'

repositories {
    mavenCentral()
    google()
}

ext {
    junitVersion = '5.7.0'
}

sourceCompatibility = 1.8
targetCompatibility = 1.8

dependencies {
    compileOnly('javax.json.bind:javax.json.bind-api:1.0')
    compileOnly('jakarta.servlet:jakarta.servlet-api:5.0.0')
    compile group: 'com.google.code.gson', name: 'gson', version: '2.8.5'
    compile group: 'com.fasterxml.jackson.core', name: 'jackson-core', version: '2.13.3'
    compile group: 'com.fasterxml.jackson.core', name: 'jackson-databind', version: '2.13.3'
    compile group: 'org.apache.httpcomponents', name: 'fluent-hc', version: '4.5.13'
    compile 'com.google.api-client:google-api-client:1.33.0'
    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitVersion}")

    implementation('javax.json.bind:javax.json.bind-api:1.0')
    implementation('org.glassfish:jakarta.json:1.1.5')
    implementation('org.eclipse:yasson:1.0.3')
    implementation('mysql:mysql-connector-java:8.0.23')
    implementation("org.apache.httpcomponents:fluent-hc:4.5.13")
    implementation("com.fasterxml.jackson.core:jackson-core:2.13.3")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.3")
    implementation("com.google.api-client:google-api-client-jackson2:1.20.0")


}

test {
    useJUnitPlatform()
}