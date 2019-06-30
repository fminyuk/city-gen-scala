package org.nnc.citygen.ast

case class ExprReal(value: Double) extends Expr {

  override def accept(visitor: NodeVisitor): Unit = visitor.visit(this)
}
