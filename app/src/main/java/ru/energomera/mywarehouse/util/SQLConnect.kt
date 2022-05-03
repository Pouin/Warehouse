package ru.energomera.mywarehouse.util

import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import java.sql.*

class SQLConnect {
    var con: Connection? = null

    fun sqlConnectClass(): Connection?  {
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        Class.forName("net.sourceforge.jtds.jdbc.Driver")

        try {
            con = DriverManager.getConnection(url, userpssw, userpssw)
        }
        catch (e: SQLException){
            Log.e("Error is ", e.message.toString())
        }

        return con
    }

    companion object{
        val ip = "10.6.0.70"
        val port = "1433"
        val database = "zip_work"
        val userpssw = "tsd"
        val url = "jdbc:jtds:sqlserver://$ip:$port/$database"
    }
}