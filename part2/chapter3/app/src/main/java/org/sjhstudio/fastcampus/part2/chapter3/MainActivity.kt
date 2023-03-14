package org.sjhstudio.fastcampus.part2.chapter3

import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.google.gson.Gson
import okhttp3.*
import org.sjhstudio.fastcampus.part2.chapter3.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val okHttpClient: OkHttpClient by lazy { OkHttpClient() }
    private val inputMethodManager: InputMethodManager by lazy {
        getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    }
    private var serverHost: String = ""

    companion object {
        private const val PORT = 8080
        private const val LOG = "client"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        with(binding) {
            etServerHost.addTextChangedListener { serverHost = it.toString() }
            btnConfirm.setOnClickListener {
                inputMethodManager.hideSoftInputFromWindow(etServerHost.windowToken, 0)
                requestOkHttp()
            }
        }
    }

    private fun requestOkHttp() {
        val request = Request.Builder()
            .url("http://$serverHost:$PORT")
            .build()
        val callback = object : Callback {
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    response.body?.string()?.let { bodyString ->
                        val message = Gson().fromJson(bodyString, Message::class.java)

                        Log.d(LOG, message.toString())

                        runOnUiThread {
                            binding.tvInformation.text = message.message
                            binding.tvInformation.isVisible = true
                            binding.etServerHost.isVisible = false
                            binding.btnConfirm.isVisible = false
                        }
                    }
                } else {
                    Log.e(LOG, "server error")

                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "수신에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.e(LOG, e.toString())

                runOnUiThread {
                    Toast.makeText(this@MainActivity, "수신에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        okHttpClient.newCall(request).enqueue(callback)
    }

    private fun request() {
        Thread {
            try {
                val socket = Socket("192.168.0.5", PORT)
                val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
                val printer = PrintWriter(socket.getOutputStream())

                printer.println("GET / HTTP/1.1")
                printer.println("Host: 127.0.0.1$PORT")
                printer.println("User-Agent: android\r\n")
                printer.flush()

                var line: String? = reader.readLine()
                var input: String? = ""
                while (!line.isNullOrEmpty()) {
                    input += (line + "\n")
                    line = reader.readLine()
                }

                reader.close()

                printer.close()

                socket.close()
            } catch (e: Exception) {
                Log.e(LOG, e.toString())
            }
        }.start()
    }
}