package com.lamti.alarmy.night_mode

import android.os.Build
import androidx.appcompat.app.AppCompatDelegate

/**
 * Contains settings.
 */
object Settings {

    /**
     * The night mode.
     */
    const val NIGHT_MODE = "night_mode"

    /**
     * Night mode which uses the recommended default option.
     */
    @JvmField
    val MODE_NIGHT_DEFAULT = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    } else {
        AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
    }

}