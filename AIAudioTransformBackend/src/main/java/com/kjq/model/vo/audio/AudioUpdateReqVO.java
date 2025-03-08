package com.kjq.model.vo.audio;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 音频表
 * </p>
 *
 * @author kongdefang
 * @since 2025-03-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AudioUpdateReq implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 音频名称
     */
    private String audioName;

    /**
     * 文件地址
     */
    private String audioUrl;

    /**
     * 类型（0歌曲1声音2结果）
     */
    private Integer type;

    /**
     * 歌曲id
     */
    private Integer songId;

    /**
     * 人声id
     */
    private Integer voiceId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 备注
     */
    private String remark;

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
