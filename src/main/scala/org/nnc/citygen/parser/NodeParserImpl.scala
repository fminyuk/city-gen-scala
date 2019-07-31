package org.nnc.citygen.parser

import org.nnc.citygen.ast._

import scala.util.{Failure, Success, Try}

class NodeParserImpl extends NodeParser {

  private val parser = new NodeParserInternal

  override def parse(src: String): Try[Seq[Rule]] = parser.parseAll(parser.rules, src) match {
    case parser.Success(result, _) => Success(result)
    case parser.NoSuccess(msg, _) => Failure(NodeParserException(msg))
  }
}
