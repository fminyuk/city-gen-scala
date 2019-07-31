package org.nnc.citygen.parser

import org.nnc.citygen.ast._
import org.scalatest.FunSuite

class NodeParserImplTest extends FunSuite {
  private val p = new NodeParserImpl

  test("empty") {
    val r = p.parse("")

    assert(r.isSuccess)
    assert(r.get.isEmpty)
  }

  test("one") {
    val r = p.parse("1: pre -> suc: 0.1")

    assert(r.isSuccess)
    assert(r.get.size == 1)
    assert(r.get(0) == Rule(1, "pre", null, StmIdent("suc"), 0.1))
  }

  test("many") {
    val r = p.parse("1: A -> B: 1\n2: B -> C: 1\n3: C -> D: 1")

    assert(r.isSuccess)
    assert(r.get.size == 3)
    assert(r.get(0) == Rule(1, "A", null, StmIdent("B"), 1))
    assert(r.get(1) == Rule(2, "B", null, StmIdent("C"), 1))
    assert(r.get(2) == Rule(3, "C", null, StmIdent("D"), 1))
  }
}
