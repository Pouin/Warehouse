package ru.energomera.mywarehouse.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.energomera.mywarehouse.R
import ru.energomera.mywarehouse.common.MainNavigationFragment


class putFormedItemsFragment : MainNavigationFragment()  {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_put_formed_items, container, false)
    }

    override fun initializeViews() {
        TODO("Not yet implemented")
    }

    override fun observeViewModel() {
        TODO("Not yet implemented")
    }
}