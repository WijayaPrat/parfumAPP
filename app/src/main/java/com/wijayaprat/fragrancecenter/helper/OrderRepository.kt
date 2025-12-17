package com.wijayaprat.fragrancecenter.helper

import com.wijayaprat.fragrancecenter.model.OrderModel

object OrderRepository {
    val orders = mutableListOf<OrderModel>()
    val orderList = OrderRepository.orders

}
