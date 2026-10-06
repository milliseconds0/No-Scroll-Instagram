package app.noscroll.instagram

import android.accessibilityservice.AccessibilityService
import android.graphics.Rect
import android.os.SystemClock
import android.view.accessibility.AccessibilityEvent

class InstagramGuardService : AccessibilityService() {
    private var lastRedirectAt = 0L

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        if (event.packageName?.toString() != InstagramNavigator.INSTAGRAM_PACKAGE) return

        if (event.eventType == AccessibilityEvent.TYPE_VIEW_CLICKED && isBlockedNavigation(event)) {
            redirectToMessages()
        }
    }

    override fun onInterrupt() = Unit

    private fun redirectToMessages() {
        val now = SystemClock.elapsedRealtime()
        // A single tap can emit several accessibility events. Launch messages once.
        if (now - lastRedirectAt < REDIRECT_COOLDOWN_MS) return
        lastRedirectAt = now
        InstagramNavigator.openMessages(this)
    }

    private fun isBlockedNavigation(event: AccessibilityEvent): Boolean {
        val label = sequenceOf(event.text?.joinToString(" "), event.contentDescription?.toString())
            .filterNotNull()
            .joinToString(" ")
            .lowercase()

        if (label.isBlank() || ("home" !in label && "reels" !in label && "reel" !in label)) return false

        // Only react to the bottom-navigation control. This avoids treating ordinary
        // message text that happens to contain one of these words as navigation.
        val source = event.source ?: return false
        val bounds = Rect()
        source.getBoundsInScreen(bounds)
        val screenHeight = resources.displayMetrics.heightPixels
        return bounds.centerY() > screenHeight * BOTTOM_NAV_TOP_FRACTION
    }

    companion object {
        private const val REDIRECT_COOLDOWN_MS = 900L
        private const val BOTTOM_NAV_TOP_FRACTION = 0.62f
    }
}
