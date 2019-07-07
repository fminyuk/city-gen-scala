package org.nnc.citygen.ast

case class RepMatch(items: Seq[Stm]) extends Stm {

  override def accept(visitor: NodeVisitor): Unit = visitor.visit(this)
}
