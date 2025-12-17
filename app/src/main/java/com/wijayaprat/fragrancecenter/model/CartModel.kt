package com.wijayaprat.fragrancecenter.model

import java.io.Serializable

data class CartModel(
    var productId: String = "",
    var productName: String = "",
    var price: Int = 0,
    var quantity: Int = 0,
    var imageUrl: String = ""
) : Serializable