package com.wijayaprat.fragrancecenter.model

import com.google.firebase.Timestamp
import java.io.Serializable

data class OrderModel(
    val id: String = "",
    val userId: String = "",
    val items: List<ParfumModel> = emptyList(),
    val totalPrice: Int = 0,
    val timestamp: Timestamp = Timestamp.now(),
    var status: String = "Pending"   // ✅ TAMBAHAN AMAN
) : Serializable
