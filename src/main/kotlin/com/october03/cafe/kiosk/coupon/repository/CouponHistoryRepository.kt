package com.october03.cafe.kiosk.coupon.repository

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

@Entity
@Table(name = "coupon_histories")
data class CouponHistory(
  @Id
  val id: Long,
  val userId: Long,
  val usedAt: LocalDateTime = LocalDateTime.now(),
) {
  constructor() : this(
    0,
    0,
    LocalDateTime.now(),
  )
}


interface CouponHistoryRepository: JpaRepository<CouponHistory, Long> {

}