package com.smallpigeon.plan.controller;

import com.smallpigeon.entity.Interest;
import com.smallpigeon.entity.Plan;
import com.smallpigeon.plan.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @className PlanController
 * @auther 吴东枚
 * @description 计划控制类
 * @date 2020/04/28 13:20
 */
@Controller
@RequestMapping("/plan")
public class PlanController {
    @Autowired
    private PlanService planService;
    @RequestMapping("/getAll")
    public String getAllInterests(Model model){
        try {
            model.addAttribute("plans", this.planService.getAllPlans());
            return "/tables/table_plan";
        }catch(Exception e){
            e.printStackTrace();
            return "/welcome";
        }
    }
    @GetMapping("/addPlans")
    public String toAdd(Model model){
        return "/tables/add_plan";
    }
    @PostMapping("/addPlans")
    public String addPlan(Plan plan){
        try {
            this.planService.addPlan(plan);
            return "redirect:/plan/getAll";
        }catch(Exception e){
            e.printStackTrace();
            return "redirect:/tables/add_plan";
        }
    }
    @GetMapping("/deletePlans/{id}")
    public String deletePlan(@PathVariable("id") int id){
        try {
            this.planService.deletePlan(id);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "redirect:/plan/getAll";
    }
    @GetMapping("/editPlans/{id}")
    public String toEdit(@PathVariable("id") int id,Model model){
        model.addAttribute("plans",this.planService.getPlan(id));
        return "/tables/edit_plan";
    }
    @PostMapping("/editPlans")
    public String updatePlan(Plan plan){
        try{
            this.planService.updatePlan(plan);
            return "redirect:/plan/getAll";
        }catch (Exception e){
            e.printStackTrace();
            return "redirect:/plan/editPlans"+plan.getId();
        }

    }
}
