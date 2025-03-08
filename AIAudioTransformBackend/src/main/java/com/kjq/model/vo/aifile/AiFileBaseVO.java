package com.kjq.model.vo.aifile;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;

/**
 * <p>
 * 文件
 * </p>
 *
 * @author kongdefang
 * @since 2025-03-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AiFileBaseVO {

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件地址
     */
    private String fileUrl;

    /**
     * 内容
     */
    private byte[] content;

    /**
     * 文件类型
     */
    private String fileType;

}
