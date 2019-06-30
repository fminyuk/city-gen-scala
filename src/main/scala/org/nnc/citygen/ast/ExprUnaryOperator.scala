package org.nnc.citygen.ast

case class ExprUnaryOperator(operator: String,
                             expr: Expr) extends Expr {

  override def accept(visitor: NodeVisitor): Unit = visitor.visit(this)
}
