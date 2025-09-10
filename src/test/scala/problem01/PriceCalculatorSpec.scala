package com.ernestochero.challenge
package problem01

import munit.FunSuite

class PriceCalculatorSpec extends FunSuite:
  test("returns expected best prices for given inputs"):
    val inputRates = Seq(
      Rate("M1", "Military"),
      Rate("M2", "Military"),
      Rate("S1", "Senior"),
      Rate("S2", "Senior")
    )

    val inputCabinPrices = Seq(
      CabinPrice("CA", "M1", BigDecimal(200.00)),
      CabinPrice("CA", "M2", BigDecimal(250.00)),
      CabinPrice("CA", "S1", BigDecimal(225.00)),
      CabinPrice("CA", "S2", BigDecimal(260.00)),
      CabinPrice("CB", "M1", BigDecimal(230.00)),
      CabinPrice("CB", "M2", BigDecimal(260.00)),
      CabinPrice("CB", "S1", BigDecimal(245.00)),
      CabinPrice("CB", "S2", BigDecimal(270.00))
    )

    val result = PriceCalculator
      .getBestGroupPrices(inputRates, inputCabinPrices)
      .toSet

    val expected = Set(
      BestGroupPrice("CA", "M1", BigDecimal(200.0), "Military"),
      BestGroupPrice("CA", "S1", BigDecimal(225.0), "Senior"),
      BestGroupPrice("CB", "M1", BigDecimal(230.0), "Military"),
      BestGroupPrice("CB", "S1", BigDecimal(245.0), "Senior"),
    )

    assertEquals(result, expected)
