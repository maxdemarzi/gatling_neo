//package request-bodies

import com.excilys.ebi.gatling.core.Predef._
import com.excilys.ebi.gatling.http.Predef._
import akka.util.duration._
import bootstrap._


class CreateNodesRest extends Simulation {
  val httpConf = httpConfig
    .baseURL("http://localhost:7474")
    .acceptHeader("application/json")

  val createNode = """{ "foo" : "bar" }"""

  val scn = scenario("Create Nodes via REST API")
    .repeat(1000) {
    exec(
      http("create node")
        .post("/db/data/node")
        .body(createNode)
        .asJSON
        .check(status.is(201)))
      .pause(0 milliseconds, 5 milliseconds)
  }


  setUp(
    scn.users(100).ramp(10).protocolConfig(httpConf)
  )
}
