package org.nnc.citygen.ast

case class ExprAbs(value: Double) extends Expr {

  override def accept(visitor: NodeVisitor): Unit = visitor.visit(this)
}
