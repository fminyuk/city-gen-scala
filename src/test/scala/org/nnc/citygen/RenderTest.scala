package org.nnc.citygen

import org.nnc.citygen.ast._
import org.scalatest.FunSuite

class RenderTest extends FunSuite {
  test("ExprAbs") {
    val n = ExprAbs(5)
    val r = Render.str(n)

    assert(r == "5.0")
  }

  test("ExprIdentifier") {
    val n = ExprIdent("sa")
    val r = Render.str(n)

    assert(r == "sa")
  }

  test("ExprExprFunction_empty") {
    val n = ExprFunction("gen", List())
    val r = Render.str(n)

    assert(r == "gen()")
  }

  test("ExprExprFunction_one") {
    val n = ExprFunction("sin", List(ExprIdent("x")))
    val r = Render.str(n)

    assert(r == "sin(x)")
  }

  test("ExprExprFunction_many") {
    val n = ExprFunction("max", List(ExprIdent("a"), ExprAbs(10)))
    val r = Render.str(n)

    assert(r == "max(a, 10.0)")
  }
}
