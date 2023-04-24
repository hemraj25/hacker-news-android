import org.gradle.api.JavaVersion

object Config {
    val versionMajor = 2
    val versionMinor = 0
    val versionPatch = 0
    // version name of "1.2.3" and version code of 10203
    val versionCode = versionMajor.times( 10000).plus(versionMinor.times( 100)).plus(versionPatch)
    val versionName = "$versionMajor.$versionMinor.$versionPatch"
    const val minSdk = 24
    const val compileSdk = 33
    const val targetSdk = 33
    val sourceCompatibility = JavaVersion.VERSION_1_8
    val targetCompatibility = JavaVersion.VERSION_1_8

}