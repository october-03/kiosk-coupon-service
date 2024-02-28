package com.october03.cafe.kiosk.coupon.controller

import com.october03.cafe.kiosk.coupon.dto.IssueCouponDto
import com.october03.cafe.kiosk.coupon.repository.Coupon
import com.october03.cafe.kiosk.coupon.service.CouponService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CouponController(
  private val couponService: CouponService,
) {
  @PostMapping("/issue-coupon")
  fun issueCoupon(@RequestBody req: IssueCouponDto): Coupon {
    return couponService.issueCoupon(req.price)
  }

  @GetMapping("/coupon/{id}")
  fun getCoupon(@PathVariable id: String): Coupon {
    return couponService.findCouponWithId(id)
  }

  @GetMapping("/coupons")
  fun getCoupons(): List<Coupon> {
    return couponService.findAllCoupon()
  }
}