package com.ernestochero.challenge
package problem01

case class Rate(rateCode: String, rateGroup: String)
case class CabinPrice(cabinCode: String, rateCode: String, price: BigDecimal)
case class BestGroupPrice(cabinCode: String, rateCode: String, price: BigDecimal, rateGroup: String)

object PriceCalculator:
  def getBestGroupPrices(rates: Seq[Rate], cabinPrices: Seq[CabinPrice]): Seq[BestGroupPrice] =
    val rateMap = rates.map(r => r.rateCode -> r.rateGroup).toMap
    val (knowCabinPrices, _) = cabinPrices.partition(cp => rateMap.contains(cp.rateCode))
    val grouped = knowCabinPrices.groupBy(cp => (cp.cabinCode, rateMap(cp.rateCode)))
    grouped.map { case ((cabinCode, rateGroup), cabinPrices) =>
      val bestPrice = cabinPrices.minBy(_.price)
      BestGroupPrice(cabinCode, bestPrice.rateCode, bestPrice.price, rateGroup)
    }.toSeq

