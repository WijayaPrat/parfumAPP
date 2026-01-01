package com.wijayaprat.fragrancecenter.model

import java.io.Serializable

data class ProductModel(
    val id: String = "",
    val name: String = "",
    val price: Int = 0,
    val stockA: Int = 0,
    val stockB: Int = 0,
    val imageUrl: String = ""
) : Serializable
