package app.noscroll.instagram

import android.content.Context
import android.content.Intent
import android.net.Uri

/** Opens Instagram's Direct Messages only after a blocked navigation-tab tap. */
object InstagramNavigator {
    const val INSTAGRAM_PACKAGE = "com.instagram.android"

    fun openMessages(context: Context) {
        val directIntent = Intent(Intent.ACTION_VIEW, Uri.parse("instagram://direct-inbox"))
            .setPackage(INSTAGRAM_PACKAGE)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)

        try {
            context.startActivity(directIntent)
        } catch (_: Exception) {
            // Retain a public-link fallback in case Instagram changes its private URI.
            val fallback = Intent(Intent.ACTION_VIEW, Uri.parse("https://ig.me/m/"))
                .setPackage(INSTAGRAM_PACKAGE)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            try {
                context.startActivity(fallback)
            } catch (_: Exception) {
                // Instagram is unavailable or disabled.
            }
        }
    }
}
