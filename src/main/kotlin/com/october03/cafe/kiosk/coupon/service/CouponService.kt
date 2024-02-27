package com.october03.cafe.kiosk.coupon.service

import com.october03.cafe.kiosk.coupon.repository.Coupon
import com.october03.cafe.kiosk.coupon.repository.CouponRepository
import org.springframework.stereotype.Service

@Service
class CouponService(
  private val couponRepository: CouponRepository
) {
  fun issueCoupon(price: Long): Coupon {
    var couponCode: String = ""

    when (price) {
      5000L -> couponCode = "01"
      10000L -> couponCode = "03"
      30000L -> couponCode = "05"
      50000L -> couponCode = "07"
      100000L -> couponCode = "09"
    }

    val couponList = couponRepository.findAll()

    val filterList = couponList.filter { it.id.startsWith(couponCode) }

    val lastCoupon = if (filterList.isNotEmpty()) {
      filterList.last().id.substring(2).toLong()
    } else {
      0
    }

    val newCouponId = couponCode + String.format("%011d", lastCoupon + 1)

    val newCoupon = Coupon(
      id = newCouponId,
      price = price,
      isUsed = false
    )

    couponRepository.save(newCoupon)

    return newCoupon
  }
}