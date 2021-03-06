package com.example.networktest

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.example.networktest.model.presentation.BookResponse
import com.example.networktest.model.remote.executeBookSearch
import com.example.networktest.model.remote.isDeviceConnected
import com.google.android.material.snackbar.Snackbar

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (isDeviceConnected())
            executeNetworkCall()
        else
            showError()
    }

    private fun showError() {
      Snackbar.make(findViewById(R.id.tv_display),
      "No network, retry?",Snackbar.LENGTH_INDEFINITE
          ).setAction("Retry") {
          Log.d(TAG, "showError: Retry")
      }.show()
    }

    private fun executeNetworkCall() {

        BootNetwork(findViewById(R.id.tv_display)).execute()
    }
    class BootNetwork (private val display : TextView): AsyncTask<String,Void,BookResponse>() {
        override fun doInBackground(vararg p0: String?): BookResponse? {
//            executeBookSearch("coding for dummies ")
            return executeBookSearch("Pride, Prejudice, and Other Flavors")
        }

        override fun onPostExecute(result: BookResponse?) {
            super.onPostExecute(result)
            display.text = result.toString() ?: ""
        }
    }

}





//    object BootNetwork: AsyncTask<Void,Void,Void?>() {
//        override fun doInBackground(vararg p0: String?): BookResponse? {
////            executeBookSearch("coding for dummies ")
//            return executeBookSearch("Pride, Prejudice, and Other Flavors")
//
//        }
//    }