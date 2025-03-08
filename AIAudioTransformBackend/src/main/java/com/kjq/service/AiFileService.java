package com.kjq.service;

import com.kjq.model.entity.AiFile;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 文件 服务类
 * </p>
 *
 * @author kongdefang
 * @since 2025-03-08
 */
public interface AiFileService extends IService<AiFile> {

    /**
     * 获取文件列表
     *
     * @return
     */
    List<AiFile> getAiFiles();
}
