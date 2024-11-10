package com.example.CRUDdemo.rest;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {

    @GetMapping("/")
    public String GetHome(){
        return "temp/home-page";
    }

    @GetMapping("/showMyLoginPage")
    public String showLoginPage(){

        return "temp/myLogin-page";
    }
    @GetMapping("/leaders")
    public String getLeaderPage(){
        return "temp/leaders";
    }

    @GetMapping("/systems")
    public String getSystemPage(){
        return "temp/system";
    }
    @GetMapping("/access-denied")
    public String getDeniedPage(){
        return "temp/denied";
    }
}
