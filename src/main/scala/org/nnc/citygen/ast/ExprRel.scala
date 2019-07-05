package org.nnc.citygen.ast

case class ExprRel(value: Double) extends Expr {

  override def accept(visitor: NodeVisitor): Unit = visitor.visit(this)
}
