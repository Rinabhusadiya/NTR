package com.example.pro_7

import android.content.Context
import android.content.SharedPreferences
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AppPreferences(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        const val PREF_NAME = "advanced_app_preferences"

        const val KEY_USER_NAME = "user_name"
        const val KEY_USER_EMAIL = "user_email"
        const val KEY_USER_AGE = "user_age"
        const val KEY_NOTIFICATIONS = "notifications_enabled"
        const val KEY_FONT_SIZE = "font_size_sp"
        const val KEY_THEME_NAME = "theme_name"
        const val KEY_CSS_BG_COLOR = "css_bg_color"
        const val KEY_CSS_TEXT_COLOR = "css_text_color"
        const val KEY_CSS_CARD_BG = "css_card_bg"
        const val KEY_CSS_BORDER_RADIUS = "css_border_radius"
        const val KEY_CUSTOM_CSS_CODE = "custom_css_code"
        const val KEY_LAST_SAVED_TIMESTAMP = "last_saved_timestamp"
    }

    var userName: String
        get() = prefs.getString(KEY_USER_NAME, "Alex Developer") ?: "Alex Developer"
        set(value) = prefs.edit().putString(KEY_USER_NAME, value).apply()

    var userEmail: String
        get() = prefs.getString(KEY_USER_EMAIL, "alex@example.com") ?: "alex@example.com"
        set(value) = prefs.edit().putString(KEY_USER_EMAIL, value).apply()

    var userAge: Int
        get() = prefs.getInt(KEY_USER_AGE, 25)
        set(value) = prefs.edit().putInt(KEY_USER_AGE, value).apply()

    var notificationsEnabled: Boolean
        get() = prefs.getBoolean(KEY_NOTIFICATIONS, true)
        set(value) = prefs.edit().putBoolean(KEY_NOTIFICATIONS, value).apply()

    var fontSizeSp: Int
        get() = prefs.getInt(KEY_FONT_SIZE, 16)
        set(value) = prefs.edit().putInt(KEY_FONT_SIZE, value).apply()

    var themeName: String
        get() = prefs.getString(KEY_THEME_NAME, "Dark Cyber") ?: "Dark Cyber"
        set(value) = prefs.edit().putString(KEY_THEME_NAME, value).apply()

    var cssBgColor: String
        get() = prefs.getString(KEY_CSS_BG_COLOR, "#0f172a") ?: "#0f172a"
        set(value) = prefs.edit().putString(KEY_CSS_BG_COLOR, value).apply()

    var cssTextColor: String
        get() = prefs.getString(KEY_CSS_TEXT_COLOR, "#38bdf8") ?: "#38bdf8"
        set(value) = prefs.edit().putString(KEY_CSS_TEXT_COLOR, value).apply()

    var cssCardBg: String
        get() = prefs.getString(KEY_CSS_CARD_BG, "#1e293b") ?: "#1e293b"
        set(value) = prefs.edit().putString(KEY_CSS_CARD_BG, value).apply()

    var cssBorderRadius: Int
        get() = prefs.getInt(KEY_CSS_BORDER_RADIUS, 12)
        set(value) = prefs.edit().putInt(KEY_CSS_BORDER_RADIUS, value).apply()

    var customCssCode: String
        get() = prefs.getString(
            KEY_CUSTOM_CSS_CODE,
            "/* Custom CSS Rules */\n.highlight { color: #f43f5e; font-weight: bold; }\n.btn { background: #38bdf8; color: #0f172a; padding: 10px 18px; border-radius: 8px; border: none; font-weight: bold; cursor: pointer; }"
        ) ?: ""
        set(value) = prefs.edit().putString(KEY_CUSTOM_CSS_CODE, value).apply()

    var lastSavedTimestamp: Long
        get() = prefs.getLong(KEY_LAST_SAVED_TIMESTAMP, System.currentTimeMillis())
        set(value) = prefs.edit().putLong(KEY_LAST_SAVED_TIMESTAMP, value).apply()

    fun saveAll(
        name: String,
        email: String,
        age: Int,
        notifications: Boolean,
        fontSize: Int,
        theme: String,
        bgColor: String,
        textColor: String,
        cardBg: String,
        borderRadius: Int,
        customCss: String
    ) {
        prefs.edit()
            .putString(KEY_USER_NAME, name)
            .putString(KEY_USER_EMAIL, email)
            .putInt(KEY_USER_AGE, age)
            .putBoolean(KEY_NOTIFICATIONS, notifications)
            .putInt(KEY_FONT_SIZE, fontSize)
            .putString(KEY_THEME_NAME, theme)
            .putString(KEY_CSS_BG_COLOR, bgColor)
            .putString(KEY_CSS_TEXT_COLOR, textColor)
            .putString(KEY_CSS_CARD_BG, cardBg)
            .putInt(KEY_CSS_BORDER_RADIUS, borderRadius)
            .putString(KEY_CUSTOM_CSS_CODE, customCss)
            .putLong(KEY_LAST_SAVED_TIMESTAMP, System.currentTimeMillis())
            .apply()
    }

    fun applyPresetTheme(preset: String) {
        when (preset) {
            "Dark Cyber" -> {
                themeName = "Dark Cyber"
                cssBgColor = "#0f172a"
                cssTextColor = "#38bdf8"
                cssCardBg = "#1e293b"
                cssBorderRadius = 14
                customCssCode = ".badge { background: #38bdf8; color: #0f172a; padding: 4px 10px; border-radius: 6px; font-weight: bold; }"
            }
            "Neon Sunset" -> {
                themeName = "Neon Sunset"
                cssBgColor = "#2d1b36"
                cssTextColor = "#ff758f"
                cssCardBg = "#3c2449"
                cssBorderRadius = 18
                customCssCode = ".badge { background: #ff758f; color: #2d1b36; padding: 4px 10px; border-radius: 12px; font-weight: bold; }"
            }
            "Mint Fresh" -> {
                themeName = "Mint Fresh"
                cssBgColor = "#f0fdf4"
                cssTextColor = "#15803d"
                cssCardBg = "#dcfce7"
                cssBorderRadius = 8
                customCssCode = ".badge { background: #16a34a; color: #ffffff; padding: 4px 10px; border-radius: 4px; font-weight: bold; }"
            }
            "Minimal Light" -> {
                themeName = "Minimal Light"
                cssBgColor = "#f8fafc"
                cssTextColor = "#0f172a"
                cssCardBg = "#ffffff"
                cssBorderRadius = 10
                customCssCode = ".badge { background: #64748b; color: #ffffff; padding: 4px 10px; border-radius: 6px; font-weight: bold; }"
            }
        }
    }

    fun clearAll() {
        prefs.edit().clear().apply()
    }

    fun removeKey(key: String) {
        prefs.edit().remove(key).apply()
    }

    fun registerChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        prefs.registerOnSharedPreferenceChangeListener(listener)
    }

    fun unregisterChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        prefs.unregisterOnSharedPreferenceChangeListener(listener)
    }

    fun getAllEntriesFormatted(): List<Pair<String, String>> {
        val allMap = prefs.all
        val list = mutableListOf<Pair<String, String>>()
        for ((key, value) in allMap) {
            val formattedVal = when (value) {
                is Long -> {
                    if (key == KEY_LAST_SAVED_TIMESTAMP) {
                        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                        "${value} (${sdf.format(Date(value))})"
                    } else {
                        value.toString()
                    }
                }
                else -> value.toString()
            }
            list.add(Pair(key, formattedVal))
        }
        return list.sortedBy { it.first }
    }

    fun generateHtmlDocument(): String {
        val bg = cssBgColor
        val text = cssTextColor
        val card = cssCardBg
        val radius = cssBorderRadius
        val fontSp = fontSizeSp
        val name = userName
        val email = userEmail
        val age = userAge
        val notif = if (notificationsEnabled) "Enabled" else "Disabled"
        val customCss = customCssCode

        return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <style>
                    body {
                        background-color: $bg;
                        color: $text;
                        font-family: 'Segoe UI', Roboto, Helvetica, Arial, sans-serif;
                        font-size: ${fontSp}px;
                        margin: 0;
                        padding: 16px;
                        box-sizing: border-box;
                    }
                    .card {
                        background-color: $card;
                        border-radius: ${radius}px;
                        padding: 20px;
                        margin-bottom: 16px;
                        box-shadow: 0 4px 12px rgba(0,0,0,0.15);
                        border: 1px solid rgba(255, 255, 255, 0.1);
                    }
                    h2 {
                        margin-top: 0;
                        border-bottom: 2px solid $text;
                        padding-bottom: 8px;
                    }
                    .info-row {
                        margin: 8px 0;
                        display: flex;
                        justify-content: space-between;
                    }
                    .label {
                        font-weight: bold;
                        opacity: 0.8;
                    }
                    /* Dynamic Custom CSS from SharedPreferences */
                    $customCss
                </style>
            </head>
            <body>
                <div class="card">
                    <h2>SharedPreferences Live CSS Preview</h2>
                    <p>This HTML content is styled in real-time using CSS properties retrieved directly from Android <b>SharedPreferences</b>!</p>
                    <span class="badge">Theme: ${themeName}</span>
                </div>
                
                <div class="card">
                    <h3>User Profile Details</h3>
                    <div class="info-row"><span class="label">Name:</span> <span>$name</span></div>
                    <div class="info-row"><span class="label">Email:</span> <span>$email</span></div>
                    <div class="info-row"><span class="label">Age:</span> <span>$age years</span></div>
                    <div class="info-row"><span class="label">Notifications:</span> <span>$notif</span></div>
                    <div class="info-row"><span class="label">Font Size:</span> <span>${fontSp}sp</span></div>
                </div>

                <div class="card">
                    <h3>CSS Settings Saved</h3>
                    <div class="info-row"><span class="label">Background Color:</span> <span>$bg</span></div>
                    <div class="info-row"><span class="label">Text Color:</span> <span>$text</span></div>
                    <div class="info-row"><span class="label">Card Background:</span> <span>$card</span></div>
                    <div class="info-row"><span class="label">Border Radius:</span> <span>${radius}px</span></div>
                </div>
            </body>
            </html>
        """.trimIndent()
    }
}
