package org.nnc.citygen.interpreters

trait ValueCoder[A] {
  def encode(value: A): Value

  def decode(value: Value): A
}

