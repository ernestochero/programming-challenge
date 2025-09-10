package com.ernestochero.challenge
package problem02

import munit.FunSuite

class PromotionCalculatorSpec extends FunSuite:
  private val promotions = Seq(
    Promotion("P1", Seq("P3")),
    Promotion("P2", Seq("P4", "P5")),
    Promotion("P3", Seq("P1")),
    Promotion("P4", Seq("P2")),
    Promotion("P5", Seq("P2"))
  )

  test("allCombinablePromotions returns expected maximal combos"):
    val expected = Set(
      PromotionCombo(Seq("P1", "P2")),
      PromotionCombo(Seq("P1", "P4", "P5")),
      PromotionCombo(Seq("P2", "P3")),
      PromotionCombo(Seq("P3", "P4", "P5"))
    )
    val result = PromotionCalculator.allCombinablePromotions(promotions).toSet
    assertEquals(result, expected)

  test("combinablePromotions for P1 returns expected combos"):
    val expected = Set(
      PromotionCombo(Seq("P1", "P2")),
      PromotionCombo(Seq("P1", "P4", "P5"))
    )
    val result = PromotionCalculator.combinablePromotions("P1", promotions).toSet
    assertEquals(result, expected)

  test("combinablePromotions for P3 returns expected combos"):
    val expected = Set(
      PromotionCombo(Seq("P2", "P3")),
      PromotionCombo(Seq("P3", "P4", "P5"))
    )
    val result = PromotionCalculator.combinablePromotions("P3", promotions).toSet
    assertEquals(result, expected)

