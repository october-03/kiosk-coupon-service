package com.october03.cafe.kiosk.coupon.controller

import com.october03.cafe.kiosk.coupon.service.CouponHistoryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CouponHistoryController(
  private val couponHistoryService: CouponHistoryService
) {
  @GetMapping("/coupon-histories")
  fun getCouponHistories() = couponHistoryService.findAllHistory()
}