package com.example.pro_7

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pro_7.databinding.FragmentFirstBinding
import com.google.android.material.snackbar.Snackbar

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var appPreferences: AppPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        appPreferences = AppPreferences(requireContext())

        // Load existing SharedPreferences into UI
        loadPreferencesToUi()

        // Slider Listeners
        binding.sliderFontSize.addOnChangeListener { _, value, _ ->
            binding.tvFontSizeLabel.text = "Font Size: ${value.toInt()} sp"
        }

        binding.sliderBorderRadius.addOnChangeListener { _, value, _ ->
            binding.tvRadiusLabel.text = "Card Border Radius: ${value.toInt()} px"
        }

        // Preset Theme Listeners
        binding.btnPresetDark.setOnClickListener {
            appPreferences.applyPresetTheme("Dark Cyber")
            loadPreferencesToUi()
            Toast.makeText(context, "Applied 'Dark Cyber' Preset", Toast.LENGTH_SHORT).show()
        }

        binding.btnPresetSunset.setOnClickListener {
            appPreferences.applyPresetTheme("Neon Sunset")
            loadPreferencesToUi()
            Toast.makeText(context, "Applied 'Neon Sunset' Preset", Toast.LENGTH_SHORT).show()
        }

        binding.btnPresetMint.setOnClickListener {
            appPreferences.applyPresetTheme("Mint Fresh")
            loadPreferencesToUi()
            Toast.makeText(context, "Applied 'Mint Fresh' Preset", Toast.LENGTH_SHORT).show()
        }

        binding.btnPresetLight.setOnClickListener {
            appPreferences.applyPresetTheme("Minimal Light")
            loadPreferencesToUi()
            Toast.makeText(context, "Applied 'Minimal Light' Preset", Toast.LENGTH_SHORT).show()
        }

        // Save Button
        binding.btnSave.setOnClickListener {
            saveUiToPreferences()
            Snackbar.make(binding.root, "Preferences saved successfully! 🎉", Snackbar.LENGTH_LONG)
                .setAction("View Preview") {
                    findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                }
                .show()
        }

        // Clear Preferences Button
        binding.btnClear.setOnClickListener {
            appPreferences.clearAll()
            loadPreferencesToUi()
            Toast.makeText(context, "All SharedPreferences cleared!", Toast.LENGTH_SHORT).show()
        }

        // Preview Navigation Button
        binding.btnPreview.setOnClickListener {
            saveUiToPreferences()
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    private fun loadPreferencesToUi() {
        binding.etName.setText(appPreferences.userName)
        binding.etEmail.setText(appPreferences.userEmail)
        binding.etAge.setText(appPreferences.userAge.toString())
        binding.switchNotifications.isChecked = appPreferences.notificationsEnabled

        val fontSize = appPreferences.fontSizeSp.coerceIn(12, 28)
        binding.sliderFontSize.value = fontSize.toFloat()
        binding.tvFontSizeLabel.text = "Font Size: $fontSize sp"

        binding.etBgColor.setText(appPreferences.cssBgColor)
        binding.etTextColor.setText(appPreferences.cssTextColor)
        binding.etCardBg.setText(appPreferences.cssCardBg)

        val radius = appPreferences.cssBorderRadius.coerceIn(0, 30)
        binding.sliderBorderRadius.value = radius.toFloat()
        binding.tvRadiusLabel.text = "Card Border Radius: $radius px"

        binding.etCustomCss.setText(appPreferences.customCssCode)
    }

    private fun saveUiToPreferences() {
        val name = binding.etName.text.toString().ifEmpty { "Alex Developer" }
        val email = binding.etEmail.text.toString().ifEmpty { "alex@example.com" }
        val age = binding.etAge.text.toString().toIntOrNull() ?: 25
        val notifications = binding.switchNotifications.isChecked
        val fontSize = binding.sliderFontSize.value.toInt()
        val theme = appPreferences.themeName
        val bgColor = binding.etBgColor.text.toString().ifEmpty { "#0f172a" }
        val textColor = binding.etTextColor.text.toString().ifEmpty { "#38bdf8" }
        val cardBg = binding.etCardBg.text.toString().ifEmpty { "#1e293b" }
        val radius = binding.sliderBorderRadius.value.toInt()
        val customCss = binding.etCustomCss.text.toString()

        appPreferences.saveAll(
            name = name,
            email = email,
            age = age,
            notifications = notifications,
            fontSize = fontSize,
            theme = theme,
            bgColor = bgColor,
            textColor = textColor,
            cardBg = cardBg,
            borderRadius = radius,
            customCss = customCss
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}