package com.example.sistemakds

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var printButton: Button

    private lateinit var tcpServer: TcpServer

    private val pedidoList = mutableListOf<Pedido>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        printButton = findViewById(R.id.printButton)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = PedidoAdapter(pedidoList)

        printButton.setOnClickListener {
            Toast.makeText(this, "Pedido enviado para impressão", Toast.LENGTH_SHORT).show()
        }

        // Start TCP server to listen for incoming orders
        tcpServer = TcpServer(port = 9200) { data ->
            runOnUiThread {
                // Process received data and update RecyclerView
                processReceivedData(data)
            }
        }
        tcpServer.start()
    }

    private fun processReceivedData(data: String) {
        // Parse the data and update the RecyclerView
        val pedido = parsePedido(data)
        pedidoList.add(pedido)
        recyclerView.adapter?.notifyDataSetChanged()
        Toast.makeText(this, "Novo pedido recebido: $data", Toast.LENGTH_SHORT).show()
    }

    private fun parsePedido(data: String): Pedido {
        // Implement the parsing logic based on your data format
        // For this example, we'll assume the data is in a simple format
        val items = data.split("\n").map {
            val parts = it.split(" - ")
            ItemPedido(parts[0], parts[1].toInt())
        }
        return Pedido(id = "1234", itens = items)
    }

    override fun onDestroy() {
        super.onDestroy()
        tcpServer.stop()
    }
}
