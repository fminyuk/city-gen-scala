package org.nnc.citygen.parser

import org.nnc.citygen.ast._
import org.scalatest.FunSuite

class NodeParserInternalExprTest extends FunSuite {
  private val p = new NodeParserInternal

  test("abs") {
    val r = p.parseAll(p.expr, "10")

    assert(r.successful)
    assert(r.get == ExprAbs(10))
  }

  test("rel") {
    val r = p.parseAll(p.expr, "1r")

    assert(r.successful)
    assert(r.get == ExprRel(1))
  }

  test("ident") {
    val r = p.parseAll(p.expr, "x")

    assert(r.successful)
    assert(r.get == ExprIdent("x"))
  }

  test("function") {
    val r = p.parseAll(p.expr, "min(4, a)")

    assert(r.successful)
    assert(r.get == ExprFunction("min", List(ExprAbs(4), ExprIdent("a"))))
  }

  test("binary operators") {
    val r = p.parseAll(p.expr, "2 + 4 * 5")

    assert(r.successful)
    assert(r.get == ExprFunction("+", List(ExprAbs(2), ExprFunction("*", List(ExprAbs(4), ExprAbs(5))))))
  }

  test("unary operators") {
    val r = p.parseAll(p.expr, "-a * 4")

    assert(r.successful)
    assert(r.get == ExprFunction("*", List(ExprFunction("-", List(ExprIdent("a"))), ExprAbs(4))))
  }

  test("function rel + abs") {
    val r = p.parseAll(p.expr, "split(1, 2r, 3r, 4)")

    assert(r.successful)
    assert(r.get == ExprFunction("split", List(ExprAbs(1), ExprRel(2), ExprRel(3), ExprAbs(4))))
  }
}
