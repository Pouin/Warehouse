package ru.energomera.mywarehouse.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ru.energomera.mywarehouse.R
import ru.energomera.mywarehouse.common.MainNavigationFragment

class inventoryFragment: MainNavigationFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_inventory, container, false)
    }

    override fun initializeViews() {

    }

    /**
     * Функция внутри которой выполняется подписка на обновление объектов LiveData
     */
    override fun observeViewModel() {
        TODO("Not yet implemented")
    }
}