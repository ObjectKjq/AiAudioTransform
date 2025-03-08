package com.kjq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kjq.model.entity.Audio;
import com.kjq.mapper.AudioMapper;
import com.kjq.service.AudioService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 音频表 服务实现类
 * </p>
 *
 * @author kongdefang
 * @since 2025-03-08
 */
@Service
public class AudioServiceImpl extends ServiceImpl<AudioMapper, Audio> implements AudioService {

    @Resource
    private AudioMapper audioMapper;

    @Override
    public List<Audio> getAudios() {
        return audioMapper.selectList(new QueryWrapper<>());
    }
}
