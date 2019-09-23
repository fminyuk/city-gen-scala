package org.nnc.citygen.interpreters

import scala.reflect.runtime.universe.Type
import org.nnc.citygen.ast._

trait ExprCompiler {
  def compile(expr: Expr, resultType: Type): Either[Error, ExprProgram]
}
