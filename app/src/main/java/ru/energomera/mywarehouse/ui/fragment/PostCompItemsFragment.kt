package ru.energomera.mywarehouse.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import app.akexorcist.bluetotohspp.library.BluetoothSPP
import ru.energomera.mywarehouse.R

class PostCompItemsFragment : Fragment() {
    private var bt: BluetoothSPP? = null

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_post_comp_items, container, false)
    }

    override fun onStart() {
        super.onStart()

        bt = BluetoothSPP(context)

        val textRead: TextView = requireView().findViewById(R.id.editTextToScan)
        bt!!.setOnDataReceivedListener { data, message ->
            textRead.text = message.toString()
        }

    }
    override fun onResume() {
        super.onResume()

        bt = BluetoothSPP(context)
    }
}