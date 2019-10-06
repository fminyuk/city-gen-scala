package org.nnc.citygen.models

import org.scalatest.FunSuite

class Vec3Test extends FunSuite {
  test("+") {
    val a = Vec3(1, 2, 3)
    val b = Vec3(5, 7, 9)

    assert(a + b == Vec3(6, 9, 12))
  }

  test("-") {
    val a = Vec3(1, 2, 3)
    val b = Vec3(5, 7, 9)

    assert(a - b == Vec3(-4, -5, -6))
  }

  test("*") {
    val a = Vec3(1, 2, 3)

    assert(a * 3 == Vec3(3, 6, 9))
  }

  test("/") {
    val a = Vec3(3, 6, 9)

    assert(a / 3 == Vec3(1, 2, 3))
  }

  test("dot") {
    val a = Vec3(1, 2, 3)
    val b = Vec3(6, 5, 4)

    assert((a dot b) == 28)
  }

  test("cross") {
    val a = Vec3(1, 2, 3)
    val b = Vec3(4, 5, 7)

    assert((a cross b) == Vec3(-1, 5, -3))
  }

  test("length") {
    val a = Vec3(3, 4, 12)

    assert(a.length == 13)
  }

  test("norm") {
    val a = Vec3(3, 4, 12)

    assert(a.norm == Vec3(3.0 / 13.0, 4.0 / 13.0, 12.0 / 13.0))
  }
}
