package org.nnc.citygen.parser

import org.nnc.citygen.ast._
import org.scalatest.FunSuite

class NodeParserInternalTest extends FunSuite {
  private val p = new NodeParserInternal

  test("expr: abs") {
    val r = p.parseAll(p.expr, "10")

    assert(r.successful)
    assert(r.get == ExprAbs(10))
  }

  test("expr: rel") {
    val r = p.parseAll(p.expr, "1r")

    assert(r.successful)
    assert(r.get == ExprRel(1))
  }

  test("expr: ident") {
    val r = p.parseAll(p.expr, "x")

    assert(r.successful)
    assert(r.get == ExprIdent("x"))
  }

  test("expr: function") {
    val r = p.parseAll(p.expr, "min(4, a)")

    assert(r.successful)
    assert(r.get == ExprFunction("min", List(ExprAbs(4), ExprIdent("a"))))
  }

  test("expr: binary operators") {
    val r = p.parseAll(p.expr, "2 + 4 * 5")

    assert(r.successful)
    assert(r.get == ExprFunction("+", List(ExprAbs(2), ExprFunction("*", List(ExprAbs(4), ExprAbs(5))))))
  }

  test("expr: unary operators") {
    val r = p.parseAll(p.expr, "-a * 4")

    assert(r.successful)
    assert(r.get == ExprFunction("*", List(ExprFunction("-", List(ExprIdent("a"))), ExprAbs(4))))
  }

  test("expr: function rel + abs") {
    val r = p.parseAll(p.expr, "split(1, 2r, 3r, 4)")

    assert(r.successful)
    assert(r.get == ExprFunction("split", List(ExprAbs(1), ExprRel(2), ExprRel(3), ExprAbs(4))))
  }

  test("rule: simple") {
    val r = p.parseAll(p.rule, "1: pre -> 0.1")

    assert(r.successful)
    assert(r.get == Rule(1, "pre", null, 0.1))
  }

  test("rule: condition") {
    val r = p.parseAll(p.rule, "1: pre: h > 1 -> 0.1")

    assert(r.successful)
    assert(r.get == Rule(1, "pre", ExprFunction(">", List(ExprIdent("h"), ExprAbs(1))), 0.1))
  }
}
