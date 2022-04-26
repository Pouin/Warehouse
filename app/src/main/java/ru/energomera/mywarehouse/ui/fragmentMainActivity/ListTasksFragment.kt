package ru.energomera.mywarehouse.ui.fragmentMainActivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.energomera.mywarehouse.R



class ListTasksFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.del_fragment_list_tasks, container, false)
    }

}