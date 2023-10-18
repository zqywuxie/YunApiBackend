create database if not exists api_platform;

use api_platform;


-- auto-generated definition
create table interface_info
(
    id             bigint auto_increment comment '主键'
        primary key,
    name           varchar(256)                           not null comment '名称',
    description    varchar(256)                           null comment '描述',
    url            varchar(512)                           not null comment '接口地址',
    requestHeader  text                                   null comment '请求头',
    responseHeader text                                   null comment '响应头',
    status         int          default 0                 not null comment '接口状态（0-关闭，1-开启）',
    method         varchar(256)                           not null comment '请求类型',
    userId         bigint                                 not null comment '创建人',
    createTime     datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime     datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDeleted      tinyint      default 0                 not null comment '是否删除(0-未删, 1-已删)',
    requestParams  text                                   null comment '请求参数',
    responseParams text                                   not null comment '返回参数',
    requestExample text                                   not null comment '请求示例',
    returnType     varchar(512) default 'JSON'            null comment '返回类型',
    returnExample  text                                   not null comment '返回示例',
    avatarUrl      varchar(1024)                          not null comment '接口头像',
    invokeNum      bigint                                 not null comment '被调用次数'
)
    comment '接口信息';

-- auto-generated definition
create table user
(
    id           bigint auto_increment comment 'id'
        primary key,
    userAccount  varchar(256)                           not null comment '账号',
    userPassword varchar(512)                           not null comment '密码',
    gender       varchar(10)  default '0'               not null comment '性别',
    userName     varchar(256)                           null comment '用户昵称',
    userAvatar   varchar(1024)                          null comment '用户头像',
    userProfile  varchar(512)                           null comment '用户简介',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user/admin',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    accessKey    varchar(256)                           null comment '唯一标识',
    secretKey    varchar(256)                           null comment '密钥',
    email        varchar(512)                           null comment '邮箱',
    phone        varchar(512)                           null comment '手机号码',
    status       varchar(10)                            null comment '账号状态 0 正常/1 封禁'
)
    comment '用户' collate = utf8mb4_unicode_ci;

-- auto-generated definition
create table user_interface_info
(
    id              bigint auto_increment comment '主键'
        primary key,
    userId          bigint                             not null comment '调用用户Id',
    interfaceInfoId bigint                             not null comment '接口Id',
    totalNum        int      default 0                 not null comment '总调用次数',
    leftNum         int      default 0                 not null comment '剩余调用次数',
    status          int      default 0                 not null comment '0-正常 ，1-禁用。是否限制用户使用该接口',
    createTime      datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime      datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete        tinyint  default 0                 not null comment '是否删除(0-未删, 1-已删)'
)
    comment '用户调用接口关系表';

