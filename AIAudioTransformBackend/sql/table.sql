-- auto-generated definition
create table ai_file
(
    id          int unsigned auto_increment comment 'ID'
        primary key,
    file_name   varchar(255)     null comment '文件名称',
    file_url    varchar(255)     null comment '文件地址',
    content     mediumblob       null comment '内容',
    file_type   varchar(255)     null comment '文件类型',
    creator     varchar(30)      null comment '创建者',
    create_time datetime         not null comment '创建时间',
    updater     varchar(30)      null comment '更新者',
    update_time datetime         not null comment '更新时间',
    deleted     bit default b'0' not null comment '是否删除，默认为0（未删除）'
)
    comment '文件';


-- auto-generated definition
create table audio
(
    id          int unsigned auto_increment comment 'ID'
        primary key,
    audio_name  varchar(255)     not null comment '音频名称',
    audio_url   varchar(255)     not null comment '文件地址',
    type        tinyint          not null comment '类型（0歌曲1声音2结果）',
    song_id     int(255)         null comment '歌曲id',
    voice_id    int(255)         null comment '人声id',
    user_id     int(255)         not null comment '用户id',
    remark      varchar(255)     null comment '备注',
    creator     varchar(30)      null comment '创建者',
    create_time datetime         not null comment '创建时间',
    updater     varchar(30)      null comment '更新者',
    update_time datetime         not null comment '更新时间',
    deleted     bit default b'0' not null comment '是否删除，默认为0（未删除）'
)
    comment '音频表';

-- auto-generated definition
create table user
(
    id          int unsigned auto_increment comment 'ID'
        primary key,
    username    varchar(255)      not null comment '用户名',
    password    varchar(255)      not null comment '密码',
    role        tinyint           not null comment '角色（0用户1管理员）',
    avatar_url  varchar(255)      null comment '头像url',
    is_disable  tinyint           not null comment '是禁用（0否1是）',
    creator     varchar(30)       not null comment '创建者',
    create_time datetime          not null comment '创建时间',
    updater     varchar(30)       null comment '更新者',
    update_time datetime          not null comment '更新时间',
    deleted     tinyint default 0 not null comment '是否删除，默认为0（未删除）',
    constraint unique_username
        unique (username)
)
    comment '用户表';