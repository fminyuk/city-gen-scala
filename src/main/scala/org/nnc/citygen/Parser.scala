package org.nnc.citygen

import org.nnc.citygen.ast._

import scala.util.parsing.combinator.RegexParsers

object Parser extends RegexParsers {
  private val integer = """[+-]\d+""".r
  private val real = """[+-]?\d+(:?\.\d*)?(:?[eE][+-]?\d+)?""".r
  private val identifier = """[a-zA-Z_][a-zA-Z_0-9]*""".r


  def expr: Parser[Expr] = term ~ opt(("+" | "-") ~ term) ^^ {
    case t ~ None => t
    case l ~ Some(op ~ r) => ExprBinaryOperator(l, op, r)
  }

  def term: Parser[Expr] = factor ~ opt(("*" | "/") ~ factor) ^^ {
    case t ~ None => t
    case l ~ Some(op ~ r) => ExprBinaryOperator(l, op, r)
  }

  def func: Parser[Expr] = identifier ~ opt("(" ~> repsep(expr, ",") <~ ")") ^^ {
    case name ~ None => ExprIdent(name)
    case name ~ Some(args) => ExprFunction(name, args)
  }

  def factor: Parser[Expr] = real ^^ { s => ExprReal(s.toDouble) } | func | "(" ~> expr <~ ")"
}
