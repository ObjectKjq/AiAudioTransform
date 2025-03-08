package com.kjq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kjq.model.entity.AiFile;
import com.kjq.mapper.AiFileMapper;
import com.kjq.service.AiFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 文件 服务实现类
 * </p>
 *
 * @author kongdefang
 * @since 2025-03-08
 */
@Service
public class AiFileServiceImpl extends ServiceImpl<AiFileMapper, AiFile> implements AiFileService {

    @Resource
    private AiFileMapper aiFileMapper;

    @Override
    public List<AiFile> getAiFiles() {
        return aiFileMapper.selectList(new QueryWrapper<>());
    }
}
