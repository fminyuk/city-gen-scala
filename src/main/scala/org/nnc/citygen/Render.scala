package org.nnc.citygen

import org.nnc.citygen.ast._

object Render {

  def str(node: Node): String = {
    val visitor = new RenderVisitor
    node.accept(visitor)
    visitor.result
  }

  private class RenderVisitor extends NodeVisitor {
    private val builder = new StringBuilder

    def result: String = builder.toString

    override def visit(node: Rule): Unit = {
      builder.append(node.id)
      builder.append(": ")
      builder.append(node.predecessor)
      if (node.cond != null) {
        builder.append(": ")
        node.cond.accept(this)
      }
      builder.append(" -> ")
      node.successor.accept(this)
      builder.append(": ")
      builder.append(node.probability)
    }

    override def visit(node: ExprAbs): Unit = {
      builder.append(node.value)
    }

    override def visit(node: ExprRel): Unit = {
      builder.append(node.value)
      builder.append('r')
    }

    override def visit(node: ExprIdent): Unit = {
      builder.append(node.name)
    }

    override def visit(node: ExprFunction): Unit = {
      builder.append(node.name)
      builder.append('(')
      add(node.args, ", ")
      builder.append(')')
    }

    override def visit(node: StmIdent): Unit = {
      builder.append(node.name)
    }

    override def visit(node: StmBlock): Unit = {
      builder.append('[')
      add(node.items, " ")
      builder.append(']')
    }

    override def visit(node: StmMatch): Unit = {
      builder.append('{')
      add(node.items, "|")
      builder.append('}')
    }

    override def visit(node: StmModifier): Unit = {
      builder.append(node.name)
      builder.append('(')
      add(node.args, ", ")
      builder.append(')')
    }

    private def add(nodes: Seq[Node], separator: String): Unit = {
      for ((node, i) <- nodes.zipWithIndex) {
        if (i > 0) {
          builder.append(separator)
        }
        node.accept(this)
      }
    }
  }
}
