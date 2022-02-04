package com.example.networktest.model.remote

import android.content.Context
import android.net.ConnectivityManager
import androidx.fragment.app.FragmentActivity
import android.net.NetworkInfo
import android.net.Uri
import android.util.Log
import com.example.networktest.model.presentation.BookItem
import com.example.networktest.model.presentation.BookResponse
import com.example.networktest.model.presentation.VolumeItem
import org.json.JSONObject
import java.io.BufferedReader
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset
import kotlin.io.readText


private const val TAG = "Network"
/** Extension function adding is connected
 * functionality to FragmentActivity Class
 * Syntax for extension function
 * fun[CLASS].funname(args:Any) :Unit
 */

fun FragmentActivity.isDeviceConnected(): Boolean {



    val connectivityManager = getSystemService(
        Context.CONNECTIVITY_SERVICE
    ) as ConnectivityManager

    val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo

//    networkInfo?.isConnected.let {
//
//    }

    return networkInfo?.isConnected ?: false
}

fun executeBookSearch(bookTitle: String) : BookResponse{
    //Create URI
    val BASE_URL = "https://www.googleapis.com/"
    val ENDPOINT = "books/v1/volumes"
    val Q_ARG = "q"
    val MAX_RESULT_ARG = "maxResults"
    val PRINT_TYPE_ARG = "printType"
    val bookUri = Uri.parse(
        "$BASE_URL$ENDPOINT?"
    ).buildUpon()
        .appendQueryParameter(Q_ARG, bookTitle)
        .appendQueryParameter(MAX_RESULT_ARG, "5")
        .appendQueryParameter(PRINT_TYPE_ARG, "books")
        .build()

    val url = URL(bookUri.toString())

    val httpUrlConnection = url.openConnection() as HttpURLConnection

    httpUrlConnection.connectTimeout = 15000
    httpUrlConnection.readTimeout = 10000
    httpUrlConnection.requestMethod = "GET"
    httpUrlConnection.doInput = true

    httpUrlConnection.connect()
    val bookIS = httpUrlConnection.inputStream
    val bookResponseCode = httpUrlConnection.responseCode
    val str = parseByteOutputStream(bookIS)


//    var inp = ByteArrayOutputStream()
//    bookIS.use {
//        it.copyTo(inp)
//    }
//    val inpStr = inp.toString()




    Log.d(TAG, "executeBookSearch: $bookIS $bookResponseCode $str")
//    Log.d(TAG, "executeBookSearch: ${bookIS.convert()} $bookResponseCode")

    return convertToPresentationData(str)
}

private fun convertToPresentationData(deSerialized : String) :BookResponse{

    val json: JSONObject = JSONObject(deSerialized)
    val itemArray = json.getJSONArray("items")

    val listOfBooks = mutableListOf<BookItem>()
    for(index in 0 until itemArray.length()){
        val bookItemsJson = itemArray.getJSONObject(index)
        val volumeInfoJson = bookItemsJson.getJSONObject("volumeInfo")
        val title = volumeInfoJson.getString("title")
        val authors = volumeInfoJson.getJSONArray("authors")

        val authorList  = mutableListOf<String>()
        for (jIndex in 0 until authors.length()){
            authorList.add(authors.getString(jIndex))
        }
        val volumeItem = VolumeItem(title,authorList)
            //author as List<String>
            //emptyList())



        val bookItem = BookItem (volumeItem)
        listOfBooks.add(bookItem)
    }
    return BookResponse(listOfBooks)
}


private fun parseByteOutputStream(bookIS : InputStream):String{

    var inp = ByteArrayOutputStream()
    bookIS.use {
        it.copyTo(inp)
    }
    val inpStr = inp.toString()
    return inpStr

}

fun InputStream.convert(): String{
    return this.bufferedReader().use ( BufferedReader:: readText )
}
