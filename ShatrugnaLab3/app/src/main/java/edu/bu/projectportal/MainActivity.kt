package edu.bu.projectportal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import android.util.Log
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        val mHandlerThread = HandlerThread("background handler thread")
        mHandlerThread!!.start()
        val threadHandler = Handler(mHandlerThread!!.looper)

        threadHandler.post{
            longOperation()
            Toast.makeText(this,"long operation is completed", Toast.LENGTH_LONG )
        }
//        Toast.makeText(this,"long operation is completed6577", Toast.LENGTH_LONG )
    }
    fun longOperation(){
        var i =0
        for(k in 1..10000000){
            Log.d("sad", i.toString())
        }
        Log.d("sad", "hfkjhakbjn")
    }

}