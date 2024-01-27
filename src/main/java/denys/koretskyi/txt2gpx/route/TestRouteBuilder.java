package denys.koretskyi.txt2gpx.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class TestRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        restConfiguration()
                .component("servlet");

        from("rest:get:hello")
                .log("hello log")
                .transform().constant("Hello World");

        from("rest:get:bye")
                .log("bye log")
                .transform().constant("Bye World");
    }
}
