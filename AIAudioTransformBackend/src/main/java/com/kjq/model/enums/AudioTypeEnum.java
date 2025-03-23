package com.kjq.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 音频类型
 */
@AllArgsConstructor
@Getter
public enum AudioTypeEnum {

    // 类型（0歌曲1声音2结果）
    SONG(0, "歌曲"),
    VOICE(1, "声音"),
    RESULT(2, "结果");

    /**
     * 类型
     */
    private final Integer type;
    /**
     * 类型名
     */
    private final String name;

}
