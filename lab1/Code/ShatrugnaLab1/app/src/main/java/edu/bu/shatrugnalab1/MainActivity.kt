package edu.bu.shatrugnalab1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(CLASS, "onCreate LifeCycle")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(CLASS, "onRestart LifeCycle")
    }

    override fun onStart() {
        super.onStart()
        Log.d(CLASS, "onStart LifeCycle")
    }

    override fun onResume() {
        super.onResume()
        Log.d(CLASS, "onResume LifeCycle")
    }

    override fun onPause() {
        super.onPause()
        Log.d(CLASS,"onPause LifeCycle")
    }

    override fun onStop() {
        super.onStop()
        Log.d(CLASS, "onStop LifeCycle")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(CLASS, "onDestroy LifeCycle")
    }

    companion object{
        const val CLASS = "MainActivity"
    }
}