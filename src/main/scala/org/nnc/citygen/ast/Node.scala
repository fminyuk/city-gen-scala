package org.nnc.citygen.ast

trait Node {
  def accept(visitor: NodeVisitor): Unit
}
