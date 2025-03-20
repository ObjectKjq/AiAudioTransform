package com.kjq.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kjq.annotation.AuthCheck;
import com.kjq.common.BaseResponse;
import com.kjq.constant.UserConstant;
import com.kjq.model.entity.AiFile;
import com.kjq.model.vo.aifile.*;
import com.kjq.service.AiFileService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
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

    @PostMapping("/upload")
    public BaseResponse<String> uploadFile(FileUploadReqVO uploadReqVO) throws Exception {
        MultipartFile file = uploadReqVO.getFile();
        String fileType = file.getContentType();
        byte[] content = IoUtil.readBytes(file.getInputStream());
        String fileName = UUID.randomUUID() + "." + fileType;
        // 文件上传
        AiFile aiFile = AiFile.builder()
                .fileName(fileName)
                .fileType(fileType)
                .content(content)
                .build();
        aiFileService.save(aiFile);
        // 修改返回文件路径
        Integer id = aiFile.getId();
        String fileUrl = "/ai-file/" + id + "/download/" + fileName;
        aiFile.setFileUrl(fileUrl);
        aiFileService.updateById(aiFile);
        return success(fileUrl);
    }

    @GetMapping("/{aiFileId}/download/{fileName}")
    public void getAiFile(HttpServletResponse response,
                          @PathVariable("aiFileId") Integer aiFileId,
                          @PathVariable("fileName") String fileName) {
        AiFile aiFile = aiFileService.getById(aiFileId);
        byte[] content = aiFile.getContent();
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            ServletOutputStream out = response.getOutputStream();
            out.write(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/add")
    public BaseResponse<Boolean> addAiFile(@RequestBody AiFileCreateReqVO aiFileCreateReqVO) {
        AiFile aiFile = AiFile.builder().build();
        BeanUtils.copyProperties(aiFileCreateReqVO, aiFile);
        aiFileService.save(aiFile);
        return success(true);
    }

    @DeleteMapping("/delete")
    public BaseResponse<Boolean> deleteAiFile(@RequestParam("id") Integer id) {
        aiFileService.removeById(id);
        return success(true);
    }

    @PutMapping("/update")
    public BaseResponse<Boolean> updateAiFile(@RequestBody AiFileUpdateReqVO aiFileUpdateReqVO) {
        AiFile aiFile = AiFile.builder().build();
        BeanUtils.copyProperties(aiFileUpdateReqVO, aiFile);
        aiFileService.updateById(aiFile);
        return success(true);
    }

    @GetMapping("/get")
    public BaseResponse<AiFileRespVO> getAiFile(@RequestParam("id") Integer id) {
        AiFile aiFile = aiFileService.getById(id);
        AiFileRespVO aiFileRespVO = new AiFileRespVO();
        BeanUtils.copyProperties(aiFile, aiFileRespVO);
        return success(aiFileRespVO);
    }

    @PostMapping("/page")
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

