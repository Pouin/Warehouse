package ru.energomera.mywarehouse.ui

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
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
import app.akexorcist.bluetotohspp.library.DeviceList
import com.google.android.material.navigation.NavigationView
import ru.energomera.mywarehouse.R
import ru.energomera.mywarehouse.util.SharedPreference

class MainActivity : AppCompatActivity()  {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var bt: BluetoothSPP
    var textStatus: TextView? = null

    private val permissionCode = 2
    lateinit var sharedPreference: SharedPreference
    var menu: Menu? = null

    companion object {
        private val TOOLBAR_DESTINATION = setOf(
            R.id.nav_aboutApp,
            R.id.nav_inventory,
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

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(TOOLBAR_DESTINATION, drawerLayout)

        //bluetooth
        bt = BluetoothSPP(this)
        textStatus = findViewById(R.id.textStatus)
        sharedPreference = SharedPreference(this)

        if (!bt.isBluetoothAvailable) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_SHORT).show()
            finish()
        }
        else
            changeStatusBluetooth(bt)

        bt.setOnDataReceivedListener { data, message ->
//            textRead.append(
//                message.toString()
//            )
        }


        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onStart() {
        super.onStart()

        checkPermission()

        if (!bt.isBluetoothEnabled) {
            val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT)
        } else {
            if (!bt.isServiceAvailable) {
                bt.setupService()
                bt.startService(BluetoothState.DEVICE_OTHER)
                changeStatusBluetooth(bt)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        this.menu = menu
        menuInflater.inflate(R.menu.menu_connect, menu)
        return true
    }

    public override fun onDestroy() {
        super.onDestroy()
        bt.disconnect()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.menu_device_connect) {
            //connecting other kind of devices
            bt!!.setDeviceTarget(BluetoothState.DEVICE_OTHER)
            val intent = Intent(this, DeviceList::class.java)
            startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE)
        } else if (id == R.id.menu_disconnect) {
            //disconnect from a device
            if (bt!!.serviceState == BluetoothState.STATE_CONNECTED) bt!!.disconnect()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun changeStatusBluetooth(bt: BluetoothSPP){

        bt.setBluetoothConnectionListener(object : BluetoothSPP.BluetoothConnectionListener {
            override fun onDeviceDisconnected() {
                textStatus!!.text = getString(R.string.bthStatus_notCon)

                menu!!.clear()
                menuInflater.inflate(R.menu.menu_disconnect, menu)
            }

            override fun onDeviceConnectionFailed() {
                textStatus!!.text = getString(R.string.bthStatus_failed)
            }

            override fun onDeviceConnected(name: String, address: String) {
                textStatus?.text  = getString(R.string.btnStatus_Connect)

                sharedPreference.save("BT_NAME", name)
                sharedPreference.save("BT_MAC", address)

                menu!!.clear()
                menuInflater.inflate(R.menu.menu_connect, menu)
            }
        })
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == RESULT_OK) bt.connect(data)
        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                bt.setupService()
            } else {
                Toast.makeText(
                    this, "Bluetooth was not enabled.", Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }


}