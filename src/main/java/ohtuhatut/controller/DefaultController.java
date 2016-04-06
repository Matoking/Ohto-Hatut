package ohtuhatut.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author tuomokar
 */
@Controller
public class DefaultController {
    
    @RequestMapping("*")
    public String index() {
        return "index";
    }
}
