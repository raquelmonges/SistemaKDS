package com.example.sistemakds

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.OutputStream
import java.net.Socket

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var printButton: Button

    private val pedido = Pedido(
        id = "1234",
        itens = listOf(
            ItemPedido("Hamburguer", 2),
            ItemPedido("Batata Frita", 1),
            ItemPedido("Coca Cola", 2)
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        printButton = findViewById(R.id.printButton)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = PedidoAdapter(pedido)

        printButton.setOnClickListener {
            val ipImpressora = "192.168.1.100"
            val portaImpressora = 9100
            val dadosImpressao = gerarTextoDoPedido(pedido)

            enviarParaImpressora(ipImpressora, portaImpressora, dadosImpressao)
            Toast.makeText(this, "Pedido enviado para impressão", Toast.LENGTH_SHORT).show()
        }
    }

    private fun gerarTextoDoPedido(pedido: Pedido): ByteArray {
        val sb = StringBuilder()
        sb.append("\u001B@")
        sb.append("Pedido #${pedido.id}\n")
        for (item in pedido.itens) {
            sb.append("${item.nome} - Qtde: ${item.quantidade}\n")
            if (item.produzido) {
                sb.append("(Produzido)\n")
            }
        }
        sb.append("\n\n")
        sb.append("\u001D@\u001Bd2")

        return sb.toString().toByteArray()
    }

    private fun enviarParaImpressora(ip: String, porta: Int, dados: ByteArray) {
        Thread {
            try {
                val socket = Socket(ip, porta)
                val outputStream: OutputStream = socket.getOutputStream()
                outputStream.write(dados)
                outputStream.flush()
                socket.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }
}
