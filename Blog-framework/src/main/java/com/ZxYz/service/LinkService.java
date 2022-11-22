package com.ZxYz.service;

import com.ZxYz.domain.ResponseResult;
import com.ZxYz.domain.entity.Link;
import com.baomidou.mybatisplus.extension.service.IService;

public interface LinkService extends IService<Link> {

    ResponseResult<?> getAllLink();
}