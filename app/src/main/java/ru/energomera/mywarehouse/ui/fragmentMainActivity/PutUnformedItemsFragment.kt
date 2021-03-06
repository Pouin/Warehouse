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




}