package com.example.sistemakds

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PedidoAdapter(private val pedido: Pedido) :
    RecyclerView.Adapter<PedidoAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pedido, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = pedido.itens[position]
        holder.itemNome.text = item.nome
        holder.itemQuantidade.text = "Quantidade: ${item.quantidade}"
        holder.itemCheckbox.isChecked = item.produzido

        holder.itemCheckbox.setOnCheckedChangeListener { _, isChecked ->
            item.produzido = isChecked
        }
    }

    override fun getItemCount(): Int = pedido.itens.size

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemNome: TextView = itemView.findViewById(R.id.itemNome)
        val itemQuantidade: TextView = itemView.findViewById(R.id.itemQuantidade)
        val itemCheckbox: CheckBox = itemView.findViewById(R.id.itemCheckbox)
    }
}
