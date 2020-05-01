package com.smallpigeon.reply.controller;

import com.smallpigeon.entity.Record;
import com.smallpigeon.entity.Reply;
import com.smallpigeon.reply.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reply")
public class ReplyController {
    @Autowired
    private ReplyService replyService;
    @RequestMapping("/getAll")
    public String getAllReply(Model model){
        try {
            model.addAttribute("reply", this.replyService.getAllReply());
            return "/tables/table_reply";
        }catch(Exception e){
            e.printStackTrace();
            return "/welcome";
        }
    }
    @GetMapping("/addReply")
    public String toAdd(Model model){
        return "/tables/add_reply";
    }
    @PostMapping("/addReply")
    public String addReply(Reply Reply){
        try {
            this.replyService.addReply(Reply);
            return "redirect:/reply/getAll";
        }catch(Exception e){
            e.printStackTrace();
            return "redirect:/tables/add_reply";
        }
    }
    @GetMapping("/deleteReply/{id}")
    public String deleteReply(@PathVariable("id") int id){
        try {
            this.replyService.deleteReply(id);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "redirect:/reply/getAll";
    }
    @GetMapping("/editReply/{id}")
    public String toEdit(@PathVariable("id") int id,Model model){
        model.addAttribute("reply",this.replyService.getReply(id));
        return "/tables/edit_reply";
    }
    @PostMapping("/editReply")
    public String updateReply(Reply reply){
        try{
            this.replyService.updateReply(reply);
            return "redirect:/reply/getAll";
        }catch (Exception e){
            e.printStackTrace();
            return "redirect:/reply/editReply"+reply.getId();
        }

    }
}
