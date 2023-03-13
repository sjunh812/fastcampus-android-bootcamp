package org.sjhstudio.fastcampus.part2.chapter3

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class MainActivity : AppCompatActivity() {

    companion object {
        private const val PORT = 8080
        private const val LOG = "client"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        request()
    }

    private fun request() {
        Thread {
            try {
                val socket = Socket("192.168.0.5", PORT)
                val printer = PrintWriter(socket.getOutputStream())
                val reader = BufferedReader(InputStreamReader(socket.getInputStream()))

                printer.println("GET / HTTP/1.1")
                printer.println("Host: 127.0.0.1$PORT")
                printer.println("User-Agent: android\r\n")
                printer.flush()

                var input: String? = "-1"
                while (input != null) {
                    input = reader.readLine()
                    Log.e(LOG, input)
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