package com.example.sistemakds

data class Pedido(val id: String, val itens: List<ItemPedido>)

data class ItemPedido(val nome: String, val quantidade: Int, var produzido: Boolean = false)



