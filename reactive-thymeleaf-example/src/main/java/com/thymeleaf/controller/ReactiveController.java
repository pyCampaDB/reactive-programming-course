package com.thymeleaf.controller;

import com.thymeleaf.ProductService;
import com.thymeleaf.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.spring6.context.webflux.IReactiveDataDriverContextVariable;
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;

@Controller
public class ReactiveController {

    //private final ProductRepository productRepository;
    private final ProductService productService;
    public ReactiveController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping("/list")
    public String productsList(Model model){
        //reactive variable
        IReactiveDataDriverContextVariable reactiveList = new ReactiveDataDriverContextVariable(
                productService.searchAll(), 1
        );
        model.addAttribute("productsList", reactiveList);
        return "list";
    }
}
