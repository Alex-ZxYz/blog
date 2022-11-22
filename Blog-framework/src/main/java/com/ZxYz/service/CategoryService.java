package com.ZxYz.service;

import com.ZxYz.domain.ResponseResult;
import com.ZxYz.domain.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;


public interface CategoryService extends IService<Category> {
    ResponseResult<?> getCategoryList();
}
