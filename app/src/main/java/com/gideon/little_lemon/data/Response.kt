package com.gideon.little_lemon.data

data class Response(
	val menu: List<MenuItem?>? = null
)

data class MenuItem(
	val image: String? = null,
	val price: String? = null,
	val description: String? = null,
	val id: Int? = null,
	val title: String? = null,
	val category: String? = null
)