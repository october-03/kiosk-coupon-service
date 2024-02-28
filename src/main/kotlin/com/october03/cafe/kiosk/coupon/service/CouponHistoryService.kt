package com.october03.cafe.kiosk.coupon.service

import com.october03.cafe.kiosk.coupon.dto.RegisterHistoryDto
import com.october03.cafe.kiosk.coupon.repository.CouponHistory
import com.october03.cafe.kiosk.coupon.repository.CouponHistoryRepository
import org.springframework.stereotype.Service

@Service
class CouponHistoryService(
  private val couponHistoryRepository: CouponHistoryRepository
) {
  fun registerHistory(req: RegisterHistoryDto) {
    val newHistory = CouponHistory(
      id = req.couponId,
      userId = req.userId
    )

    couponHistoryRepository.save(newHistory)
  }

  fun findAllHistory(): List<CouponHistory> {
    return couponHistoryRepository.findAll()
  }
}