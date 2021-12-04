package patches.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.ui.*

/*
This patch script was generated by TeamCity on settings change in UI.
To apply the patch, create a buildType with id = 'DomumOrnamentum_UpgradeBetaRelease_UpgradeBetaRelease'
in the project with id = 'DomumOrnamentum_UpgradeBetaRelease', and delete the patch script.
*/
create(RelativeId("DomumOrnamentum_UpgradeBetaRelease"), BuildType({
    templates(RelativeId("Upgrade"))
    id("DomumOrnamentum_UpgradeBetaRelease_UpgradeBetaRelease")
    name = "Upgrade Beta -> Release"
    description = "Upgrades the current Beta to Release."

    params {
        text("Source.Branch", "testing", label = "Source branch type", description = "The source branch type for the upgrade. EG: version or testing", allowEmpty = false)
        text("Default.Branch", "release/%Current Minecraft Version%", label = "Default branch", description = "The default branch of this build.", allowEmpty = true)
        param("VCS.Branches", "+:refs/heads/release/(*)")
        text("Target.Branch", "release", label = "Target branch type", description = "The target branch type for the upgrade. EG: testing or release.", allowEmpty = false)
        text("env.Version", "%env.Version.Major%.%env.Version.Minor%.%build.counter%-RELEASE", label = "Version", description = "The version of the project.", display = ParameterDisplay.HIDDEN, allowEmpty = true)
    }
}))

