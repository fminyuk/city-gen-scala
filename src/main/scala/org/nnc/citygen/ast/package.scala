package org.nnc.citygen

package object ast {

  sealed trait Expr

  final case class ExprIdent(name: String) extends Expr

  final case class ExprBool(value: Boolean) extends Expr

  final case class ExprInt(value: Int) extends Expr

  final case class ExprFloat(value: Double) extends Expr

  final case class ExprFunction(name: String, args: Seq[Expr]) extends Expr

  sealed trait Stm

  sealed trait StmPrg extends Stm

  sealed trait StmRes extends Stm

  final case class StmModRes(mods: Seq[StmMod], res: StmRes) extends StmPrg

  final case class StmBlock(items: Seq[StmPrg]) extends StmPrg

  final case class StmIdent(name: String) extends StmRes

  final case class StmGen(name: String, args: Seq[Expr], results: Seq[Stm]) extends StmRes

  final case class StmMod(name: String, args: Seq[Expr]) extends Stm

  final case class Rule(id: Int, predecessor: String, cond: Option[Expr], successor: Stm, probability: Option[Double])

  //  final case class StmValue(expr: Expr) extends Stm

}
