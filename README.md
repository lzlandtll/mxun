# mxun

## 开始

该系统的主要功能主要是实现一个轻量化的博客系统, 用于交流学习使用, 目前只实现了登录注册和对接通义千问的功能

## 模块介绍

mxun-common: 公共模块 <br />
mxun-auth: 认证模块 <br />
mxun-sys: 系统模块 <br />
mxun-chatai: AI模块 <br />
mxun-third-party: 第三方模块 <br />
mxun-gateway: 网关模块 <br />

## 后端技术栈
* spring-cloud-alibaba: 整体是在该框架上面进行开发的
* openfeign: 服务间调用的框架
* nacos: 服务发现和注册中心, 配置中心暂时没有使用
* mybatis-flex: 数据库操作框架
* redisson: redis操作框架
* netty: 用于websocket通信的框架
* mysql: 数据库(用户数据, 文章信息存储)
* redis: 缓存数据存储
* mongo: ai对话信息存储
* docker: 短期的部署使用
* k8s: 后期本地部署使用(服务器资金太大)
