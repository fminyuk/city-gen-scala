package org.nnc.citygen.ast

trait NodeVisitor {
  def visit(node: Rule): Unit

  def visit(node: ExprReal): Unit

  def visit(node: ExprIdent): Unit

  def visit(node: ExprFunction): Unit

  def visit(node: ExprUnaryOperator): Unit

  def visit(node: ExprBinaryOperator): Unit
}
