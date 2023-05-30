# 云Api免费开放平台

> 一个可以接入免费Api的开放平台，为开发者提供便利、实用的Api调用体验
>
> Java + React，包含用户前端界面和管理员后台界面
>
> 前端代码：[zqywuxie/YunApiFrontend: 云API开放平台前端 (github.com)](https://github.com/zqywuxie/YunApiFrontend)
>
> 后端代码:[[zqywuxie/YunApiBackend: 云API开放平台后端 (github.com)](https://github.com/zqywuxie/YunApiBackend)](https://github.com/zqywuxie/Study_Partner_backed)

## 项目介绍

云Api免费开放平台是一个能够提供Api服务的平台，旨在方便开发者使用和集成其他应用程序。开放平台通常会提供一系列的API接口，包括但不限于地理位置数据、天气预报、股票信息、新闻资讯等等。通过这些API，开发人员可以轻松地将平台上的数据整合到自己的应用程序中，并以此为基础构建出更多的新功能和服务。同时，API开放平台还可以帮助企业扩展业务范围，利用自身的数据和服务来吸引新用户和提高品牌曝光率。



项目后端使用语言为Java，使用spring boot框架进行快速开发后端架构，采用微服务架构开发，解耦业务模块，前端使用React，Ant Design Pro + Ant Design组件库，使用现成组件库快速开发项目。



## 项目背景

目前市场上的免费API开放平台存在一些痛点，比如：

- 数据质量不稳定，会出现一定的延迟和数据错误，给开发者带来麻烦。
- API使用限制比较多，比如要关注公众号，需要进行付费等功能。
- 安全性得不到保障，很容易导致用户数据的泄露等问题。
- 接口使用文档不全面，给一些刚开始学习的用户带来不便

综合以上的痛点，打算开发一个简单，便捷使用的API开放平台。该平台为用户提供一些常见的接口，并且接口文档全面，用户根据生成的accessKey和secretKey，使用平台的SDK就可以快速进行开发。

## 技术栈

### 前端技术栈

- 开发框架：React、Umi
- 脚手架：Ant Design Pro
- 组件库：Ant Design、Ant Design Components
- 语法扩展：TypeScript、Less
- 打包工具：Webpack
- 代码规范：ESLint、StyleLint、Prettier

### 后端技术栈：

- 主语言：Java
- 框架：SpringBoot、Mybatis-plus、Spring Cloud，ssm
- 数据库：Mysql8.0
- 中间件：RabbitMq(消息队列)，Redis(缓存)
- 注册中心：Nacos
- 服务调用：Dubbo
- 网关：Spring Cloud  Gateway



## 快速上手

### 前端

环境要求：nodejs >= 14

~~~sh
git https://github.com/zqywuxie/YunApiFrontend
cd YunApiFrontend
~~~

安装依赖

```sh
yarn / npm install
```

启动项目

```sh
npm run start:dev
```

或打开package.json，点击`scripts/dev` 旁边的箭头启动

### 后端

- 不适用nacos作为配置中心，就将yunapi-config里面的配置文件移动到对应的模块中，并改名为`application.yml`
- 更改redis,mysql,nacos,rabbitmq等配置信息
- 服务启动顺序参考：

  1. yunapi-backend
  2. yunapi-interface
  3. yunapi-gateway

## 功能模块

### 前端界面功能

* 用户、管理员
  * 登录注册
    * 短信验证：[短信验证码—Java实现](https://blog.csdn.net/idogbin/article/details/130444691)（待完成）
  * 个人主页
  * 设置个人信息
  * 管理员：用户管理
  * 管理员：接口管理
  * 管理员：接口分析、订单分析
* 接口
  * 浏览接口信息
  * 在线调用接口
  * 接口搜索
  * 购买接口（待完成）
  * 下载SDK调用接口（待完成）
  * 用户上传自己的接口（待完成）
* 订单（待完成）
  * 创建订单
  * 支付宝沙箱支付



### 后端模块



## 项目展示

