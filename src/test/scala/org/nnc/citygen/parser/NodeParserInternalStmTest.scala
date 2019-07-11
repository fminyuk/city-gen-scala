package org.nnc.citygen.parser

import org.nnc.citygen.ast._
import org.scalatest.FunSuite

class NodeParserInternalStmTest extends FunSuite {
  private val p = new NodeParserInternal

  test("ident") {
    val r = p.parseAll(p.stm, "x")

    assert(r.successful)
    assert(r.get == StmIdent("x"))
  }

  test("modifier") {
    val r = p.parseAll(p.stm, "rotate(x, 1)")

    assert(r.successful)
    assert(r.get == StmBlock(List(StmModifier("rotate", List(ExprIdent("x"), ExprAbs(1))))))
  }

  test("match") {
    val r = p.parseAll(p.stm, "sx(1) { A }")

    assert(r.successful)
    assert(r.get == StmBlock(List(StmModifier("sx", List(ExprAbs(1))), StmMatch(List(StmIdent("A"))))))
  }
}
