package denys.koretskyi.txt2gpx.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public String testEndpoint() {
        return "Hello World!";
    }
}
