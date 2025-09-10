package com.ernestochero.challenge
package problem02

import munit.FunSuite

class IncompatibilityGraphSpec extends FunSuite:
  private val promotions = Seq(
    Promotion("P1", Seq("P3")),
    Promotion("P2", Seq("P4", "P5")),
    Promotion("P3", Seq("P1")),
    Promotion("P4", Seq("P2")),
    Promotion("P5", Seq("P2"))
  )

  test("fromPromotions creates correct graph"):
    val g = IncompatibilityGraph.fromPromotions(promotions)
    assertEquals(g.vertices, Set("P1", "P2", "P3", "P4", "P5"))
    assert(g.areIncompatible("P1", "P3"))
    assert(g.areIncompatible("P3", "P1"))
    assert(g.areIncompatible("P2", "P4"))
    assert(g.areIncompatible("P4", "P2"))
    assert(g.areIncompatible("P2", "P5"))
    assert(g.areIncompatible("P5", "P2"))

  test("isCompatibleWithSet works for candidates"):
    val g = IncompatibilityGraph.fromPromotions(promotions)
    assert(g.isCompatibleWithSet(Set("P1"), "P2"))
    assert(!g.isCompatibleWithSet(Set("P1"), "P3"))
    assert(g.isCompatibleWithSet(Set("P4"), "P5"))
    assert(!g.isCompatibleWithSet(Set("P4"), "P2"))
