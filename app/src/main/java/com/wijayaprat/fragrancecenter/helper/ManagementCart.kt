package com.wijayaprat.fragrancecenter.helper

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wijayaprat.fragrancecenter.model.ParfumModel

class ManagementCart(context: Context) {

    private val tinyDB = TinyDB(context)
    private val gson = Gson()
    private val key = "cart_list"

    fun getCart(): MutableList<ParfumModel> {
        val json = tinyDB.getString(key)
        if (json.isNullOrEmpty()) return mutableListOf()

        val type = object : TypeToken<MutableList<ParfumModel>>() {}.type
        return gson.fromJson(json, type)
    }

    fun saveCart(list: MutableList<ParfumModel>) {
        val json = gson.toJson(list)
        tinyDB.putString(key, json)
    }

    fun addItem(item: ParfumModel) {
        val list = getCart()
        val index = list.indexOfFirst { it.title == item.title }

        if (index >= 0) {
            list[index].quantity += item.quantity
        } else {
            list.add(item)
        }

        saveCart(list)
    }

    fun clearCart() {
        tinyDB.remove(key)
    }

    fun getTotalPrice(): Int {
        return getCart().sumOf { it.price * it.quantity }
    }
}
