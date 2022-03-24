package ru.energomera.mywarehouse.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.energomera.mywarehouse.R
import ru.energomera.mywarehouse.common.MainNavigationFragment

class PostCompItemsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_post_comp_items, container, false)
    }
}