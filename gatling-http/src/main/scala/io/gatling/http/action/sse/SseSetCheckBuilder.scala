/*
 * Copyright 2011-2018 GatlingCorp (http://gatling.io)
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

package io.gatling.http.action.sse

import scala.concurrent.duration.FiniteDuration

import io.gatling.core.action.Action
import io.gatling.core.session.Expression
import io.gatling.core.structure.ScenarioContext
import io.gatling.http.action.HttpActionBuilder
import io.gatling.http.check.sse.{ SseMessageCheck, SseMessageCheckSequence }

import com.softwaremill.quicklens._

case class SseSetCheckBuilder(
    requestName:    Expression[String],
    sseName:        String,
    checkSequences: List[SseMessageCheckSequence]
) extends HttpActionBuilder {

  def wait(timeout: FiniteDuration)(checks: SseMessageCheck*): SseSetCheckBuilder =
    this.modify(_.checkSequences).using(_ ::: List(SseMessageCheckSequence(timeout, checks.toList)))

  override def build(ctx: ScenarioContext, next: Action): Action =
    new SseSetCheck(requestName, checkSequences, sseName, ctx.coreComponents.statsEngine, ctx.coreComponents.clock, next)
}

class SseCloseBuilder(requestName: Expression[String], sseName: String) extends HttpActionBuilder {

  override def build(ctx: ScenarioContext, next: Action): Action =
    new SseClose(requestName, sseName, ctx.coreComponents.statsEngine, ctx.coreComponents.clock, next)
}
