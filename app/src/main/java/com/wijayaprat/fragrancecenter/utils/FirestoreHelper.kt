package com.wijayaprat.fragrancecenter.utils

import com.wijayaprat.fragrancecenter.model.ParfumModel
import com.wijayaprat.fragrancecenter.model.OrderModel

object FirestoreHelper {
    // Simulasi fungsi addProduct
    fun addProduct(product: ParfumModel, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        // Logika Firebase/Database Anda di sini
        // contoh: firestore.collection("products").add(product)...
        onSuccess()
    }

    // Simulasi fungsi getProducts
    fun getProducts(onSuccess: (List<ParfumModel>) -> Unit, onFailure: (String) -> Unit) {
        // Logika ambil data
        onSuccess(arrayListOf()) // Return list kosong untuk mencegah error
    }

    // Simulasi fungsi getOrders
    fun getOrders(onSuccess: (List<OrderModel>) -> Unit, onFailure: (String) -> Unit) {
        onSuccess(arrayListOf())
    }

    // Simulasi fungsi updatePayment
    fun updatePaymentStatus(orderId: String, status: String, onSuccess: () -> Unit) {
        onSuccess()
    }
}