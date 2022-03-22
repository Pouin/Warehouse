package ru.energomera.mywarehouse.common

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import ru.energomera.mywarehouse.R

interface InitViews {
    /**
     * Функция внутри которой будет производиться
     * инициализация всех View внутри фрагментов
     */
    fun initializeViews()

    /**
     * Функция внутри которой будет выполняться
     * подписка на обновление объектов LiveData
     */
    fun observeViewModel()
}

/**
 * Интерфейс для изменения Toolbar внутри MainActivity
 */
interface NavigationHost {
    fun registerToolbarWithNavigation(toolbar: Toolbar)
}

/**
 * [MainNavigationFragment] регистрирует панель инструментов из фрагмента с активностью
 */
abstract class MainNavigationFragment: Fragment(), InitViews {

    abstract class MainNavigationFragment: Fragment(), InitViews  {
        private var navigationHost: NavigationHost? = null
        private var itemView: View? = null

        override fun onAttach(context: Context) {
            super.onAttach(context)
            if (context is NavigationHost) {
                navigationHost = context
            }
        }

        override fun onDetach() {
            super.onDetach()
            navigationHost = null
            itemView = null
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            itemView = view
        }

        /**
         * Получаем Toolbar из View и передаем его в Activity
         */
        override fun onResume() {
            super.onResume()

            val host = navigationHost ?: return
            val mainToolbar: Toolbar = itemView?.findViewById(R.id.toolbar) ?: return

            host.registerToolbarWithNavigation(mainToolbar)
        }
    }
}