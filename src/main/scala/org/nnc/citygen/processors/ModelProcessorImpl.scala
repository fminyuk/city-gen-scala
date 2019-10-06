package org.nnc.citygen.processors

import org.nnc.citygen.models.{Model, Shape}

import scala.annotation.tailrec

class ModelProcessorImpl(processors: Seq[ShapeProcessor]) extends ModelProcessor {
  override def process(model: Model): Option[Model] = {
    ModelProcessorImpl.process(model, processors, 0)
  }
}

object ModelProcessorImpl {
  @tailrec
  private def process(model: Model, processors: Seq[ShapeProcessor], processorIndex: Int): Option[Model] = {
    if (processorIndex < processors.size) {
      process(model, processors(processorIndex)) match {
        case None => process(model, processors, processorIndex + 1)
        case some => some
      }
    } else {
      None
    }
  }

  private def process(model: Model, processor: ShapeProcessor): Option[Model] = {
    val applicability = model.shapes.map(processor.applicability)

    if (applicability.exists(a => a)) {
      val src = applicability.zip(model.shapes)

      val oldShapes = src.filter(!_._1).map(_._2)
      val newShapes = src.filter(_._1).flatMap(s => processor.process(s._2))

      Some(Model(oldShapes ++ newShapes))
    } else {
      None
    }
  }
}
