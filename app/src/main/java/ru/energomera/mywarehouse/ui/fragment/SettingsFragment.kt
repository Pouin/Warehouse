package ru.energomera.mywarehouse.ui.fragment

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import ru.energomera.mywarehouse.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}