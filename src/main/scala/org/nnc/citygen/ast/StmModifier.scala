package org.nnc.citygen.ast

case class StmModifier(name: String,
                       args: Seq[Expr]) extends Stm {

  override def accept(visitor: NodeVisitor): Unit = visitor.visit(this)
}