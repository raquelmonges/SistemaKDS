package com.example.sistemakds

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PedidoAdapter(private val pedidos: List<Pedido>) :
    RecyclerView.Adapter<PedidoAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pedido, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val pedido = pedidos[position]
        holder.itemNome.text = pedido.itens.joinToString { it.nome }
        holder.itemQuantidade.text = pedido.itens.joinToString { "Qtde: ${it.quantidade}" }
    }

    override fun getItemCount(): Int = pedidos.size

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemNome: TextView = itemView.findViewById(R.id.itemNome)
        val itemQuantidade: TextView = itemView.findViewById(R.id.itemQuantidade)
    }
}
