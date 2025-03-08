package com.kjq.service;

import com.kjq.model.entity.Audio;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 音频表 服务类
 * </p>
 *
 * @author kongdefang
 * @since 2025-03-08
 */
public interface AudioService extends IService<Audio> {

    /**
     * 获取音频列表
     *
     * @return
     */
    List<Audio> getAudios();
}
