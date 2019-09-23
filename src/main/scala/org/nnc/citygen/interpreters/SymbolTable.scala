package org.nnc.citygen.interpreters

trait SymbolTable {
  def getValue(ident: String): Seq[Value]
}

object SymbolTable {
  val IMPLICIT = "implicit"
}