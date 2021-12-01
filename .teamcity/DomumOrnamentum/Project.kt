package DomumOrnamentum

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project
import jetbrains.buildServer.configs.kotlin.v2019_2.projectFeatures.githubIssues

object Project : Project({
    id("DomumOrnamentum")
    name = "Domum Ornamentum"
    description = "Structure based world modification using creative wants."

    params {
        password("env.crowdinKey", "credentialsJSON:444bd785-791b-42ae-9fae-10ee93a2fbd3")
        select("Current Minecraft Version", "latest", label = "Current Minecraft Version",
                options = listOf("1.12", "1.13", "1.14", "1.15", "1.16", "1.17"))
        text("Repository", "ldtteam/Domum-Ornamentum", label = "Repository", description = "The repository for minecolonies.", readOnly = true, allowEmpty = true)
        param("env.Version.Minor", "0")
        param("env.Version.Patch", "0")
        param("Upsource.Project.Id", "structurize")
        param("env.Version.Suffix", "")
        param("env.Version.Major", "1")
        text("env.Version", "%env.Version.Major%.%env.Version.Minor%.%env.Version.Patch%%env.Version.Suffix%", label = "Version", description = "The version of the project.", display = ParameterDisplay.HIDDEN, allowEmpty = true)
    }

    features {
        githubIssues {
            id = "PROJECT_EXT_35"
            displayName = "ldtteam/minecolonies"
            repositoryURL = "https://github.com/ldtteam/minecolonies"
            authType = accessToken {
                accessToken = "credentialsJSON:47381468-aceb-4992-93c9-1ccd4d7aa67f"
            }
        }
    }
    subProjectsOrder = arrayListOf(RelativeId("DomumOrnamentum_Release"), RelativeId("DomumOrnamentum_UpgradeBetaRelease"), RelativeId("DomumOrnamentum_Beta"), RelativeId("DomumOrnamentum_UpgradeAlphaBeta"), RelativeId("DomumOrnamentum_Alpha"), RelativeId("DomumOrnamentum_OfficialPublications"), RelativeId("DomumOrnamentum_Branches"), RelativeId("DomumOrnamentum_PullRequests2"))

    subProject(DomumOrnamentum_UpgradeBetaRelease.Project)
    subProject(DomumOrnamentum_Alpha.Project)
    subProject(DomumOrnamentum_PullRequests2.Project)
    subProject(DomumOrnamentum_Beta.Project)
    subProject(DomumOrnamentum_Branches.Project)
    subProject(DomumOrnamentum_OfficialPublications.Project)
    subProject(DomumOrnamentum_Release.Project)
    subProject(DomumOrnamentum_UpgradeAlphaBeta.Project)
})
