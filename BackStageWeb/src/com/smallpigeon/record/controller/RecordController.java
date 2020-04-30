package com.smallpigeon.record.controller;

import com.smallpigeon.entity.Record;
import com.smallpigeon.record.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @className RecordController
 * @auther 吴东枚
 * @description 记录控制类
 * @date 2020/04/27 13:20
 */
@Controller
@RequestMapping("/record")
public class RecordController {
        @Autowired
        private RecordService recordService;
        @RequestMapping("/getAll")
        public String getAllRecords(Model model){
            try {
                model.addAttribute("records", this.recordService.getAllRecords());
                return "/tables/table_record";
            }catch(Exception e){
                e.printStackTrace();
                return "/welcome";
            }
        }
        @GetMapping("/addRecords")
        public String toAdd(Model model){
            return "/tables/add_record";
        }
        @PostMapping("/addRecords")
        public String addRecord(Record record){
            try {
                this.recordService.addRecord(record);
                return "redirect:/record/getAll";
            }catch(Exception e){
                e.printStackTrace();
                return "redirect:/tables/add_record";
            }
        }
        @GetMapping("/deleteRecords/{id}")
        public String deleteRecord(@PathVariable("id") int id){
            try {
                this.recordService.deleteRecord(id);
            }catch(Exception e){
                e.printStackTrace();
            }
            return "redirect:/record/getAll";
        }
        @GetMapping("/editRecords/{id}")
        public String toEdit(@PathVariable("id") int id,Model model){
            model.addAttribute("records",this.recordService.getRecord(id));
            return "/tables/edit_record";
        }
        @PostMapping("/editRecords")
        public String updateRecord(Record record){
            try{
                this.recordService.updateRecord(record);
                return "redirect:/record/getAll";
            }catch (Exception e){
                e.printStackTrace();
                return "redirect:/record/editRecords"+record.getId();
            }

        }
    }

