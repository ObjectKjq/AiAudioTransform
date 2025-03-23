package com.kjq.model.vo.audio;

import com.baomidou.mybatisplus.annotation.*;
import com.kjq.model.vo.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
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
public class AudioPageReqVO extends PageParam {

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

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime[] createTime;


}
