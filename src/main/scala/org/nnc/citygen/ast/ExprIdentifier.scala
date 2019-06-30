package org.nnc.citygen.ast

case class ExprIdentifier(name: String) extends Expr {

  override def accept(visitor: NodeVisitor): Unit = visitor.visit(this)
}
