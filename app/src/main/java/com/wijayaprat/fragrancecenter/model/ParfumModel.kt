package com.wijayaprat.fragrancecenter.model

data class ParfumModel(
    val categoryId: Int = 0,
    val title: String = "",
    val description: String = "",
    val extra: String = "",
    val picUrl: String = "",   // ← WAJIB default ""
    val price: Int = 0,
    val rating: Double = 0.0,
    var quantity: Int = 1
)
