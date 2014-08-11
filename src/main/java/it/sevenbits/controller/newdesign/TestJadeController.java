package it.sevenbits.controller.newdesign;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestJadeController {

    @RequestMapping(value = "/testJade.html", method = RequestMethod.GET)
    public ModelAndView showUsers() {
        return new ModelAndView("testJade.jade");
    }

}
