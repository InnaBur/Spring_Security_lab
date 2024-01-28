package gatling;

import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpDsl;
import io.gatling.javaapi.http.HttpProtocolBuilder;

public class GatlingTest extends Simulation {

    HttpProtocolBuilder httpProtocolBuilder = HttpDsl.http.baseUrl("http://localhost:8080");

    public GatlingTest() {
        this.setUp(
                Scenario.authScen.injectOpen(
                     CoreDsl.constantUsersPerSec(10).during(10)
                ),
                Scenario.regScen.injectOpen(
                        CoreDsl.constantUsersPerSec(1).during(1)
                )
        ).protocols(httpProtocolBuilder);
    }

}
