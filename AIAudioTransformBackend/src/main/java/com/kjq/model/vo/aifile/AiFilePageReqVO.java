package com.kjq.model.vo.aifile;

import com.baomidou.mybatisplus.annotation.*;
import com.kjq.model.vo.PageParam;
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
public class AiFilePageReqVO extends PageParam {

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

    /**
     * 创建者
     */
    private String creator;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新者
     */
    private String updater;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 是否删除，默认为0（未删除）
     */
    @TableLogic
    private Boolean deleted;


}
