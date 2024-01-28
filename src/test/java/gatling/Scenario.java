package gatling;

import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.ScenarioBuilder;

public class Scenario {

    public static ScenarioBuilder authScen = CoreDsl.scenario("authentication scen")
            .exec(Steps.authReg);

    public static ScenarioBuilder regScen = CoreDsl.scenario("register scen")
            .exec(Steps.register);
}
