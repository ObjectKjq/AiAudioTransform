package com.kjq.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kjq.annotation.AuthCheck;
import com.kjq.common.BaseResponse;
import com.kjq.model.entity.Audio;
import com.kjq.model.enums.AudioTypeEnum;
import com.kjq.model.enums.DeletedEnum;
import com.kjq.model.vo.audio.*;
import com.kjq.model.vo.user.UserRespVO;
import com.kjq.service.AudioService;
import com.kjq.service.UserService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static com.kjq.common.ResultUtils.success;

/**
 * <p>
 * 音频表 前端控制器
 * </p>
 *
 * @author kongdefang
 * @since 2025-03-08
 */
@RestController
@RequestMapping("/audio")
public class AudioController {

    @Resource
    private AudioService audioService;
    @Resource
    private UserService userService;

    @PostMapping("/add")
    public BaseResponse<Boolean> addAudio(@RequestBody AudioCreateReqVO audioCreateReqVO) {
        Audio audio = new Audio();
        BeanUtils.copyProperties(audioCreateReqVO, audio);
        audioService.save(audio);
        return success(true);
    }

    @DeleteMapping("/delete")
    public BaseResponse<Boolean> deleteAudio(@RequestParam("id") Integer id) {
        audioService.removeById(id);
        return success(true);
    }

    @PutMapping("/update")
    public BaseResponse<Boolean> updateAudio(@RequestBody AudioUpdateReqVO audioUpdateReqVO) {
        Audio audio = new Audio();
        BeanUtils.copyProperties(audioUpdateReqVO, audio);
        audioService.updateById(audio);
        return success(true);
    }

    @GetMapping("/get")
    public BaseResponse<AudioRespVO> getAudio(@RequestParam("id") Integer id) {
        Audio audio = audioService.getById(id);
        AudioRespVO audioRespVO = new AudioRespVO();
        BeanUtils.copyProperties(audio, audioRespVO);
        return success(audioRespVO);
    }

    @PostMapping("/page")
    public BaseResponse<Page<AudioRespVO>> getPage(@RequestBody AudioPageReqVO audioPageReqVO, HttpServletRequest request) {
        // 获取用户信息
        UserRespVO userInfo = userService.getUserInfo(request);
        // 获取结果
        Integer pageNo = audioPageReqVO.getPageNo();
        Integer pageSize = audioPageReqVO.getPageSize();
        Page<Audio> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<Audio> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Audio::getUserId, userInfo.getId());
        wrapper.eq(Audio::getDeleted, DeletedEnum.NO.getStatus());
        wrapper.ne(Audio::getType, AudioTypeEnum.RESULT.getType());
        // 拼接条件
        if (StrUtil.isNotBlank(audioPageReqVO.getAudioName())) {
            wrapper.like(Audio::getAudioName, audioPageReqVO.getAudioName());
        }
        if (ObjectUtils.isNotEmpty(audioPageReqVO.getType())) {
            wrapper.eq(Audio::getType, audioPageReqVO.getType());
        }
        if (ObjectUtils.isNotEmpty(audioPageReqVO.getCreateTime())) {
            wrapper.between(Audio::getCreateTime, audioPageReqVO.getCreateTime()[0], audioPageReqVO.getCreateTime()[1]);
        }
        wrapper.orderByDesc(Audio::getId);
        Page<Audio> audioPage = audioService.page(page, wrapper);
        // 生成返回值
        List<Audio> audios = audioPage.getRecords();
        List<AudioRespVO> audioRespVOS = audios.stream()
                .map(audio -> {
                    AudioRespVO audioRespVO = new AudioRespVO();
                    BeanUtils.copyProperties(audio, audioRespVO);
                    return audioRespVO;
                }).collect(Collectors.toList());
        Page<AudioRespVO> result = new Page<>(pageNo, pageSize, audioPage.getTotal());
        result.setRecords(audioRespVOS);
        return success(result);
    }

    @PostMapping("/my-page")
    public BaseResponse<Page<MyAudioRespVO>> getMyPage(@RequestBody AudioPageReqVO audioPageReqVO, HttpServletRequest request) {
        // 获取用户信息
        UserRespVO userInfo = userService.getUserInfo(request);
        // 获取结果
        Integer pageNo = audioPageReqVO.getPageNo();
        Integer pageSize = audioPageReqVO.getPageSize();
        Page<Audio> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<Audio> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Audio::getUserId, userInfo.getId());
        wrapper.eq(Audio::getDeleted, DeletedEnum.NO.getStatus());
        wrapper.eq(Audio::getType, AudioTypeEnum.RESULT.getType());
        // 拼接条件
        if (ObjectUtils.isNotEmpty(audioPageReqVO.getSongId())) {
            wrapper.eq(Audio::getSongId, audioPageReqVO.getSongId());
        }
        if (ObjectUtils.isNotEmpty(audioPageReqVO.getVoiceId())) {
            wrapper.eq(Audio::getVoiceId, audioPageReqVO.getVoiceId());
        }
        if (ObjectUtils.isNotEmpty(audioPageReqVO.getCreateTime())) {
            wrapper.between(Audio::getCreateTime, audioPageReqVO.getCreateTime()[0], audioPageReqVO.getCreateTime()[1]);
        }
        wrapper.orderByDesc(Audio::getId);
        Page<Audio> audioPage = audioService.page(page, wrapper);
        // 生成返回值
        List<Audio> audios = audioPage.getRecords();
        List<MyAudioRespVO> myAudioRespVOS = audios.stream()
                .map(audio -> {
                    MyAudioRespVO myAudioRespVO = new MyAudioRespVO();
                    BeanUtils.copyProperties(audio, myAudioRespVO);
                    return myAudioRespVO;
                }).collect(Collectors.toList());
        // 获取音乐名称和声音名称
        audioService.getSongAndVoiceName(myAudioRespVOS);
        // 获取
        Page<MyAudioRespVO> result = new Page<>(pageNo, pageSize, audioPage.getTotal());
        result.setRecords(myAudioRespVOS);
        return success(result);
    }

    @GetMapping("/list")
    public BaseResponse<List<AudioRespVO>> getList() {
        List<Audio> audios = audioService.getAudios();
        List<AudioRespVO> audioRespVOS = audios.stream()
                .map(audio -> {
                    AudioRespVO audioRespVO = new AudioRespVO();
                    BeanUtils.copyProperties(audio, audioRespVO);
                    return audioRespVO;
                }).collect(Collectors.toList());
        return success(audioRespVOS);
    }

    @GetMapping("/song-list")
    public BaseResponse<List<AudioRespVO>> getSongList(HttpServletRequest request) {
        // 获取用户信息
        UserRespVO userInfo = userService.getUserInfo(request);
        // 获取音乐列表
        List<Audio> audios = audioService.getSongList(userInfo.getId());
        // List<Audio> audios = audioService.getAudios();
        List<AudioRespVO> audioRespVOS = audios.stream()
                .map(audio -> {
                    AudioRespVO audioRespVO = new AudioRespVO();
                    BeanUtils.copyProperties(audio, audioRespVO);
                    return audioRespVO;
                }).collect(Collectors.toList());
        return success(audioRespVOS);
    }

    @GetMapping("/voice-list")
    public BaseResponse<List<AudioRespVO>> getVoiceList(HttpServletRequest request) {
        // 获取用户信息
        UserRespVO userInfo = userService.getUserInfo(request);
        // 获取声音列表
        List<Audio> audios = audioService.getVoiceList(userInfo.getId());
        // List<Audio> audios = audioService.getAudios();
        List<AudioRespVO> audioRespVOS = audios.stream()
                .map(audio -> {
                    AudioRespVO audioRespVO = new AudioRespVO();
                    BeanUtils.copyProperties(audio, audioRespVO);
                    return audioRespVO;
                }).collect(Collectors.toList());
        return success(audioRespVOS);
    }
}

