package MinecoloniesWiki.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.VcsTrigger
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object MinecoloniesWiki_Deploy : BuildType({
    name = "Deploy"
    description = "Deploys the wiki into the swarm"
    paused = true

    enablePersonalBuilds = false
    type = BuildTypeSettings.Type.DEPLOYMENT
    maxRunningBuilds = 1

    params {
        text("env.DOCKER_CERT_PATH", "/auth/docker", label = "Docker certificates path", description = "The path to the docker certificates on the agent that allow for authentication with the target docker host.", allowEmpty = true)
        checkbox("env.DOCKER_TLS_VERIFY", "1", label = "Docker TLS Verify", description = "Indicator used to verifiy the remote servers TLS data.",
                  checked = "1", unchecked = "0")
        password("Kubeconfig", "credentialsJSON:9ea24281-c289-4b04-9b6d-dd282a69af1a", display = ParameterDisplay.HIDDEN)
        text("env.DOCKER_HOST", "tcp://192.168.10.52:2376", label = "Docker host", description = "The docker host to deploy the target on.", allowEmpty = true)
    }

    vcs {
        root(MinecoloniesWiki.vcsRoots.MinecoloniesWiki_HttpsGithubComLdtteamMinecoloniesWikiRefsHeadsMain)
    }

    steps {
        script {
            name = "Install Kubectl & Helm"
            scriptContent = """
                if [ ! -f /etc/apt/sources.list.d/kubernetes.list ]
                then
                    echo "Installing KubeCtl and HELM..."
                    
                    # prereq packages
                    sudo apt-get update
                	sudo apt-get install -y apt-transport-https ca-certificates curl gnupg
                
                	# add repo and signing key
                    sudo mkdir -p /etc/apt/keyrings
                    curl -fsSL https://pkgs.k8s.io/core:/stable:/v1.30/deb/Release.key | sudo gpg --dearmor -o /etc/apt/keyrings/kubernetes-apt-keyring.gpg
                    sudo chmod 644 /etc/apt/keyrings/kubernetes-apt-keyring.gpg
                	echo 'deb [signed-by=/etc/apt/keyrings/kubernetes-apt-keyring.gpg] https://pkgs.k8s.io/core:/stable:/v1.30/deb/ /' | sudo tee /etc/apt/sources.list.d/kubernetes.list
                    sudo chmod 644 /etc/apt/sources.list.d/kubernetes.list
                    sudo apt-get update
                
                	# install kubectl
                	sudo apt-get install -y kubectl
                    
                    # install helm
                    curl https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3 |  bash
                fi
            """.trimIndent()
        }
        script {
            name = "Setup Kubeconfig"
            scriptContent = """
                mkdir -p ~/.kube
                echo "%Kubeconfig%" > ~/.kube/config
                chmod 644 ~/.kube/config
                
                kubectl get namespaces
            """.trimIndent()
        }
        script {
            name = "Deploy wiki chart"
            scriptContent = "helm upgrade --atomic -i -n minecolonies-wiki --set image.tag=${MinecoloniesWiki_BuildAndPublish.depParamRefs.buildNumber} minecolonies-wiki ./chart/wiki"
        }
    }

    triggers {
        vcs {
            quietPeriodMode = VcsTrigger.QuietPeriodMode.USE_DEFAULT
            branchFilter = ""
            watchChangesInDependencies = true
        }
    }

    dependencies {
        snapshot(MinecoloniesWiki_BuildAndPublish) {
            reuseBuilds = ReuseBuilds.NO
            onDependencyFailure = FailureAction.FAIL_TO_START
        }
    }
})
