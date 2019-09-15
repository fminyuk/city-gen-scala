package org.nnc.citygen

package object ast {

  sealed trait Expr

  final case class ExprIdent(name: String) extends Expr

  final case class ExprBool(value: Boolean) extends Expr

  final case class ExprInt(value: Int) extends Expr

  final case class ExprFloat(value: Double) extends Expr

  final case class ExprFunction(name: String, args: Seq[Expr]) extends Expr

  sealed trait Stm

  final case class StmIdent(name: String) extends Stm

//  final case class StmValue(expr: Expr) extends Stm

//  final case class StmSubDiv(axis: StmIdent, values: Seq[StmValue]) extends Stm
//
//  final case class StmRepeat(axis: StmIdent, value: StmValue) extends Stm

  final case class StmGen(name: String, args: Seq[Expr], results: Seq[Stm]) extends Stm

//  final case class StmBlock(items: Seq[Stm]) extends Stm
//
//  final case class StmMatch(items: Seq[Stm]) extends Stm
//
//  final case class StmModifier(name: String, args: Seq[Expr]) extends Stm

  final case class Rule(id: Int, predecessor: String, cond: Option[Expr], successor: Stm, probability: Option[Double])

}
