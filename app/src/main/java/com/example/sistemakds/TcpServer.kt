package com.example.sistemakds

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.ServerSocket
import java.net.Socket

class TcpServer(private val port: Int, private val onDataReceived: (String) -> Unit) {

    private var serverSocket: ServerSocket? = null

    fun start() {
        Thread {
            try {
                serverSocket = ServerSocket(port)
                println("Servidor TCP iniciado na porta $port") // Adicione um log
                while (true) {
                    val socket = serverSocket?.accept()
                    val reader = BufferedReader(InputStreamReader(socket?.getInputStream()))
                    val data = reader.readLine()
                    if (data != null) {
                        onDataReceived(data)
                    }
                    socket?.close()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }

    fun stop() {
        serverSocket?.close()
    }
}
