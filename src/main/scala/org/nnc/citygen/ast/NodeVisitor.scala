package org.nnc.citygen.ast

trait NodeVisitor {
  def visit(node: Rule): Unit

  def visit(node: ExprAbs): Unit

  def visit(node: ExprIdent): Unit

  def visit(node: ExprFunction): Unit
}
