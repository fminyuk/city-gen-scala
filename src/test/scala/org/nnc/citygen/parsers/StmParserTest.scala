package org.nnc.citygen.parsers

import org.nnc.citygen.ast._
import org.scalatest.FunSuite

class StmParserTest extends FunSuite {

  private val p = new StmParser {}

  test("ident") {
    val r = p.parseAll(p.stm, "x")

    assert(r.successful)
    assert(r.get == StmIdent("x"))
  }

  test("modifier") {
    val r = p.parseAll(p.stm, "rotate(x, 1)")

    assert(r.successful)
    assert(r.get == StmBlock(List(StmModifier("rotate", List(ExprIdent("x"), ExprInt(1))))))
  }

  test("match") {
    val r = p.parseAll(p.stm, "sx(1) { A }")

    assert(r.successful)
    assert(r.get == StmBlock(List(StmModifier("sx", List(ExprInt(1))), StmMatch(List(StmIdent("A"))))))
  }
}
