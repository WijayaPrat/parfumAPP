package com.wijayaprat.fragrancecenter.utils

import com.wijayaprat.fragrancecenter.model.ParfumModel
import com.wijayaprat.fragrancecenter.model.OrderModel

@Suppress("unused")
object FirestoreHelper {

    // ================= ADD PRODUCT =================
    fun addProduct(
        product: ParfumModel,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        // TODO: Implement Firestore logic
        // firestore.collection("products").add(product)

        onSuccess()
    }

    // ================= GET PRODUCTS =================
    fun getProducts(
        onSuccess: (List<ParfumModel>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        // TODO: Implement Firestore logic
        onSuccess(emptyList())
    }

    // ================= GET ORDERS =================
    fun getOrders(
        onSuccess: (List<OrderModel>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        // TODO: Implement Firestore logic
        onSuccess(emptyList())
    }

    // ================= UPDATE PAYMENT =================
    fun updatePaymentStatus(
        orderId: String,
        status: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit = {}
    ) {
        // TODO: Implement Firestore logic
        onSuccess()
    }
}
