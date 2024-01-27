package denys.koretskyi.txt2gpx.route;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Txt2gpxRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("telegram:bots")
                .log("bot log: ${body}")
                .to("telegram:bots");
    }
}
