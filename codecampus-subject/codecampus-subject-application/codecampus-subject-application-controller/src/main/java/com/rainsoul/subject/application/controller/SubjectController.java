package com.rainsoul.subject.application.controller;

import com.rainsoul.subject.infra.basic.entity.SubjectCategory;
import com.rainsoul.subject.infra.basic.service.SubjectCategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 刷题controller
 *
 * @author RainSoul
 * @create 2024/4/21
 */
@RestController
public class SubjectController {

    @Resource
    private SubjectCategoryService subjectCategoryService;

    @GetMapping("/test")
    public String test() {
        SubjectCategory subjectCategory = subjectCategoryService.queryById(14L);
        return subjectCategory.getCategoryName();
    }

}
