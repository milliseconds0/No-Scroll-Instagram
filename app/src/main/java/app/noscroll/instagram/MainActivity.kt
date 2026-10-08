package app.noscroll.instagram

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.accessibilityservice.AccessibilityServiceInfo
import android.os.Bundle
import android.provider.Settings
import android.net.Uri
import android.view.accessibility.AccessibilityManager
import androidx.appcompat.app.AppCompatActivity
import app.noscroll.instagram.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.accessibilityButton.setOnClickListener { openAccessibilitySettings() }
        binding.appSettingsButton.setOnClickListener { openAppSettings() }
        binding.guardSwitch.setOnClickListener { openAccessibilitySettings() }
    }

    override fun onResume() {
        super.onResume()
        val enabled = isGuardEnabled(this)
        binding.statusText.text = if (enabled) {
            "Guard is on"
        } else {
            "Guard is off"
        }
        binding.guardSwitch.isChecked = enabled
    }

    private fun openAccessibilitySettings() {
        startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
    }

    private fun openAppSettings() {
        startActivity(
            Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:$packageName")
            )
        )
    }

    private fun isGuardEnabled(context: Context): Boolean {
        val manager = context.getSystemService(AccessibilityManager::class.java) ?: return false
        val expected = ComponentName(context, InstagramGuardService::class.java)
        return manager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK)
            .any { ComponentName(it.resolveInfo.serviceInfo.packageName, it.resolveInfo.serviceInfo.name) == expected }
    }
}
