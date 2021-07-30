package com.codegym.controller;

import com.codegym.model.Category;
import com.codegym.service.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/create")
    public String showFormCategory(){
        return "/category/create";
    }
    @PostMapping("/create")
    public String create(Category category){
        categoryService.save(category);
        return "redirect:/categories";
    }

    @GetMapping("")
    public ModelAndView findAll(){
        ModelAndView modelAndView = new ModelAndView("/category/list");
        modelAndView.addObject("categories", categoryService.findAll());
        return modelAndView;
    }
    @GetMapping("{id}/edit")
    public ModelAndView showFormEdit(@PathVariable Long id){
        Optional<Category> category = categoryService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/category/edit");
        modelAndView.addObject("category", category.get());
        return modelAndView;
    }
    @PostMapping("/edit")
    public String update(@ModelAttribute Category category){
        categoryService.save(category);
        return "redirect:/categories";
    }
}
