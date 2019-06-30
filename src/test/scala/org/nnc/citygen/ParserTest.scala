package org.nnc.citygen

import org.nnc.citygen.ast.{ExprBinaryOperator, ExprFunction, ExprIdent, ExprReal}
import org.scalatest.FunSuite

class ParserTest extends FunSuite {
  test("real") {
    val r = Parser.parseAll(Parser.expr, "10")

    assert(r.get == ExprReal(10))
  }

  test("ident") {
    val r = Parser.parseAll(Parser.expr, "x")

    assert(r.get == ExprIdent("x"))
  }

  test("function") {
    val r = Parser.parseAll(Parser.expr, "min(4, a)")

    assert(r.get == ExprFunction("min", List(ExprReal(4), ExprIdent("a"))))
  }

  test("binary") {
    val r = Parser.parseAll(Parser.expr, "4 * 5")

    assert(r.get == ExprBinaryOperator(ExprReal(4), "*", ExprReal(5)))
  }

  test("complex") {
    val r = Parser.parseAll(Parser.expr, "1 + min(a * 1, 10)")

    val p = Render.str(r.get)

    assert(p == "(1.0 + min((a * 1.0), 10.0))")
  }
}
