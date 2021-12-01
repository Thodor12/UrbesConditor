package PistonUnlimited_UpgradeBetaRelease

import PistonUnlimited_UpgradeBetaRelease.buildTypes.*
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object Project : Project({
    id("PistonUnlimited_UpgradeBetaRelease")
    name = "Upgrade Beta -> Release"
    description = "Upgrades the current Beta to Release"

    buildType(PistonUnlimited_UpgradeBetaRelease_UpgradeBetaRelease)
})
