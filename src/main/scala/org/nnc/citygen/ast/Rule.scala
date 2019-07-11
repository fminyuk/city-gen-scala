package org.nnc.citygen.ast

case class Rule(id: Int,
                predecessor: String,
                cond: Expr,
                successor: Stm,
                probability: Double) extends Node {

  override def accept(visitor: NodeVisitor): Unit = visitor.visit(this)
}
