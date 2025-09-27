package com.gideon.little_lemon.data

import com.gideon.little_lemon.MenuItemRoom
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MenuResponse(
    @SerialName("menu")
    val menu: List<MenuItemNetwork>
)

@Serializable
data class MenuItemNetwork(
    @SerialName("id")
    val id: Int,
    @SerialName("image")
    val image: String,
    @SerialName("price")
    val price: String,
    @SerialName("description")
    val description: String,
    @SerialName("title")
    val title: String,
    @SerialName("category")
    val category: String
){
    fun toMenuItemRoom() = MenuItemRoom(
        id,
        image,
        price,
        description,
        title,
        category,
    )
}

