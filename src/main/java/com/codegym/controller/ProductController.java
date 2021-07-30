package com.codegym.controller;


import com.codegym.model.Product;
import com.codegym.service.category.ICategoryService;
import com.codegym.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private IProductService productService;
    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/create")
    public ModelAndView showFormCreat(){
        ModelAndView modelAndView = new ModelAndView("/product/create");
        modelAndView.addObject("product", new Product());
        modelAndView.addObject("categories", categoryService.findAll());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView saveProduct(@ModelAttribute("product") Product product) {
        productService.save(product);
        ModelAndView modelAndView = new ModelAndView("/product/create");
        modelAndView.addObject("product", product);
        modelAndView.addObject("message", "Created new product successfully !");
        return modelAndView;
    }

    @GetMapping("")
    public ModelAndView findAll() {
        ModelAndView modelAndView = new ModelAndView("/product/list");
        modelAndView.addObject("products", productService.findAll());
        return modelAndView;
    }

    @GetMapping("{id}/delete")
    public ModelAndView delete(@PathVariable Long id){
        Optional<Product> product = productService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/product/delete");
        modelAndView.addObject("product", product.get());
        return modelAndView;
    }
    @PostMapping("/delete")
    public String delete(@ModelAttribute Product product){
        productService.remove(product.getId());
        return "redirect:/products";
    }
    @GetMapping("{id}/edit")
    public ModelAndView edit(@PathVariable Long id){
        Optional<Product> product = productService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/product/edit");
        modelAndView.addObject("product", product.get());
        modelAndView.addObject("categories", categoryService.findAll());
        return modelAndView;
    }

    @PostMapping("/edit")
    public ModelAndView update(Product product){
        productService.save(product);
        ModelAndView modelAndView = new ModelAndView("/product/list");
        modelAndView.addObject("message","Updated");
        modelAndView.addObject("products",productService.findAll());
        return modelAndView;
    }

}
