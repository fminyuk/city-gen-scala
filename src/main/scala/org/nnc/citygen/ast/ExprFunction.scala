package org.nnc.citygen.ast

case class ExprFunction(name: String,
                        args: Seq[Expr]) extends Expr {

  override def accept(visitor: NodeVisitor): Unit = visitor.visit(this)
}
