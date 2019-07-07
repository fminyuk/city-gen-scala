package org.nnc.citygen.ast

case class SubScope(sub: Seq[Stm]) extends Stm {

  override def accept(visitor: NodeVisitor): Unit = visitor.visit(this)
}
