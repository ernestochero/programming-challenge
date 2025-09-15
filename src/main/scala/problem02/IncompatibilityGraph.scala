package com.ernestochero.challenge
package problem02

final case class IncompatibilityGraph(
  vertices: Set[String],
  incompatibilityMap: Map[String, Set[String]]
):
  private def neighborsOf(code: String): Set[String] =
    incompatibilityMap.getOrElse(code, Set.empty)
  def areIncompatible(a: String, b: String): Boolean =
    neighborsOf(a).contains(b)
  def isCompatibleWithSet(codes: Set[String], candidate: String): Boolean =
    codes.forall(c => !areIncompatible(c, candidate))

object IncompatibilityGraph:
  def fromPromotions(promos: Seq[Promotion]): IncompatibilityGraph =
    val vertices = promos.map(_.code).toSet
    val incompatibilityEmptyMap = vertices.map(_ -> Set.empty[String]).toMap
    val incompatibilityMap = promos.foldLeft(incompatibilityEmptyMap) { (externalAcc, promo) =>
      promo.notCombinableWith.foldLeft(externalAcc) { (internalAcc, incompatibleCode) =>
        if incompatibleCode == promo.code || !vertices.contains(incompatibleCode) then internalAcc
        else
          val updatedInternalAcc = internalAcc.updated(promo.code, internalAcc(promo.code) + incompatibleCode) // P1 -> {P3}
          updatedInternalAcc.updated(incompatibleCode, updatedInternalAcc(incompatibleCode) + promo.code) // P3 -> {P1}
      }
    }
    IncompatibilityGraph(vertices, incompatibilityMap)
