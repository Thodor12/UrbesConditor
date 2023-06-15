package _Self.buildTypes

import _Self.vcsRoots.General
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.commitStatusPublisher
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.GradleBuildStep
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.gradle
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object BuildWithTesting : Template({
    name = "Build with Testing"
    description = "Builds the current project without testing."

    artifactRules = """+:build\libs\*.jar => build\libs"""
    buildNumberPattern = "%env.Version%"

    params {
        text("Repository", "", label = "Repostior", description = "The repository that this build targets.", allowEmpty = true)
        text("VCS.Branches", "+:refs/heads/(*)", label = "Branches", description = "Defines the branches that this build could target", allowEmpty = true)
        text("env.Version.Minor", "1", label = "Minor Version", description = "The minor version of the project",
              regex = "[0-9]+", validationMessage = "Please specify a number!")
        param("Project.Type", "")
        param("env.Version.Patch", "%build.number%%")
        param("Upsource.Project.Id", "")
        text("env.Version.Major", "1", label = "Major Version", description = "The major version of the project.",
              regex = "[0-9]+", validationMessage = "Please specificy a number!")
        text("Default.Branch", "CI/Default", label = "Default branch", description = "The default branch of this build.", allowEmpty = true)
        text("env.Version", "%env.Version.Major%.%env.Version.Minor%.%env.Version.Patch%", label = "Version", description = "The version of the project.", display = ParameterDisplay.HIDDEN, allowEmpty = true)
    }

    vcs {
        root(_Self.vcsRoots.General)

        branchFilter = """
            +:*
            -:<default>
        """.trimIndent()
        showDependenciesChanges = true
    }

    steps {
        gradle {
            name = "Compile"
            id = "RUNNER_9"
            tasks = "build"
            buildFile = "build.gradle"
            useGradleWrapper = false
            enableStacktrace = true
            dockerImage = "gradle:%env.GRADLE_VERSION%-%env.JDK_VERSION%"
            dockerImagePlatform = GradleBuildStep.ImagePlatform.Linux
            dockerRunParameters = """
                -v /opt/buildagent/gradle:/home/gradle/.gradle
                -u 0
            """.trimIndent()
            param("org.jfrog.artifactory.selectedDeployableServer.deployReleaseText", "%Project.Type%")
            param("org.jfrog.artifactory.selectedDeployableServer.buildRetentionNumberOfBuilds", "300")
            param("org.jfrog.artifactory.selectedDeployableServer.defaultModuleVersionConfiguration", "GLOBAL")
            param("org.jfrog.artifactory.selectedDeployableServer.buildRetention", "true")
            param("org.jfrog.artifactory.selectedDeployableServer.deployReleaseFlag", "true")
            param("org.jfrog.artifactory.selectedDeployableServer.buildRetentionAsync", "true")
            param("org.jfrog.artifactory.selectedDeployableServer.targetRepo", "libraries")
            param("org.jfrog.artifactory.selectedDeployableServer.publishBuildInfo", "true")
            param("org.jfrog.artifactory.selectedDeployableServer.urlId", "2")
            param("org.jfrog.artifactory.selectedDeployableServer.envVarsExcludePatterns", "*password*,*secret*")
            param("org.jfrog.artifactory.selectedDeployableServer.resolvingRepo", "modding")
            param("org.jfrog.artifactory.selectedDeployableServer.buildRetentionDeleteArtifacts", "true")
            param("org.jfrog.artifactory.selectedDeployableServer.buildRetentionMaxDays", "150")
        }
    }

    triggers {
        vcs {
            id = "vcsTrigger"
        }
    }

    features {
        commitStatusPublisher {
            id = "BUILD_EXT_15"
            vcsRootExtId = "${General.id}"
            publisher = upsource {
                serverUrl = "https://upsource.minecolonies.com"
                projectId = "%Upsource.Project.Id%"
                userName = "teamcity"
                password = "credentialsJSON:f19631a7-1bc1-4a66-88a0-dc2b9cd36734"
            }
        }
        commitStatusPublisher {
            id = "BUILD_EXT_16"
            vcsRootExtId = "${General.id}"
            publisher = github {
                githubUrl = "https://api.github.com"
                authType = personalToken {
                    token = "credentialsJSON:23cd3823-3e9f-4c80-a1c4-6ad9c876842a"
                }
            }
            param("github_oauth_user", "OrionDevelopment")
        }
    }
})
