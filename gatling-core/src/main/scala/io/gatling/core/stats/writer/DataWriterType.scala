/*
 * Copyright 2011-2017 GatlingCorp (http://gatling.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gatling.core.stats.writer

object DataWriterType {

  private val AllTypes = Seq(ConsoleDataWriterType, FileDataWriterType, GraphiteDataWriterType, LeakReporterDataWriterType)
    .map(t => t.name -> t).toMap

  def findByName(name: String): Option[DataWriterType] = AllTypes.get(name)
}

sealed abstract class DataWriterType(val name: String, val className: String)
object ConsoleDataWriterType extends DataWriterType("console", "io.gatling.core.stats.writer.ConsoleDataWriter")
object FileDataWriterType extends DataWriterType("file", "io.gatling.core.stats.writer.LogFileDataWriter")
object GraphiteDataWriterType extends DataWriterType("graphite", "io.gatling.metrics.GraphiteDataWriter")
object LeakReporterDataWriterType extends DataWriterType("leak", "io.gatling.core.stats.writer.LeakReporterDataWriter")
