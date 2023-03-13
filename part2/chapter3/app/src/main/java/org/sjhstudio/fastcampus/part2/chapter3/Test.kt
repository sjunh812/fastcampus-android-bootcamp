package org.sjhstudio.fastcampus.part2.chapter3

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket

const val PORT = 8080

fun main() {
    Thread {
        val server = ServerSocket(PORT)

        while (true) {
            val socket = server.accept() // connected(open socket)
            // socket.getInputStream() : 클라이언트로부터 받은 데이터 흐름 == 클라이언트의 OutputStream
            // socket.getOutputStream() : 클라리언트에게 보낼 데이터 흐름 == 클라이언트의 InputStream
            val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
            val printer = PrintWriter(socket.getOutputStream())

            var line: String? = reader.readLine()
            var input: String? = ""
            while (!line.isNullOrEmpty()) {
                input += (line + "\n")
                line = reader.readLine()
            }

            println("[SERVER] read data >>\n$input")

            printer.println("HTTP/1.1 200 OK")
            printer.println("Content-Type: text/html")
            printer.println("\r\n")
            printer.println("<h1>Hello, World!</h1>")
            printer.println("\r\n")

            printer.flush()
            printer.close()

            reader.close()

            socket.close()
        }
    }.start()
}