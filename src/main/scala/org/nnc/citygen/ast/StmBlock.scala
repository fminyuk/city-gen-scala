package org.nnc.citygen.ast

case class StmBlock(items: Seq[Stm]) extends Stm {

  override def accept(visitor: NodeVisitor): Unit = visitor.visit(this)
}
