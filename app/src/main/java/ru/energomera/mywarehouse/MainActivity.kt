package ru.energomera.mywarehouse

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import app.akexorcist.bluetotohspp.library.BluetoothSPP
import app.akexorcist.bluetotohspp.library.BluetoothState
import app.akexorcist.bluetotohspp.library.DeviceList
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.energomera.mywarehouse.common.NavigationHost

class MainActivity : AppCompatActivity(), NavigationHost {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private var navController: NavController? = null
    private val permissionCode = 2
    private var bt: BluetoothSPP? = null

    companion object {
        private val TOOLBAR_DESTINATION = setOf(
            R.id.nav_aboutApp, R.id.nav_inventory,
            R.id.nav_listTasks, R.id.nav_postCompItems,
            R.id.nav_putFormedItems, R.id.nav_putUnformedItems,
            R.id.nav_settings, R.id.nav_transfer
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bt = BluetoothSPP(this)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as? NavHostFragment
            ?: return
        navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(TOOLBAR_DESTINATION)

        val mainBottomNavigationView = findViewById<BottomNavigationView>(R.id.mainBottomNavigationView)

        navController?.apply {
            mainBottomNavigationView.setupWithNavController(this)
        }
    }

    override fun registerToolbarWithNavigation(toolbar: Toolbar) {
        navController?.apply {
            toolbar.setupWithNavController(this, appBarConfiguration)
        }
    }

    override fun onStart() {
        super.onStart()

        checkPermission()
        //enable bluetooth and bt service.
        if (!bt!!.isBluetoothEnabled) {
            val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT)
        } else {
            if (!bt!!.isServiceAvailable) {
                bt!!.setupService()
                bt!!.startService(BluetoothState.DEVICE_ANDROID)
            }
        }
    }

    override fun onResume() {
        super.onResume()
//        checkBluetoothConnect()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val result = super.onCreateOptionsMenu(menu)
        // Using findViewById because NavigationView exists in different layout files
        // between w600dp and w1240dp
        val navView: NavigationView? = findViewById(R.id.nav_view)
        if (navView == null) {
            // The navigation drawer already has the items including the items in the overflow menu
            // We only inflate the overflow menu if the navigation drawer isn't visible
            menuInflater.inflate(R.menu.overflow, menu)
        }
        return result
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_settings -> {
                val navController = findNavController(R.id.nav_host_fragment_content_main)
                navController.navigate(R.id.nav_settings)
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun checkPermission(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) !=
            PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.BLUETOOTH), permissionCode)
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) !=
            PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.INTERNET), permissionCode)
        }
    }

    private fun checkBluetoothConnect() {

        var textStatusView: TextView = findViewById(R.id.bthStatusConnect)
        val connectBth: Boolean = getSharedPreferences("bluetoothSwitchConnect", Context.MODE_PRIVATE).getBoolean("bluetoothSwitchConnect", false)

        if (connectBth) {
                bt!!.setDeviceTarget(BluetoothState.DEVICE_OTHER)
                val intent = Intent(applicationContext, DeviceList::class.java)
                startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE)
        } else {
            if (bt!!.serviceState == BluetoothState.STATE_CONNECTED) bt!!.disconnect()
        }

        //change the status label and menu options when connection status changed.
        bt!!.setBluetoothConnectionListener(object : BluetoothSPP.BluetoothConnectionListener {
            override fun onDeviceDisconnected() {
                Toast.makeText(applicationContext, getString(R.string.bthStatus_notCon), Toast.LENGTH_LONG).show()
                textStatusView.text = getString(R.string.bthStatus_notCon)
            }

            override fun onDeviceConnectionFailed() {
                Toast.makeText(applicationContext, getString(R.string.bthStatus_failed), Toast.LENGTH_LONG).show()
                textStatusView.text = getString(R.string.bthStatus_failed)
            }

            override fun onDeviceConnected(name: String, address: String) {
                Toast.makeText(applicationContext, getString(R.string.btnStatus_Connect) + name, Toast.LENGTH_LONG).show()
                textStatusView.text = getString(R.string.btnStatus_Connect)
            }
        })

    }
}