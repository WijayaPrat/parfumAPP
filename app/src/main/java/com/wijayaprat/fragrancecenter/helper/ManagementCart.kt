package com.wijayaprat.fragrancecenter.helper

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wijayaprat.fragrancecenter.model.ParfumModel

class ManagementCart(context: Context) {

    private val prefs = context.getSharedPreferences("CART_PREF", Context.MODE_PRIVATE)
    private val gson = Gson()

    @Suppress("unused")
    fun addItem(item: ParfumModel) {
        val list = getCart()
        val index = list.indexOfFirst { it.id == item.id }

        if (index >= 0) {
            list[index].quantity++
        } else {
            list.add(item)
        }
        saveCart(list)
    }

    fun getCart(): ArrayList<ParfumModel> {
        val json = prefs.getString("cart_list", null) ?: return arrayListOf()
        val type = object : TypeToken<ArrayList<ParfumModel>>() {}.type
        return gson.fromJson(json, type)
    }

    fun clearCart() {
        saveCart(arrayListOf())
    }

    fun getTotalPrice(): Int {
        return getCart().sumOf { it.price * it.quantity }
    }

    private fun saveCart(list: ArrayList<ParfumModel>) {
        prefs.edit {
            putString("cart_list", gson.toJson(list))
        }
    }
}
