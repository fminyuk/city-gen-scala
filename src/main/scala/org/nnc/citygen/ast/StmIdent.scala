package org.nnc.citygen.ast

case class StmIdent(name: String) extends Stm {

  override def accept(visitor: NodeVisitor): Unit = visitor.visit(this)
}