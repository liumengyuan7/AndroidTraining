package com.community.controller;

import com.community.service.DynamicService;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @className DynamicController
 * @auther 刘梦圆
 * @description 动态控制器类
 * @date 2020/04/15 16:45
 */
@Controller
@RequestMapping("dynamic")
public class DynamicController {
    @Resource
    private DynamicService dynamicService;
    @Resource
	private ServletContext servletContext;
//    添加发布的动态到数据库
    @ResponseBody
    @RequestMapping("/addDynamic")
    public String addDynamic(@RequestParam("userId") String userId,
                             @RequestParam("pushContent") String pushContent,
                             @RequestParam("pushTime") String pushTime,@RequestParam("pushImg") String pushImg) throws Exception {
        System.out.println(userId+pushContent+pushTime+pushImg);
        String result =this.dynamicService.addDynamic(userId,pushTime,pushContent,pushImg);
        return result;
    }
    //获取的图片存入out中
	@ResponseBody
	@RequestMapping("/getPicture")
	public String getPicture(HttpServletRequest request) throws Exception {
		String path = servletContext.getRealPath("")+"dynamic\\";
		System.out.println(path);
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List<FileItem> items = upload.parseRequest(request);
        FileItem item = items.get(0);
		item.write(new File(path+item.getName()));
		return "true";
	}
	//得到所有动态
    @ResponseBody
    @RequestMapping(value = "getAllDynamic",produces = "text/html;charset=UTF-8")
    public String getAllDynamic(){
        String result = this.dynamicService.queryAllDynamic();
        String s = this.dynamicService.queryAllDynamicAndComment();
        System.out.println(s);
        if(result == null || result.equals("")){
			return "false";
		}else{
			return result;
		}
    }
    //得到所有动态 带评论内容
    @ResponseBody
    @RequestMapping(value = "getAllDynamicAndComment",produces = "text/html;charset=UTF-8")
    public String queryAllDynamicAndComment(){
        String result = this.dynamicService.queryAllDynamicAndComment();
        if(result == null || result.equals("")){
			return "false";
		}else{
			return result;
		}
    }
}
