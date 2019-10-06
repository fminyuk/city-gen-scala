package org.nnc.citygen.processors

import org.nnc.citygen.models.Model

trait ModelProcessor {
  def process(model: Model): Option[Model]
}
