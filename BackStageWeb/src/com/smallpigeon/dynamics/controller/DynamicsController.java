package com.smallpigeon.dynamics.controller;

import com.smallpigeon.dynamics.service.DynamicsService;
import com.smallpigeon.entity.Dynamics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dynamics")
public class DynamicsController {
    @Autowired
    private DynamicsService dynamicsService;
    @RequestMapping("/getAll")
    public String getAllDynamics(Model model){
        try {
            model.addAttribute("dynamics", this.dynamicsService.getAllDynamics());
            return "/tables/table_dynamics";
        }catch(Exception e){
            e.printStackTrace();
            return "/welcome";
        }
    }
    @GetMapping("/addDynamics")
    public String toAdd(Model model){
        return "/tables/add_dynamics";
    }
    @PostMapping("/addDynamics")
    public String addDynamics(Dynamics dynamics){
        try {
            this.dynamicsService.addDynamics(dynamics);
            return "redirect:/dynamics/getAll";
        }catch(Exception e){
            e.printStackTrace();
            return "redirect:/tables/add_dynamics";
        }
    }
    @GetMapping("/deleteDynamics/{id}")
    public String deleteDynamics(@PathVariable("id") int id){
        try {
            this.dynamicsService.deleteDynamics(id);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "redirect:/dynamics/getAll";
    }
    @GetMapping("/editDynamics/{id}")
    public String toEdit(@PathVariable("id") int id,Model model){
        model.addAttribute("dynamics",this.dynamicsService.getDynamics(id));
        return "/tables/edit_dynamics";
    }
    @PostMapping("/editDynamics")
    public String updateDynamics(Dynamics dynamics){
        try{
            this.dynamicsService.updateDynamics(dynamics);
            return "redirect:/dynamics/getAll";
        }catch (Exception e){
            e.printStackTrace();
            return "redirect:/dynamics/editDynamics"+dynamics.getId();
        }

    }
}
