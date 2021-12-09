package patches.projects

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project
import jetbrains.buildServer.configs.kotlin.v2019_2.ui.*

/*
This patch script was generated by TeamCity on settings change in UI.
To apply the patch, change the root project
accordingly, and delete the patch script.
*/
changeProject(DslContext.projectId) {
    expectSubProjectsOrder(RelativeId("Minecolonies"), RelativeId("Aequivaleo"), RelativeId("Structurize"), RelativeId("DomumOrnamentum"), RelativeId("PistonUnlimited"), RelativeId("BlockOut"), RelativeId("Animatrix"), RelativeId("PerViamInvenire"), RelativeId("BlockUI"), RelativeId("DataGenerators"), RelativeId("GraphicsExpanded"), RelativeId("MinecoloniesWiki"), RelativeId("Armory"), RelativeId("JVoxelizer"), RelativeId("SmithsCore"))
    subProjectsOrder = arrayListOf(RelativeId("Minecolonies"), RelativeId("Aequivaleo"), RelativeId("Structurize"), RelativeId("DomumOrnamentum"), RelativeId("PistonUnlimited"), RelativeId("BlockOut"), RelativeId("Animatrix"), RelativeId("PerViamInvenire"), RelativeId("BlockUI"), RelativeId("DataGenerators"), RelativeId("GraphicsExpanded"), RelativeId("MinecoloniesWiki"))
}
