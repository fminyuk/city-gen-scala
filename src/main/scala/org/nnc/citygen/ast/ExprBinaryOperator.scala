package org.nnc.citygen.ast

case class ExprBinaryOperator(left: Expr,
                              operator: String,
                              right: Expr) extends Expr {

  override def accept(visitor: NodeVisitor): Unit = visitor.visit(this)
}
