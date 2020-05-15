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
//    public String addDynamic(@RequestParam("userId") String userId,
//                             @RequestParam("pushContent") String pushContent,
//                             @RequestParam("pushTime") String pushTime,@RequestParam("pushImg") String pushImg) throws Exception {
//        System.out.println(userId+pushContent+pushTime+pushImg);
//        String result =this.dynamicService.addDynamic(userId,pushTime,pushContent,pushImg);
//        return result;
//    }
      public String addDynamic(@RequestParam("userId") String userId,
                             @RequestParam("pushContent") String pushContent,
                             @RequestParam("pushTime") String pushTime, @RequestParam("pushImg") String pushImg,
                                @RequestParam("forwardId") String forwardId,
                               @RequestParam("type") String type) throws Exception {
        System.out.println(userId+pushContent+pushTime+pushImg);
        String result =this.dynamicService.addDynamic(userId,pushTime,pushContent,pushImg,forwardId,type);
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
        if(result == null || result.equals("")){
			return "false";
		}else{
			return result;
		}
    }
   //得到自己发布的所有动态
    @ResponseBody
    @RequestMapping(value = "getAllDynamicAndCommentByUerId",produces = "text/html;charset=UTF-8")
    public String queryAllDynamicAndComment(@RequestParam("userId") String userId){
        String result = this.dynamicService.queryAllDynamicAndComment(userId);
        if(result == null || result.equals("")){
			return "false";
		}else{
			return result;
		}
    }
  /*
   * @Description 给动态添加评论信息
   * @Auther 刘梦圆
   * @Date 10:42 2020/04/28
   * @Param
   * @return
   */
   @ResponseBody
   @RequestMapping("/addComment")
   public String addComment(@RequestParam("dynamicId") String dynamicId,
                             @RequestParam("userId") String userId,
                             @RequestParam("content") String content,@RequestParam("contentTime") String contentTime) throws Exception {
       System.out.println(userId + dynamicId + content + contentTime);
       String result = this.dynamicService.addComment(dynamicId, userId, content, contentTime);
       return result;
   }
   /*
    * @Description 对评论进行回复
    * @Auther 刘梦圆
    * @Date 16:13 2020/04/28
    * @Param
    * @return
    */
   @ResponseBody
   @RequestMapping("/addReply")
   public String addReply(@RequestParam("commentId") String commentId,
                             @RequestParam("fromId") String fromId,
                             @RequestParam("toId") String toId,
                             @RequestParam("replyContent") String replyContent,@RequestParam("replyTime") String replyTime) throws Exception {
       System.out.println(commentId + fromId + toId + replyContent+replyTime);
       String result = this.dynamicService.addReply(commentId, fromId, toId, replyContent,replyTime);
       return result;
   }
    /*
     * @Description  对动态进行点赞
     * @Auther 刘梦圆
     * @Date 9:56 2020/04/29
     * @Param
     * @return
     */
    @ResponseBody
    @RequestMapping("/addZanNum")
    public  String addZanNum(@RequestParam("dynamicId") String dynamicId,
                             @RequestParam("userId") String userId,
                             @RequestParam("zanNumAfter") String zanNumAfter){
        String result = this.dynamicService.addZan(dynamicId,userId,zanNumAfter);
        return result;
    }
    /*
     * @Description  对动态取消点赞
     * @Auther 刘梦圆
     * @Date 11:08 2020/04/29
     * @Param
     * @return
     */
     @ResponseBody
     @RequestMapping("/decZanNum")
    public  String decZanNum(@RequestParam("dynamicId") String dynamicId,
                             @RequestParam("userId") String userId,
                             @RequestParam("zanNumAfter") String zanNumAfter){
        String result = this.dynamicService.decZan(dynamicId,userId,zanNumAfter);
        return result;
    }

    /*
     * @Description 对评论进行点赞
     * @Auther 刘梦圆
     * @Date 15:39 2020/05/08
     * @Param [dynamicId, commentId, userId, zanNumAfter]
     * @return java.lang.String
     */
    @ResponseBody
    @RequestMapping("/addZanNumByComment")
    public  String addZanNumByComment(@RequestParam("dynamicId") String dynamicId,@RequestParam("commentId") String commentId,
                             @RequestParam("userId") String userId,
                             @RequestParam("zanNumAfter") String zanNumAfter){
        String result = this.dynamicService.addZanNumByComment(dynamicId,commentId,userId,zanNumAfter);
        return result;
    }
    /*
     * @Description 对评论取消点赞
     * @Auther 刘梦圆
     * @Date 15:39 2020/05/08
     * @Param [dynamicId, commentId, userId, zanNumAfter]
     * @return java.lang.String
     */
    @ResponseBody
    @RequestMapping("/decZanNumByComment")
    public  String decZanNumByComment(@RequestParam("dynamicId") String dynamicId
             ,@RequestParam("commentId") String commentId,
                             @RequestParam("userId") String userId,
                             @RequestParam("zanNumAfter") String zanNumAfter){
        String result = this.dynamicService.decZanNumByComment(dynamicId,commentId,userId,zanNumAfter);
        return result;
    }

    /*
     * @Description 得到用户收藏的所有动态
     * @Auther 刘梦圆
     * @Date 16:05 2020/05/08
     * @Param [userId]
     * @return java.lang.String
     */
    @ResponseBody
    @RequestMapping(value = "getAllCollectByUserId",produces = "text/html;charset=UTF-8")
    public String getAllCollectByUserId(@RequestParam("userId") String userId){
        String result = this.dynamicService.queryAllCollectByUserId(userId);
        if(result == null || result.equals("")){
			return "false";
		}else{
			return result;
		}
    }
    /*
     * @Description 添加收藏
     * @Auther 刘梦圆
     * @Date 16:02 2020/05/08
     * @Param [dynamicId, userId]
     * @return java.lang.String
     */
    @ResponseBody
    @RequestMapping("/addCollect")
    public  String addCollect(@RequestParam("dynamicId") String dynamicId,@RequestParam("userId") String userId){
        String result = this.dynamicService.addCollect(dynamicId,userId);
        return result;
    }
    /*
     * @Description 取消收藏
     * @Auther 刘梦圆
     * @Date 16:03 2020/05/08
     * @Param [dynamicId, userId]
     * @return java.lang.String
     */
    @ResponseBody
    @RequestMapping("/decCollect")
    public  String decCollect(@RequestParam("dynamicId") String dynamicId,@RequestParam("userId") String userId){
        String result = this.dynamicService.decCollect(dynamicId,userId);
        return result;
    }
}
