package com.loft.controller;

import com.loft.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping(path = "/categoryList")
    public String categoryList(ModelMap modelMap) {
        modelMap.addAttribute("Kategorie", categoryService.getAll());
        return "category";
    }
}


