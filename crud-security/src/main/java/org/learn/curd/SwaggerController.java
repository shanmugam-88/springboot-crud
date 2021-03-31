package org.learn.curd;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SwaggerController {
    @RequestMapping(value = "/")
    public String index() {
        return "redirect:swagger-ui/index.html";
    }
}
