package invoicing.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hello")
public class HelloRestController {

    @GetMapping
    public String sayHello(@RequestParam(value = "name", defaultValue = "Guest") String name){
        return String.format("Hello %s, from Spring 5!", name);
    }

}
