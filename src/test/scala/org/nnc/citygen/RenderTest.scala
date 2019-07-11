package org.nnc.citygen

import org.nnc.citygen.ast._
import org.scalatest.FunSuite

class RenderTest extends FunSuite {
  test("ExprAbs") {
    val n = ExprAbs(5)
    val r = Render.str(n)

    assert(r == "5.0")
  }

  test("ExprRel") {
    val n = ExprRel(5)
    val r = Render.str(n)

    assert(r == "5.0r")
  }

  test("ExprIdentifier") {
    val n = ExprIdent("sa")
    val r = Render.str(n)

    assert(r == "sa")
  }

  test("ExprFunction: empty") {
    val n = ExprFunction("gen", List())
    val r = Render.str(n)

    assert(r == "gen()")
  }

  test("ExprFunction: one") {
    val n = ExprFunction("sin", List(ExprIdent("x")))
    val r = Render.str(n)

    assert(r == "sin(x)")
  }

  test("ExprFunction: many") {
    val n = ExprFunction("max", List(ExprIdent("a"), ExprAbs(10)))
    val r = Render.str(n)

    assert(r == "max(a, 10.0)")
  }

  test("Rule: simple") {
    val n = Rule(1, "p", null, StmIdent("s"), 0.1)
    val r = Render.str(n)

    assert(r == "1: p -> s: 0.1")
  }

  test("Rule: condition") {
    val n = Rule(1, "p", ExprFunction(">", List(ExprIdent("h"), ExprAbs(1))), StmIdent("s"), 0.1)
    val r = Render.str(n)

    assert(r == "1: p: >(h, 1.0) -> s: 0.1")
  }

  test("Rule: modifier") {
    val n = Rule(1, "p", null, StmBlock(List(StmModifier("s", List(ExprAbs(1))), StmMatch(List(StmIdent("A"))))), 0.1)
    val r = Render.str(n)

    assert(r == "1: p -> [s(1.0) {A}]: 0.1")
  }
}
