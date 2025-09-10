package com.ernestochero.challenge
package problem02

import scala.math.Ordered.orderingToOrdered

case class Promotion(code: String, notCombinableWith: Seq[String])
case class PromotionCombo(promotionCodes: Seq[String])

object PromotionCalculator:
  private def generateIndependentSets(
    graph: IncompatibilityGraph,
    selected: Set[String],
    remaining: List[String]
  ): List[Set[String]] =
    remaining match
      case Nil => List(selected)
      case candidate :: rest =>
        val withCandidateSelected =
          if graph.isCompatibleWithSet(selected, candidate) then
            val newSelected = selected + candidate
            val compatibleRest = rest.filter(graph.isCompatibleWithSet(newSelected, _))
            generateIndependentSets(graph, newSelected, compatibleRest)
          else Nil
        val withoutCandidateSelected =
          generateIndependentSets(graph, selected, rest)
        withCandidateSelected ++ withoutCandidateSelected

  private def keepMaximal(sets: List[Set[String]]): List[Set[String]] =
    sets.filter(s => !sets.exists(o => o.size > s.size && s.subsetOf(o)))

  private def toSortedCombos(sets: Iterable[Set[String]]): Seq[PromotionCombo] =
    sets
      .filter(_.nonEmpty)
      .map(s => PromotionCombo(s.toList.sorted))
      .toSeq
      .sortBy(_.promotionCodes)

  def allCombinablePromotions(
      allPromotions: Seq[Promotion]
  ): Seq[PromotionCombo] =
    if allPromotions.isEmpty then Seq.empty
    else
      val graph = IncompatibilityGraph.fromPromotions(allPromotions)
      val promotionCodes = graph.vertices.toList.sorted
      val allSets = generateIndependentSets(graph, Set.empty, promotionCodes)
      val maximal = keepMaximal(allSets)
      toSortedCombos(maximal)

  def combinablePromotions(
      promotionCode: String,
      allPromotions: Seq[Promotion]
  ): Seq[PromotionCombo] =
    if allPromotions.isEmpty then Seq.empty
    else
      val graph = IncompatibilityGraph.fromPromotions(allPromotions)
      if !graph.vertices.contains(promotionCode) then Seq.empty
      else
        val rest = graph.vertices.toList.filter(_ != promotionCode).sorted
        val allSets = generateIndependentSets(graph, Set(promotionCode), rest)
        val maximal = keepMaximal(allSets)
        toSortedCombos(maximal)
