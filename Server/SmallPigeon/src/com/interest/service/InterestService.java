package com.interest.service;

import com.interest.dao.InterestMapper;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @auther angel
 * @description service
 * @date 2019/11/26 17:04
 */
@Service
public class InterestService {

    @Resource
    private InterestMapper interestMapper;

}
