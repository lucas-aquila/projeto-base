package br.org.pti.fpti_base.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NavigationController {

	/**
     * 
     * @return
     */
    @GetMapping("/")
    public ModelAndView web() {
        return new ModelAndView("modules/index");
    }
    
//    @GetMapping("/mobile")
//    public ModelAndView mobile() {
//        return new ModelAndView("modules/mobile/index");
//    }
}