package org.nnc.citygen

import org.nnc.citygen.ast._
import org.scalatest.FunSuite

class RenderTest extends FunSuite {
  test("ExprReal") {
    val n = ExprReal(5)
    val r = Render.str(n)

    assert(r == "5.0")
  }

  test("ExprIdentifier") {
    val n = ExprIdentifier("sa")
    val r = Render.str(n)

    assert(r == "sa")
  }

  test("ExprUnaryOperator") {
    val n = ExprUnaryOperator("+", ExprIdentifier("a"))
    val r = Render.str(n)

    assert(r == "+a")
  }

  test("ExprBinaryOperator") {
    val n = ExprBinaryOperator(ExprReal(10), "+", ExprIdentifier("a"))
    val r = Render.str(n)

    assert(r == "(10.0 + a)")
  }

  test("ExprExprFunction_empty") {
    val n = ExprFunction("gen", List())
    val r = Render.str(n)

    assert(r == "gen()")
  }

  test("ExprExprFunction_one") {
    val n = ExprFunction("sin", List(ExprIdentifier("x")))
    val r = Render.str(n)

    assert(r == "sin(x)")
  }

  test("ExprExprFunction_many") {
    val n = ExprFunction("max", List(ExprIdentifier("a"), ExprReal(10)))
    val r = Render.str(n)

    assert(r == "max(a, 10.0)")
  }
}