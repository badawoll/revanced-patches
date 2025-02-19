package app.revanced.patches.netguard.broadcasts.removerestriction.resource.patch

import app.revanced.patcher.annotation.Description
import app.revanced.patcher.annotation.Name
import app.revanced.patcher.data.ResourceContext
import app.revanced.patcher.patch.ResourcePatch
import app.revanced.patcher.patch.annotations.Patch
import app.revanced.patches.netguard.broadcasts.removerestriction.resource.annotations.RemoveBroadcastsRestrictionCompatibility
import org.w3c.dom.Element

@Patch(false)
@Name("Remove broadcasts restriction")
@Description("Enables starting/stopping NetGuard via broadcasts.")
@RemoveBroadcastsRestrictionCompatibility
class RemoveBroadcastsRestrictionPatch : ResourcePatch {
    override fun execute(context: ResourceContext) {
        context.xmlEditor["AndroidManifest.xml"].use { dom ->
            val applicationNode = dom
                .file
                .getElementsByTagName("application")
                .item(0) as Element

            applicationNode.getElementsByTagName("receiver").also {  list ->
                for (i in 0 until list.length) {
                    val element = list.item(i) as? Element ?: continue
                    if (element.getAttribute("android:name") == "eu.faircode.netguard.WidgetAdmin") {
                        element.removeAttribute("android:permission")
                        break
                    }
                }
            }
        }
    }
}
