package org.nnc.citygen

import cats.Show
import cats.syntax.show._
import cats.instances.all._
import org.nnc.citygen.ast._

package object show {
  implicit def showExpr[T <: Expr]: Show[T] = Show.show(exprToString)

  implicit def showStm[T <: Stm](implicit showExpr: Show[Expr]): Show[T] = Show.show(stmToString)

  implicit def showRule(implicit showExpr: Show[Expr], showStm: Show[Stm]): Show[Rule] = Show.show({
    case Rule(id, predecessor, cond, successor, prob) =>
      val optCond = cond match {
        case None => ""
        case Some(cond) => s": ${cond.show}"
      }
      val optProb = prob match {
        case None => ""
        case Some(prob) => s": ${prob.show}"
      }

      s"${id.show}: ${predecessor.show}$optCond -> ${successor.show}$optProb"
  })

  private def exprToString[T <: Expr](e: T): String = e match {
    case value: ExprBool => value.value.toString
    case value: ExprInt => value.value.toString
    case value: ExprFloat => value.value.toString
    case ident: ExprIdent => ident.name
    case fun: ExprFunction => s"${fun.name}(${reduce(fun.args.map(exprToString), ", ")})"
  }

  private def stmToString[T <: Stm](e: T)(implicit showExpr: Show[Expr]): String = e match {
    case ident: StmIdent => ident.name
    case gen: StmGen =>
      val name = gen.name
      val args = reduce(gen.args.map(showExpr.show), ", ")
      val results = reduce(gen.results.map(stmToString), " | ")
      s"$name($args) {$results}"
    case mod: StmMod =>
      val name = mod.name
      val args = reduce(mod.args.map(showExpr.show), ", ")
      s"$name($args)"
    case mdr: StmModRes =>
      reduce(mdr.mods.map(stmToString) :+ stmToString(mdr.res), " ")
    case blk: StmBlock =>
      val items = reduce(blk.items.map(stmToString), " ")
      s"[$items]"
  }

  private def reduce(seq: Seq[String], delimiter: String, default: String = ""): String = {
    seq.reduceOption(_ + delimiter + _).getOrElse(default)
  }
}
