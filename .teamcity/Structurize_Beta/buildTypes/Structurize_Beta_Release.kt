package Structurize_Beta.buildTypes

import Structurize_OfficialPublications.buildTypes.Structurize_OfficialPublications_CommonB
import jetbrains.buildServer.configs.kotlin.v2019_2.*

object Structurize_Beta_Release : BuildType({
    templates(_Self.buildTypes.BuildWithRelease)
    name = "Release"
    description = "Releases the mod as Alpha to CurseForge"

    params {
        param("Project.Type", "mods")
        param("env.Version.Patch", "${Structurize_OfficialPublications_CommonB.depParamRefs.buildNumber}")
    }

    dependencies {
        snapshot(Structurize_OfficialPublications.buildTypes.Structurize_OfficialPublications_CommonB) {
            reuseBuilds = ReuseBuilds.NO
            onDependencyFailure = FailureAction.FAIL_TO_START
        }
    }
})
