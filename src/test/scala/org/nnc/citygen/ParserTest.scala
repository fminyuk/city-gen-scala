package org.nnc.citygen

import org.nnc.citygen.ast.{ExprAbs, ExprFunction, ExprIdent, ExprRel}
import org.scalatest.FunSuite

class ParserTest extends FunSuite {
  test("abs") {
    val r = Parser.parseAll(Parser.expr, "10")

    assert(r.get == ExprAbs(10))
  }

  test("rel") {
    val r = Parser.parseAll(Parser.expr, "1r")

    assert(r.get == ExprRel(1))
  }

  test("ident") {
    val r = Parser.parseAll(Parser.expr, "x")

    assert(r.get == ExprIdent("x"))
  }

  test("function") {
    val r = Parser.parseAll(Parser.expr, "min(4, a)")

    assert(r.get == ExprFunction("min", List(ExprAbs(4), ExprIdent("a"))))
  }

  test("complex: 2 + 4 * 5") {
    val r = Parser.parseAll(Parser.expr, "2 + 4 * 5")

    val p = Render.str(r.get)

    assert(p == "+(2.0, *(4.0, 5.0))")
  }

  test("complex: a * (-4) > -b") {
    val r = Parser.parseAll(Parser.expr, "a * (-4) > -b")

    val p = Render.str(r.get)

    assert(p == ">(*(a, -(4.0)), -(b))")
  }


  test("complex: 1 + min(a * 1, -10)") {
    val r = Parser.parseAll(Parser.expr, "1 + min(a * 1, -10)")

    val p = Render.str(r.get)

    assert(p == "+(1.0, min(*(a, 1.0), -(10.0)))")
  }

  test("complex: split(1, 0.2r, 0.1r, 2)") {
    val r = Parser.parseAll(Parser.expr, "split(1, 0.2r, 0.1r, 2)")

    val p = Render.str(r.get)

    assert(p == "split(1.0, 0.2r, 0.1r, 2.0)")
  }
}
