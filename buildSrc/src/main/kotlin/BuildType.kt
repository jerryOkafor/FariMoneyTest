/**
 * Author: Jerry Okafor
 * Project: TestApp
 */

interface BuildType {

    companion object {
        const val DEBUG = "debug"
        const val RELEASE = "release"
    }

    val isMinifyEnabled: Boolean
    val isTestCoverageEnabled: Boolean
}

object BuildTypeDebug : BuildType {
    override val isMinifyEnabled = false
    override val isTestCoverageEnabled = true

}

object BuildTypeRelease : BuildType {
    override val isMinifyEnabled = true
    override val isTestCoverageEnabled = false
}