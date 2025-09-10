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
    val incompatibilityMap = promos.map(p => p.code -> p.notCombinableWith.toSet).toMap
    IncompatibilityGraph(vertices, incompatibilityMap)
