package com.example.pro_7

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pro_7.databinding.FragmentSecondBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SecondFragment : Fragment(), SharedPreferences.OnSharedPreferenceChangeListener {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var appPreferences: AppPreferences
    private val logStringBuilder = StringBuilder()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        appPreferences = AppPreferences(requireContext())
        appPreferences.registerChangeListener(this)

        // Setup WebView
        binding.webView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
        }

        renderWebView()
        updateInspectorTable()

        logEvent("Listener registered & Initialized.")

        // Interactive Buttons
        binding.btnIncrementAge.setOnClickListener {
            val currentAge = appPreferences.userAge
            appPreferences.userAge = currentAge + 1
        }

        binding.btnToggleNotif.setOnClickListener {
            val current = appPreferences.notificationsEnabled
            appPreferences.notificationsEnabled = !current
        }

        // Back Button
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun renderWebView() {
        val htmlContent = appPreferences.generateHtmlDocument()
        binding.webView.loadDataWithBaseURL(
            null,
            htmlContent,
            "text/html",
            "UTF-8",
            null
        )
    }

    private fun updateInspectorTable() {
        val entries = appPreferences.getAllEntriesFormatted()
        val sb = StringBuilder()
        sb.append("=== SHARED PREFERENCES DUMP ===\n")
        sb.append("File: ${AppPreferences.PREF_NAME}.xml\n")
        sb.append("Total Keys: ${entries.size}\n")
        sb.append("--------------------------------\n\n")

        if (entries.isEmpty()) {
            sb.append("(Empty SharedPreferences)")
        } else {
            entries.forEachIndexed { index, (key, value) ->
                sb.append("${index + 1}. [$key]\n   ➔ $value\n\n")
            }
        }

        binding.tvInspector.text = sb.toString()
    }

    private fun logEvent(message: String) {
        val time = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault()).format(Date())
        logStringBuilder.insert(0, "[$time] $message\n")
        binding.tvEventLog.text = logStringBuilder.toString()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        logEvent("EVENT: Key '$key' was modified in SharedPreferences!")
        renderWebView()
        updateInspectorTable()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        appPreferences.unregisterChangeListener(this)
        _binding = null
    }
}