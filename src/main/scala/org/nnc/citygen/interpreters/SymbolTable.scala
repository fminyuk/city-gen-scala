package org.nnc.citygen.interpreters

trait SymbolTable {

  def getValue(ident: String): Option[Value]
}