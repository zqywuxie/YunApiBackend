INSERT INTO api_platform.interface_info (id, name, description, url, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDeleted, requestParams, responseParams, requestExample, returnType, returnExample, avatarUrl, invokeNum) VALUES (24, '123', '1234', '13', '132', '312', 0, '1312', 1661032514713161729, '2023-05-24 15:42:24', '2023-10-13 11:18:21', 1, null, '', '', 'JSON', '', '', 0);
INSERT INTO api_platform.interface_info (id, name, description, url, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDeleted, requestParams, responseParams, requestExample, returnType, returnExample, avatarUrl, invokeNum) VALUES (25, 'getNameByGet', '根据名字获得信息', 'http://localhost:8023/api/name/user', '{
 ”content-type“:"application/json"
}', '{
 ”content-type“:"application/json"
}', 1, 'POST', 1661032514713161729, '2023-05-25 18:39:40', '2023-10-14 20:56:33', 0, '[{"name":"userName","type":"string"}]', '', '', 'JSON', '', '', 0);
INSERT INTO api_platform.interface_info (id, name, description, url, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDeleted, requestParams, responseParams, requestExample, returnType, returnExample, avatarUrl, invokeNum) VALUES (26, 'queryPhoneLocation', '手机号归属地', 'http://localhost:8023/api/phone', '{
 ”content-type“:"application/json"
}', '{
 ”content-type“:"application/json"
}', 1, 'GET', 1661675898263977987, '2023-10-10 10:55:56', '2023-10-13 11:18:21', 0, '{
"city":"string"
}', '', '', 'JSON', '', '', 0);
INSERT INTO api_platform.interface_info (id, name, description, url, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDeleted, requestParams, responseParams, requestExample, returnType, returnExample, avatarUrl, invokeNum) VALUES (27, '查询IP信息', '查询IP信息', 'http://localhost:8023/api/ipInfo', '{
 ”content-type“:"application/json"
}', '{
 ”content-type“:"application/json"
}', 1, 'GET', 1661675898263977987, '2023-10-11 14:51:07', '2023-10-15 15:33:57', 0, '[{
     "fieldName": "ip",
     "type": "string",
     "desc": "输入IP地址",
     "required": "是"
 }]', '[
    {
        "fieldName": "code",
        "type": "int",
        "desc": "响应码"
    },
    {
        "fieldName": "data.ip",
        "type": "string",
        "desc": "请求的ip地址"
    },
    {
        "fieldName": "data.info.country",
        "type": "string",
        "desc": "ip所在国家"
    },
    {
        "fieldName": "data.info.prov",
        "type": "string",
        "desc": "ip所在省"
    },
    {
        "fieldName": "data.info.city",
        "type": "string",
        "desc": "ip所在城市"
    },
    {
        "fieldName": "data.info.lsp",
        "type": "string",
        "desc": "运营商"
    },
    {
        "fieldName": "message",
        "type": "string",
        "desc": "响应信息"
    }
]', 'http://localhost:8023/api/ipInfo?ip=58.154.0.0', 'jsonCode', '{
    "code": 0,
    "data": {
        "ip": "58.154.0.0",
        "info": {
            "country": "中国",
            "prov": "辽宁",
            "city": "沈阳市",
            "lsp": "教育网/沈阳建筑大学"
        }
    },
    "message": "ok"
}', 'https://wuxie-image.oss-cn-chengdu.aliyuncs.com/2023/09/19/IP地址.png', 10);
INSERT INTO api_platform.interface_info (id, name, description, url, requestHeader, responseHeader, status, method, userId, createTime, updateTime, isDeleted, requestParams, responseParams, requestExample, returnType, returnExample, avatarUrl, invokeNum) VALUES (28, '随机情话', '随机给出一句情话', 'http://localhost:9091/api/loveTalk', '{
 ”content-type“:"application/json"
}', null, 1, 'GET', 1661675898263977987, '2023-10-13 08:55:25', '2023-10-19 22:24:26', 0, null, null, 'http://localhost:8023/api/loveTalk', 'string', '你好，我爱你', 'https://wuxie-image.oss-cn-chengdu.aliyuncs.com/2023/09/19/love.png', 0);
