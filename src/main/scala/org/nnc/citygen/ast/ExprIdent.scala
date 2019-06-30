package org.nnc.citygen.ast

case class ExprIdent(name: String) extends Expr {

  override def accept(visitor: NodeVisitor): Unit = visitor.visit(this)
}
