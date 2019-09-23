package org.nnc.citygen.interpreters

import scala.reflect.runtime.universe.TypeTag
import org.nnc.citygen.ast._

trait ExprInterpreter {
  def exec[R: TypeTag: ValueCoder](expr: Expr): Either[Error, R]
}
