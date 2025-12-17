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
                val products = snapshot.toObjects(ParfumModel::class.java)
                onSuccess(products)
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }


    // ================= ADD PRODUCT =================
    fun addProduct(
        product: ParfumModel,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        firestore.collection("products")
            .add(product)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e) }
    }

    // ================= REDUCE STOCK =================
    fun reduceStock(
        productId: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val productRef = firestore.collection("products").document(productId)

        firestore.runTransaction { transaction ->
            val snapshot = transaction.get(productRef)
            val currentStock = snapshot.getLong("stock") ?: 0

            if (currentStock > 0) {
                transaction.update(productRef, "stock", currentStock - 1)
            } else {
                throw Exception("Out of Stock")
            }
        }.addOnSuccessListener {
            onSuccess()
        }.addOnFailureListener { e ->
            onFailure(e)
        }
    }

    // ================= SAVE ORDER =================
    fun saveOrder(
        order: OrderModel,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        firestore.collection("orders")
            .add(order)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e) }
    }
}
