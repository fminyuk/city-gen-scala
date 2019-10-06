package org.nnc.citygen.processors

import org.nnc.citygen.models.Shape

trait ShapeProcessor {
  def applicability(shape: Shape): Boolean

  def process(shape: Shape): Seq[Shape]
}
