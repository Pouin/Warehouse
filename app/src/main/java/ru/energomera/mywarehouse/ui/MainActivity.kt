package ru.energomera.mywarehouse.ui

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import app.akexorcist.bluetotohspp.library.BluetoothSPP
import app.akexorcist.bluetotohspp.library.BluetoothState

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import ru.energomera.mywarehouse.R

class MainActivity : AppCompatActivity()  {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private val permissionCode = 2

    companion object {
        private val TOOLBAR_DESTINATION = setOf(
            R.id.nav_aboutApp,
            R.id.nav_inventory,
            R.id.nav_listTasks,
            R.id.nav_postCompItems,
            R.id.nav_putFormedItems,
            R.id.nav_putUnformedItems,
            R.id.nav_settings,
            R.id.nav_transfer
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermission()

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(TOOLBAR_DESTINATION, drawerLayout)

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    /**
     * Регистрируем Toolbar внутри [MainActivity]
     *
     * @param toolbar виджет Toolbar приходящий из фрагментов
     */
//    override fun registerToolbarWithNavigation(toolbar: Toolbar) {
//        navController?.apply {
//            toolbar.setupWithNavController(this, appBarConfiguration)
//        }
//    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        val result = super.onCreateOptionsMenu(menu)
//        // Using findViewById because NavigationView exists in different layout files
//        // between w600dp and w1240dp
//        val navView: NavigationView? = findViewById(R.id.nav_view)
//        if (navView == null) {
//            // The navigation drawer already has the items including the items in the overflow menu
//            // We only inflate the overflow menu if the navigation drawer isn't visible
//            menuInflater.inflate(R.menu.overflow, menu)
//        }
//        return result
//    }
//
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_settings -> {

                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
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

//        var textStatusView: TextView = findViewById(R.id.bthStatusConnect)
//        val connectBth: Boolean = getSharedPreferences("bluetoothSwitchConnect", Context.MODE_PRIVATE).getBoolean("bluetoothSwitchConnect", false)
//
//        if (connectBth) {
//                bt!!.setDeviceTarget(BluetoothState.DEVICE_OTHER)
//                val intent = Intent(applicationContext, DeviceList::class.java)
//                startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE)
//        } else {
//            if (bt!!.serviceState == BluetoothState.STATE_CONNECTED) bt!!.disconnect()
//        }
//
//        //change the status label and menu options when connection status changed.
//        bt!!.setBluetoothConnectionListener(object : BluetoothSPP.BluetoothConnectionListener {
//            override fun onDeviceDisconnected() {
//                Toast.makeText(applicationContext, getString(R.string.bthStatus_notCon), Toast.LENGTH_LONG).show()
//                textStatusView.text = getString(R.string.bthStatus_notCon)
//            }
//
//            override fun onDeviceConnectionFailed() {
//                Toast.makeText(applicationContext, getString(R.string.bthStatus_failed), Toast.LENGTH_LONG).show()
//                textStatusView.text = getString(R.string.bthStatus_failed)
//            }
//
//            override fun onDeviceConnected(name: String, address: String) {
//                Toast.makeText(applicationContext, getString(R.string.btnStatus_Connect) + name, Toast.LENGTH_LONG).show()
//                textStatusView.text = getString(R.string.btnStatus_Connect)
//            }
//        })

    }
}