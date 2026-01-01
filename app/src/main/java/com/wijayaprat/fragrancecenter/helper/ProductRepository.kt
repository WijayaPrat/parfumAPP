package com.wijayaprat.fragrancecenter.helper

import com.google.firebase.firestore.FirebaseFirestore
import com.wijayaprat.fragrancecenter.model.OrderModel
import com.wijayaprat.fragrancecenter.model.ParfumModel

class ProductRepository {

    private val firestore = FirebaseFirestore.getInstance()

    fun getProducts(
        onSuccess: (List<ParfumModel>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        firestore.collection("products")
            .get()
            .addOnSuccessListener { snapshot ->
                onSuccess(snapshot.toObjects(ParfumModel::class.java))
            }
            .addOnFailureListener { onFailure(it) }
    }

    // ✅ TAMBAHAN: ADD PRODUCT (FIX AdminAddProductActivity)
    fun addProduct(
        product: ParfumModel,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        firestore.collection("products")
            .add(product)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    // ✅ TAMBAHAN: REDUCE STOCK (FIX DetailedActivity)
    fun reduceStock(
        productId: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val ref = firestore.collection("products").document(productId)

        firestore.runTransaction { transaction ->
            val snapshot = transaction.get(ref)
            val stock = snapshot.getLong("stockA") ?: 0

            if (stock > 0) {
                transaction.update(ref, "stockA", stock - 1)
            } else {
                throw Exception("Stock habis")
            }
        }.addOnSuccessListener {
            onSuccess()
        }.addOnFailureListener {
            onFailure(it)
        }
    }

    fun saveOrder(
        order: OrderModel,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        firestore.collection("orders")
            .add(order)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    fun getOrders(
        onSuccess: (List<OrderModel>) -> Unit,
        onFailure: () -> Unit
    ) {
        firestore.collection("orders")
            .get()
            .addOnSuccessListener { snapshot ->
                onSuccess(snapshot.toObjects(OrderModel::class.java))
            }
            .addOnFailureListener { onFailure() }
    }

    fun updateOrderStatus(
        orderId: String,
        newStatus: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        firestore.collection("orders")
            .document(orderId)
            .update("status", newStatus)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }
}
