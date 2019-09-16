package org.nnc.citygen.parsers

import org.nnc.citygen.ast._
import org.scalatest.FunSuite

class StmParserTest extends FunSuite {

  private val p = new StmParser {}

  test("ident") {
    val r = p.parseAll(p.stm, "x")

    assert(r.successful)
    assert(r.get == block(StmIdent("x")))
  }

  test("gen") {
    val r = p.parseAll(p.stm, "comp(face) {A | B}")

    assert(r.successful)
    assert(r.get == block(StmGen("comp", Seq(ExprIdent("face")), Seq(block(StmIdent("A")), block(StmIdent("B"))))))
  }

  test("mod") {
    val r = p.parseAll(p.stm, "S(1, 2, 3) A")

    assert(r.successful)
    assert(r.get == StmBlock(Seq(StmModRes(Seq(StmMod("S", Seq(ExprInt(1), ExprInt(2), ExprInt(3)))), StmIdent("A")))))
  }

  test("block") {
    val r = p.parseAll(p.stm, "A [B C] D")

    assert(r.successful)
    assert(r.get == StmBlock(Seq(mod(StmIdent("A")), block(StmIdent("B"), StmIdent("C")), mod(StmIdent("D")))))
  }

  private def block(prg: StmRes*): StmBlock = StmBlock(prg.map(mod))

  private def mod(prg: StmRes): StmModRes = StmModRes(Seq(), prg)
}
