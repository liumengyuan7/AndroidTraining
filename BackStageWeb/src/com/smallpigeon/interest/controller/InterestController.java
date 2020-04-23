package com.smallpigeon.interest.controller;

import com.smallpigeon.entity.Interest;
import com.smallpigeon.interest.service.InterestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @className InterestController
 * @auther 吴东枚
 * @description 兴趣控制类
 * @date 2020/04/22 13:20
 */
@Controller
@RequestMapping("/interest")
public class InterestController {
    @Autowired
    private InterestService interestService;
    @RequestMapping("/getAll")
    public String getAllInterests(Model model){
        try {
            model.addAttribute("interests", this.interestService.getAllInterests());
            return "/tables/table_interest";
        }catch(Exception e){
            e.printStackTrace();
            return "/welcome";
        }
    }
    @GetMapping("/addInterests")
    public String toAdd(Model model){
        return "/tables/add_interest";
    }
    @PostMapping("/addInterests")
    public String addInterest(Interest interest){
        try {
            this.interestService.addInterest(interest);
            return "redirect:/interest/getAll";
        }catch(Exception e){
            e.printStackTrace();
            return "redirect:/tables/add_interest";
        }
    }
   @GetMapping("/deleteInterests/{id}")
    public String deleteInterest(@PathVariable("id") int id){
        try {
            this.interestService.deleteInterest(id);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "redirect:/interest/getAll";
    }
    @GetMapping("/editInterests/{id}")
    public String toEdit(@PathVariable("id") int id,Model model){
        model.addAttribute("interests",this.interestService.getInterest(id));
        return "/tables/edit_interest";
    }
    @PostMapping("/editInterests")
    public String updateUser(Interest interest){
        try{
            this.interestService.updateInterest(interest);
            return "redirect:/interest/getAll";
        }catch (Exception e){
            e.printStackTrace();
            return "redirect:/interest/editInterests"+interest.getId();
        }

    }
}
