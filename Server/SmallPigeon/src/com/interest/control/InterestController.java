package com.interest.control;

import java.util.List;

import com.entity.Interest;
import com.interest.service.InterestService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @auther angel
 * @description 兴趣表的控制
 * @date 2019/11/26 17:02
 */
@Controller
@RequestMapping("interest")
public class InterestController {

    @Resource
    private InterestService interestService;

}
