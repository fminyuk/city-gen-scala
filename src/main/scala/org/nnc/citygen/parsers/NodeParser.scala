package org.nnc.citygen.parsers

import org.nnc.citygen.ast.Rule

import scala.util.Try

trait NodeParser {
  def parse(src: String): Try[Seq[Rule]]
}
