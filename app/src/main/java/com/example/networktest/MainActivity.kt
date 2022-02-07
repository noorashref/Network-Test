package com.example.networktest

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.example.networktest.model.presentation.BookResponse
import com.example.networktest.model.remote.Api
import com.example.networktest.model.remote.executeBookSearch
import com.example.networktest.model.remote.isDeviceConnected
import com.example.networktest.view.BookListFragment
import com.example.networktest.view.SearchBookFragment
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (isDeviceConnected())

            supportFragmentManager.beginTransaction().replace(R.id.container_search,
                SearchBookFragment()
            ).commit()
            //executeNetworkCall()
                //executeRetrofit()
        else
            showError()
    }

     fun executeRetrofit(bookTitle:String,bookType:String,maxResult:Int) {
       Api.initRetrofit().getBookByTitle(bookTitle,bookType,maxResult)
           .enqueue(object : Callback<BookResponse> {
               override fun onResponse(call: Call<BookResponse>, response: Response<BookResponse>) {


                   if(response.isSuccessful)
//                       findViewById<TextView>(R.id.tv_display).text = response.body().toString() ?: ""

                        inflateDisplayFragment(response.body())
                   else
                       showError()
               }

               override fun onFailure(call: Call<BookResponse>, t: Throwable) {

               }
           } )
    }

    private fun inflateDisplayFragment(dataSet: BookResponse?) {

        dataSet?.let {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_display,BookListFragment.newInstance(it))
                .commit()
        }

    }

    private fun showError() {
//      Snackbar.make(findViewById(R.id.tv_display),
     Snackbar.make(findViewById(R.id.container_display),
      "No network, retry?",Snackbar.LENGTH_INDEFINITE
          ).setAction("Retry") {
          Log.d(TAG, "showError: Retry")
      }.show()
    }

//    private fun executeNetworkCall() {
//
//        BootNetwork(findViewById(R.id.tv_display)).execute()
//    }

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