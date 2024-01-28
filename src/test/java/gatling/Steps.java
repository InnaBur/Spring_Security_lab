package gatling;

import static io.gatling.javaapi.core.CoreDsl.StringBody;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.http.HttpDsl;

public class Steps {
    public static ChainBuilder authReg = CoreDsl.exec(
            HttpDsl.http("authentication")
                    .post("/api/v1/auth/authenticate")
                    .header("Content-Type", "application/json")
                    .body(StringBody("{ \"username\": \"admin\",  \"password\": \"admin\" }"))
                    .check(HttpDsl.status().is(200))
    );

    public static ChainBuilder register = CoreDsl.exec(
            HttpDsl.http("register")
                    .post("/api/v1/auth/register")
                    .header("Content-Type", "application/json")
                    .body(StringBody("{ \"username\": \"1\",  \"password\": \"112\" }"))
                    .check(HttpDsl.status().is(200))
    );
}
