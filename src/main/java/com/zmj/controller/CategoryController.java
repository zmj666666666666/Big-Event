package com.zmj.controller;

import com.zmj.domain.Category;
import com.zmj.domain.Result;
import com.zmj.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public Result add(@RequestBody @Validated Category category){
        categoryService.add(category);
        return Result.success();
    }

    @GetMapping
    public Result<List<Category>> getAll(){
        List<Category> categoryList=categoryService.getAll();
        return Result.success(categoryList);
    }

    @GetMapping("/{id}")
    public Result<Category> getById(@PathVariable Integer id){
        Category category=categoryService.getById(id);
        return Result.success(category);
    }

    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Integer id){
        categoryService.deleteById(id);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody @Validated(Category.Update.class) Category category){
        categoryService.update(category);
        return Result.success();
    }

}
