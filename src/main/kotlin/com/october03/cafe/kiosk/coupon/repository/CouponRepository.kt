package com.october03.cafe.kiosk.coupon.repository

import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

@Entity
@Table(name = "coupons")
data class Coupon(
  @Id
  val id: Long,
  val price: Long,
  val isUsed: Boolean,
  val issuedAt: LocalDateTime = LocalDateTime.now(),
) {
  constructor() : this(
    0,
    0,
    false,
    LocalDateTime.now(),
  )
}

interface CouponRepository: JpaRepository<Coupon, Long> {

}