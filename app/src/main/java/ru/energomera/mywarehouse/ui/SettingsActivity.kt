package ru.energomera.mywarehouse.ui

import ru.energomera.mywarehouse.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.energomera.mywarehouse.ui.fragment.SettingsFragment

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings_container, SettingsFragment())
            .commit()
    }
}