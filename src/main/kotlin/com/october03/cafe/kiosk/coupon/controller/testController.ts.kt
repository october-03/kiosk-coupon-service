package com.october03.cafe.kiosk.coupon.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("")
class TestController(
  @Value("\${api.base-url}")
  private val baseUrl: String
) {
  @RequestMapping("/test")
  fun test(): String {
    return baseUrl
  }
}