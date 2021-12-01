package DomumOrnamentum_PullRequests2.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*

object DomumOrnamentum_PullRequests2_BuildAndTest : BuildType({
    templates(_Self.buildTypes.BuildWithTesting)
    name = "Build and Test"
    description = "Builds and Tests the pull request."

    params {
        param("Project.Type", "mods")
        param("env.Version.Patch", "${DomumOrnamentum_PullRequests2_CommonBuildCounter.depParamRefs.buildNumber}")
        param("env.Version.Suffix", "-PR")
    }

    dependencies {
        snapshot(DomumOrnamentum_PullRequests2_CommonBuildCounter) {
            reuseBuilds = ReuseBuilds.NO
            onDependencyFailure = FailureAction.FAIL_TO_START
        }
    }
    
    disableSettings("BUILD_EXT_15")
})
