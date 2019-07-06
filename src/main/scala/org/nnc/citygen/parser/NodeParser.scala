package org.nnc.citygen.parser

import org.nnc.citygen.ast.Node

import scala.util.Try

trait NodeParser {
  def parse(src: String): Try[Node]
}
