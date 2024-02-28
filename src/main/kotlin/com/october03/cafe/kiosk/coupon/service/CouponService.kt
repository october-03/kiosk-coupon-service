package com.october03.cafe.kiosk.coupon.service

import com.october03.cafe.kiosk.coupon.dto.RegisterHistoryDto
import com.october03.cafe.kiosk.coupon.dto.UseCouponDto
import com.october03.cafe.kiosk.coupon.dto.User
import com.october03.cafe.kiosk.coupon.repository.Coupon
import com.october03.cafe.kiosk.coupon.repository.CouponRepository
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class CouponService(
  private val couponRepository: CouponRepository,
  webClientBuilder: WebClient.Builder,
  private val couponHistoryService: CouponHistoryService
) {
  private var webClient = webClientBuilder.baseUrl("http://localhost:61999").build()

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

  fun findCouponWithId(id: String): Coupon {
    val coupon = couponRepository.findById(id).orElse(null)

    return coupon
  }

  fun findAllCoupon(): List<Coupon> {
    return couponRepository.findAll()
  }

  fun useCoupon(req: UseCouponDto): Coupon {
    try {
      val coupon = couponRepository.findById(req.couponId).orElse(null)

      if (coupon.isUsed) {
        throw Exception("Coupon is already used")
      }

      val user = getAuthToken(req.authToken).block()

      if (coupon != null && user != null) {
        coupon.isUsed = true
        couponRepository.save(coupon)

        val registerHistoryDto = RegisterHistoryDto(
          couponId = coupon.id,
          userId = user.id
        )

        couponHistoryService.registerHistory(registerHistoryDto)
      }

      return coupon
    } catch (e: Exception) {
      throw Exception(e.message)
    }
  }

  fun getAuthToken(authToken: String): Mono<User> {
    val res = webClient.get()
      .uri("/v1/user/auth-token/$authToken")
      .retrieve()
      .bodyToMono(User::class.java)

    return res
  }
}