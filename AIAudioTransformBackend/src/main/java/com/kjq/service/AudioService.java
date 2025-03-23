package com.kjq.service;

import com.kjq.model.entity.Audio;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kjq.model.vo.audio.MyAudioRespVO;

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

    /**
     * 根据用户id获取音乐列表
     *
      * @param userId
     * @return
     */
    List<Audio> getSongList(Integer userId);

    /**
     * 根据用户id获取声音列表
     *
     * @param userId
     * @return
     */
    List<Audio> getVoiceList(Integer userId);

    /**
     * 获取音乐名称和声音名称
     *
     * @param myAudioRespVOS
     */
    void getSongAndVoiceName(List<MyAudioRespVO> myAudioRespVOS);
}
