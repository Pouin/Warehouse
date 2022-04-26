package ru.energomera.mywarehouse.ui.fragmentMainActivity

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import app.akexorcist.bluetotohspp.library.BluetoothSPP
import app.akexorcist.bluetotohspp.library.BluetoothState
import ru.energomera.mywarehouse.R
import ru.energomera.mywarehouse.util.SharedPreference

class PutUnformedItemsFragment : Fragment() {

    private lateinit var bt: BluetoothSPP
    private lateinit var mSettings: SharedPreference
//    private var mBluetoothAdapter: BluetoothAdapter? = null

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_put_unformed_items, container, false)

        val textRead: TextView = view.findViewById(R.id.editTextToScanBth)
        val textStatus: TextView = view.findViewById(R.id.textStatusss)
        mSettings = SharedPreference(requireActivity().applicationContext)

        textRead.text = mSettings.getValueString("BT_NAME") + "\n" + mSettings.getValueString("BT_MAC")
        bt = BluetoothSPP(this.requireActivity().applicationContext)
        //https://github.com/prasad-psp/Android-Bluetooth-Library
//        bt = BluetoothAdapterProvider.getAdapter()
//        bt.connectedDeviceAddress
//        if (bt.serviceState < 0) {
//            val btMac = mSettings.getValueString("BT_MAC")
//            bt.connect(btMac)
////            bt.autoConnect()
//        }
//        textRead.text = bt.serviceState.toString()
        bt.setOnDataReceivedListener { _data, message ->
            textRead.text = message.toString()
        }

        bt.setBluetoothConnectionListener(object : BluetoothSPP.BluetoothConnectionListener {
            override fun onDeviceDisconnected() {
                textStatus.text = getString(R.string.bthStatus_notCon)
            }

            override fun onDeviceConnectionFailed() {
                textStatus.text = getString(R.string.bthStatus_failed)
            }

            override fun onDeviceConnected(name: String, address: String) {
                textStatus.text = getString(R.string.btnStatus_Connect)
            }
        })

        return view
    }

    override fun onStart() {
        super.onStart()

        if (!bt.isBluetoothEnabled) {
            val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT)
        } else {
            if (!bt.isServiceAvailable) {
                bt.setupService()
                bt.startService(BluetoothState.DEVICE_OTHER)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        bt.stopService()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK) bt.connect(data)
        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                bt.setupService()
            } else {
                Toast.makeText(
                    activity, "Bluetooth was not enabled.", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


}