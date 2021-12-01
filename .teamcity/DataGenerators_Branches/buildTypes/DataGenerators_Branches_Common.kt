package DataGenerators_Branches.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*

object DataGenerators_Branches_Common : BuildType({
    templates(_Self.buildTypes.CommonBuildCounter)
    name = "Common Build Counter"
    description = "Tracks the amount of builds run for branches"
})
