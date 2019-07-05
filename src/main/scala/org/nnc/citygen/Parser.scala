package org.nnc.citygen

import org.nnc.citygen.ast._

import scala.util.parsing.combinator.RegexParsers

object Parser extends RegexParsers {
  private val integer = """[+-]\d+""".r
  private val real = """[+-]?\d+(:?\.\d*)?(:?[eE][+-]?\d+)?""".r
  private val identifier = """[a-zA-Z_][a-zA-Z_0-9]*""".r

  def expr: Parser[Expr] = operators(List(
    ("+ -", unary),
    ("!", unary),
    ("* /", binary),
    ("+ -", binary),
    ("== != >= <= > <", binary),
    ("&&", binary),
    ("||", binary)
  ))(factor)

  def func: Parser[Expr] = identifier ~ opt("(" ~> repsep(expr, ",") <~ ")") ^^ {
    case name ~ None => ExprIdent(name)
    case name ~ Some(args) => ExprFunction(name, args)
  }

  def value: Parser[Expr] = real ~ opt("r") ^^ {
    case value ~ None => ExprAbs(value.toDouble)
    case value ~ Some("r") => ExprRel(value.toDouble)
  }

  def factor: Parser[Expr] = value | func | "(" ~> expr <~ ")"

  private type Operators = (String, (Parser[Expr], Parser[String]) => Parser[Expr])

  private def operators(ops: Seq[Operators])(s: Parser[Expr]): Parser[Expr] = ops.foldLeft(s) {
    (p, op) => op._2(p, operexpr(op._1))
  }

  private def unary(p: Parser[Expr], ops: Parser[String]): Parser[Expr] = opt(ops) ~ p ^^ {
    case None ~ t => t
    case Some(op) ~ t => ExprFunction(op, List(t))
  }

  private def binary(p: Parser[Expr], ops: Parser[String]): Parser[Expr] = p ~ opt(ops ~ p) ^^ {
    case t ~ None => t
    case l ~ Some(op ~ r) => ExprFunction(op, List(l, r))
  }

  private def operexpr(ops: String): Parser[String] = ops.split("""\s+""").map(Parser(_)).reduce(_ | _)
}
