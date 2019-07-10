package org.nnc.citygen.ast

trait NodeVisitor {
  def visit(node: Rule): Unit

  def visit(node: ExprAbs): Unit
  def visit(node: ExprRel): Unit
  def visit(node: ExprIdent): Unit
  def visit(node: ExprFunction): Unit

  def visit(node: StmIdent): Unit
  def visit(node: StmBlock): Unit
  def visit(node: StmMatch): Unit
  def visit(node: StmModifier): Unit
}
