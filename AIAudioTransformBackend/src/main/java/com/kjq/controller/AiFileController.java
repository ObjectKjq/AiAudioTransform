package com.kjq.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kjq.annotation.AuthCheck;
import com.kjq.common.BaseResponse;
import com.kjq.model.entity.AiFile;
import com.kjq.model.vo.aifile.AiFileCreateReqVO;
import com.kjq.model.vo.aifile.AiFilePageReqVO;
import com.kjq.model.vo.aifile.AiFileRespVO;
import com.kjq.model.vo.aifile.AiFileUpdateReqVO;
import com.kjq.service.AiFileService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static com.kjq.common.ResultUtils.success;

/**
 * <p>
 * 文件 前端控制器
 * </p>
 *
 * @author kongdefang
 * @since 2025-03-08
 */
@RestController
@RequestMapping("/ai-file")
public class AiFileController {

    @Resource
    private AiFileService aiFileService;

    @PostMapping("/add")
    @AuthCheck()
    public BaseResponse<Boolean> addAiFile(@RequestBody AiFileCreateReqVO aiFileCreateReqVO) {
        AiFile aiFile = new AiFile();
        BeanUtils.copyProperties(aiFileCreateReqVO, aiFile);
        aiFileService.save(aiFile);
        return success(true);
    }

    @DeleteMapping("/delete")
    @AuthCheck()
    public BaseResponse<Boolean> deleteAiFile(@RequestParam("id") Integer id) {
        aiFileService.removeById(id);
        return success(true);
    }

    @PutMapping("/update")
    @AuthCheck()
    public BaseResponse<Boolean> updateAiFile(@RequestBody AiFileUpdateReqVO aiFileUpdateReqVO) {
        AiFile aiFile = new AiFile();
        BeanUtils.copyProperties(aiFileUpdateReqVO, aiFile);
        aiFileService.updateById(aiFile);
        return success(true);
    }

    @GetMapping("/get")
    @AuthCheck()
    public BaseResponse<AiFileRespVO> getAiFile(@RequestParam("id") Integer id) {
        AiFile aiFile = aiFileService.getById(id);
        AiFileRespVO aiFileRespVO = new AiFileRespVO();
        BeanUtils.copyProperties(aiFile, aiFileRespVO);
        return success(aiFileRespVO);
    }

    @PostMapping("/page")
    @AuthCheck()
    public BaseResponse<Page<AiFileRespVO>> getPage(@RequestBody AiFilePageReqVO aiFilePageReqVO) {
        // 获取结果
        Integer pageNo = aiFilePageReqVO.getPageNo();
        Integer pageSize = aiFilePageReqVO.getPageSize();
        Page<AiFile> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<AiFile> wrapper = new LambdaQueryWrapper<>();
        Page<AiFile> aiFilePage = aiFileService.page(page, wrapper);
        // 生成返回值
        List<AiFile> aiFiles = aiFilePage.getRecords();
        List<AiFileRespVO> aiFileRespVOS = aiFiles.stream()
                .map(aiFile -> {
                    AiFileRespVO aiFileRespVO = new AiFileRespVO();
                    BeanUtils.copyProperties(aiFile, aiFileRespVO);
                    return aiFileRespVO;
                }).collect(Collectors.toList());
        Page<AiFileRespVO> result = new Page<>(pageNo, pageSize, aiFilePage.getTotal());
        result.setRecords(aiFileRespVOS);
        return success(result);
    }

    @GetMapping("/list")
    @AuthCheck()
    public BaseResponse<List<AiFileRespVO>> getList() {
        List<AiFile> aiFiles = aiFileService.getAiFiles();
        List<AiFileRespVO> aiFileRespVOS = aiFiles.stream()
                .map(aiFile -> {
                    AiFileRespVO aiFileRespVO = new AiFileRespVO();
                    BeanUtils.copyProperties(aiFile, aiFileRespVO);
                    return aiFileRespVO;
                }).collect(Collectors.toList());
        return success(aiFileRespVOS);
    }
}

