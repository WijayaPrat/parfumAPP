package com.wijayaprat.fragrancecenter.model

import java.io.Serializable

data class OrderModel(
    val id: String = "",
    val userId: String = "",
    val items: List<ParfumModel> = emptyList(),
    val totalPrice: Int = 0,
    var status: String = "Pending",
    val branch: String = ""
) : Serializable
