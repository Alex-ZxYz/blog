package com.ZxYz.service.Impl;

import com.ZxYz.domain.entity.Tag;
import com.ZxYz.mapper.TagMapper;
import com.ZxYz.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
}
