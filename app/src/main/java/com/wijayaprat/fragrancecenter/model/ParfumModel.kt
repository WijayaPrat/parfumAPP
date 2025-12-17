package com.wijayaprat.fragrancecenter.model

import java.io.Serializable

data class ParfumModel(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val price: Int = 0,
    var imageUrl: String = "",
    val brand: String = "",
    var stock: Int = 0,
    var quantity: Int = 1
) : Serializable
