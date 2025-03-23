package com.kjq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kjq.model.entity.Audio;
import com.kjq.mapper.AudioMapper;
import com.kjq.model.enums.AudioTypeEnum;
import com.kjq.model.enums.DeletedEnum;
import com.kjq.model.vo.audio.MyAudioRespVO;
import com.kjq.service.AudioService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<Audio> getSongList(Integer userId) {
        LambdaQueryWrapper<Audio> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Audio::getUserId, userId)
                .eq(Audio::getType, AudioTypeEnum.SONG.getType())
                .eq(Audio::getDeleted, DeletedEnum.NO.getStatus())
                .orderByDesc(Audio::getId);
        return audioMapper.selectList(wrapper);
    }

    @Override
    public List<Audio> getVoiceList(Integer userId) {
        LambdaQueryWrapper<Audio> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Audio::getUserId, userId)
                .eq(Audio::getType, AudioTypeEnum.VOICE.getType())
                .eq(Audio::getDeleted, DeletedEnum.NO.getStatus())
                .orderByDesc(Audio::getId);
        return audioMapper.selectList(wrapper);
    }

    @Override
    public void getSongAndVoiceName(List<MyAudioRespVO> myAudioRespVOS) {
        if (ObjectUtils.isEmpty(myAudioRespVOS)) {
            return;
        }
        List<Integer> songIds = myAudioRespVOS.stream()
                .map(MyAudioRespVO::getSongId)
                .distinct()
                .collect(Collectors.toList());
        List<Integer> voiceIds = myAudioRespVOS.stream()
                .map(MyAudioRespVO::getVoiceId)
                .distinct()
                .collect(Collectors.toList());
        List<Audio> songList = null;
        if (ObjectUtils.isNotEmpty(songIds)) {
            LambdaQueryWrapper<Audio> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(Audio::getId, songIds);
            wrapper.eq(Audio::getType, AudioTypeEnum.SONG.getType());
            wrapper.eq(Audio::getDeleted, DeletedEnum.NO.getStatus());
            songList = audioMapper.selectList(wrapper);
        }
        List<Audio> voiceList = null;
        if (ObjectUtils.isNotEmpty(songIds)) {
            LambdaQueryWrapper<Audio> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(Audio::getId, voiceIds);
            wrapper.eq(Audio::getType, AudioTypeEnum.VOICE.getType());
            wrapper.eq(Audio::getDeleted, DeletedEnum.NO.getStatus());
            voiceList = audioMapper.selectList(wrapper);
        }
        for (MyAudioRespVO myAudioRespVO : myAudioRespVOS) {
            if (ObjectUtils.isNotEmpty(songList)) {
                for (Audio audio : songList) {
                    if (myAudioRespVO.getSongId().equals(audio.getId()))
                        myAudioRespVO.setSongName(audio.getAudioName());
                }
            }
            if (ObjectUtils.isNotEmpty(voiceList)) {
                for (Audio audio : voiceList) {
                    if (myAudioRespVO.getVoiceId().equals(audio.getId()))
                        myAudioRespVO.setVoiceName(audio.getAudioName());
                }
            }
        }
    }
}
