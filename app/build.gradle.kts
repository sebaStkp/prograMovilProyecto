import com.google.protobuf.gradle.id
import java.io.File
import java.net.URL
import java.util.Properties

import java.io.FileInputStream

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(FileInputStream(localPropertiesFile))
}

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)

    alias(libs.plugins.google.protobuf)
    kotlin("plugin.serialization") version "2.1.0"
    id("com.google.gms.google-services")
}

android {
    namespace = "com.ucb.perritos"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.ucb.perritos"
        minSdk = 23
        targetSdk = 36
        versionCode = 4
        versionName = "2.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        val supabaseKey = localProperties["SUPABASE_KEY"] ?: ""
        buildConfigField("String", "SUPABASE_KEY", "\"${localProperties["SUPABASE_KEY"]}\"")

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE-notice.md"
        }
    }

}

fun getLocalProperty(key: String): String {
    val localProperties = Properties()
    val localPropertiesFile = project.rootProject.file("local.properties")

    if (localPropertiesFile.exists()) {
        localPropertiesFile.inputStream().use { input ->
            localProperties.load(input)
        }
    } else {
        error("Error: El archivo 'local.properties' no se encuentra en la raíz del proyecto. Asegúrese de crearlo.")
    }
    return localProperties.getProperty(key) ?: error("Error: La clave '$key' no se encuentra en 'local.properties'. Por favor, añádala con su valor.")
}
val localeMapping = mapOf(
    "en-US" to "values",
    "es-ES" to "values-es",
    "es-BO" to "values-es-rBO"
)
tasks.register("downloadLocoStrings") {
    group = "localization"
    description = "Downloads strings.xml files from Localise.biz via API"

    val locoApiKey = getLocalProperty("LOCO_API_KEY")

    val resDir = file("src/main/res")

    doLast {
        localeMapping.forEach { (apiCode, resFolder) ->
            downloadFile(
                apiKey = locoApiKey,
                apiCode = apiCode,
                resFolder = resFolder,
                resDir = resDir
            )
        }
    }
}
fun downloadFile(apiKey: String, apiCode: String, resFolder: String, resDir: File) {
    println("-> Descargando [$apiCode] en $resFolder")

    val outputFile = file("$resDir/$resFolder/strings.xml")

    outputFile.parentFile.mkdirs()

    val exportUrl = "https://localise.biz/api/export/locale/$apiCode.xml?key=$apiKey&format=android"

    try {
        URL(exportUrl).openStream().use { input ->
            outputFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        println("✅ Descarga de $apiCode exitosa en $resFolder/strings.xml.")
    } catch (e: Exception) {
        println("❌ ERROR al descargar $apiCode. Verifique que la clave '$apiCode' tenga contenido en Localise.biz y que la API Key sea correcta: ${e.message}")
    }
}

tasks.named("preBuild").configure {
    dependsOn("downloadLocoStrings")
}



dependencies {
    // --- ANDROID & COMPOSE ---
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.foundation)
    // Iconos extendidos
    implementation("androidx.compose.material:material-icons-extended")

    // --- FIREBASE (Solo lo necesario) ---
    // implementation(libs.firebase.database) <--- BORRADO: Usas Supabase, no Firebase DB
    implementation(platform("com.google.firebase:firebase-bom:33.7.0")) // Recomiendo usar BOM para Firebase también
    implementation("com.google.firebase:firebase-messaging-ktx") // Para las notificaciones
    // implementation("com.google.firebase:firebase-analytics") // Descomenta si usas Analytics

    // --- SUPABASE & KTOR (Corregido) ---
    // Usamos el BOM para que gestione las versiones automáticamente
    implementation(platform("io.github.jan-tennert.supabase:bom:3.1.1")) // O la 3.2.6 si libs lo tiene
    implementation("io.github.jan-tennert.supabase:postgrest-kt")
    implementation("io.github.jan-tennert.supabase:auth-kt")
    implementation("io.github.jan-tennert.supabase:realtime-kt")
    implementation("io.github.jan-tennert.supabase:storage-kt")

    // Ktor (Motor http para Supabase) - UNA SOLA VERSIÓN
    implementation("io.ktor:ktor-client-android:3.0.3")
    // Nota: Si usas Supabase 3.0.0+ necesitas Ktor 3.0.0+

    // --- INYECCIÓN DE DEPENDENCIAS (KOIN) ---
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.navigation)
    implementation(libs.koin.androidx.compose)

    // --- MAPAS & UBICACIÓN ---
    implementation(libs.google.maps.compose) // Usa el del catálogo si existe
    implementation(libs.google.play.services.maps)
    implementation(libs.play.services.location) // Usa solo el del catálogo
    // Si necesitas OSM también (si no, bórralo para ahorrar espacio):
    implementation("org.osmdroid:osmdroid-android:6.1.18")

    // --- PERMISOS (Para notificaciones Android 13+) ---
    implementation("com.google.accompanist:accompanist-permissions:0.36.0") // Versión más estable

    // --- NAVEGACIÓN & IMÁGENES ---
    implementation(libs.androidx.navigation.compose)
    implementation(libs.coil.compose)
    implementation(libs.coil.network)

    // --- NETWORK & DATA ---
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.datastore)

    // --- PROTOBUF & GRPC ---
    implementation(libs.grpc.protobuf.lite)
    implementation(libs.protobuf.kotlin.lite)
    implementation(libs.grpc.kotlin.stub)
    runtimeOnly(libs.grpc.okhttp)

    // --- ROOM DATABASE ---
    implementation(libs.bundles.local)
    annotationProcessor(libs.room.compiler)
    ksp(libs.room.compiler)

    // --- TESTING ---
    testImplementation(libs.junit)
    testImplementation(libs.mockk)


    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.room.testing)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation("io.mockk:mockk-android:1.13.8")
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation("androidx.room:room-testing:2.6.1")
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:${libs.versions.protobuf.get()}"
    }
    plugins {
        id("java") {
            artifact = libs.protoc.gen.grpc.java.get().toString()
        }
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:${libs.versions.grpc.get()}"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:${libs.versions.grpcKotlin.get()}:jdk8@jar"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                create("java") { option("lite") }
                create("grpc") { option("lite") }
                create("grpckt") { option("lite") }
            }
            it.builtins {
                create("kotlin") { option("lite") }
            }
        }
    }
}