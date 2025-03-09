package com.kjq.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kjq.annotation.AuthCheck;
import com.kjq.common.BaseResponse;
import com.kjq.model.entity.Audio;
import com.kjq.model.vo.audio.AudioCreateReqVO;
import com.kjq.model.vo.audio.AudioPageReqVO;
import com.kjq.model.vo.audio.AudioRespVO;
import com.kjq.model.vo.audio.AudioUpdateReqVO;
import com.kjq.service.AudioService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
    public BaseResponse<Page<AudioRespVO>> getPage(@RequestBody AudioPageReqVO audioPageReqVO) {
        // 获取结果
        Integer pageNo = audioPageReqVO.getPageNo();
        Integer pageSize = audioPageReqVO.getPageSize();
        Page<Audio> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<Audio> wrapper = new LambdaQueryWrapper<>();
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
}

