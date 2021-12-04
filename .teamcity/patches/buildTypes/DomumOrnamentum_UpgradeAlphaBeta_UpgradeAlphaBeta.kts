package patches.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.ui.*

/*
This patch script was generated by TeamCity on settings change in UI.
To apply the patch, create a buildType with id = 'DomumOrnamentum_UpgradeAlphaBeta_UpgradeAlphaBeta'
in the project with id = 'DomumOrnamentum_UpgradeAlphaBeta', and delete the patch script.
*/
create(RelativeId("DomumOrnamentum_UpgradeAlphaBeta"), BuildType({
    templates(RelativeId("Upgrade"))
    id("DomumOrnamentum_UpgradeAlphaBeta_UpgradeAlphaBeta")
    name = "Upgrade - Alpha -> Beta"
    description = "Upgrades the current Alpha to Beta."

    params {
        text("Source.Branch", "version", label = "Source branch type", description = "The source branch type for the upgrade. EG: version or testing", allowEmpty = false)
        text("Default.Branch", "testing/%Current Minecraft Version%", label = "Default branch", description = "The default branch of this build.", allowEmpty = true)
        param("VCS.Branches", "+:refs/heads/testing/(*)")
        text("Target.Branch", "testing", label = "Target branch type", description = "The target branch type for the upgrade. EG: testing or release.", allowEmpty = false)
        text("env.Version", "%env.Version.Major%.%env.Version.Minor%.%build.counter%-BETA", label = "Version", description = "The version of the project.", display = ParameterDisplay.HIDDEN, allowEmpty = true)
    }
    
    disableSettings("BUILD_EXT_9")
}))

