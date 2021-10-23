package cn.tellsea.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SiteController {

    @GetMapping("/login")
    public String index() {
        return "login";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

    @GetMapping("/base")
    public String base() {
        return "index";
    }

    @GetMapping("/images")
    public String images() {
        return "imageDB";
    }
}
