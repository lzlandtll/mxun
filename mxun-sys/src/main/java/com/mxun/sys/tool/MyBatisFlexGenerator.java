package com.mxun.sys.tool;

import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Desc: 代码生成器
 * @author: chenjun
 * @time: 2024/1/11 09:43
 */
public class MyBatisFlexGenerator {

        public static void main(String[] args) {
            //配置数据源
            HikariDataSource dataSource = new HikariDataSource();
            dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/mxun-admin?characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Shanghai&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true");
            dataSource.setUsername("root");
            dataSource.setPassword("123456");

            //创建配置内容，两种风格都可以。
            GlobalConfig globalConfig = createGlobalConfigUseStyle1();

            //通过 datasource 和 globalConfig 创建代码生成器
            Generator generator = new Generator(dataSource, globalConfig);

            //生成代码
            generator.generate();
        }

        public static GlobalConfig createGlobalConfigUseStyle1() {
            //创建配置内容
            GlobalConfig globalConfig = new GlobalConfig();

            //设置根包
            globalConfig.setBasePackage("com.mxun.admin");

            //设置表前缀和只生成哪些表
            globalConfig.setTablePrefix("admin_");
            globalConfig.setGenerateTable("admin_role_user");

            // 设置jdk
            globalConfig.setEntityJdkVersion(17);

            // 设置生成 实体
            globalConfig.setEntityGenerateEnable(true);
            // 设置生成entity 并启用 Lombok
            globalConfig.setEntityWithLombok(true);

            // 设置父级类
            //globalConfig.setEntitySuperClass(BaseEntity.class);
            // 生成接口文档
//            globalConfig.setEntityWithSwagger(true);
//            globalConfig.getEntityConfig().setSwaggerVersion(EntityConfig.SwaggerVersion.DOC);
            // 允许实体类覆盖
            globalConfig.getEntityConfig().setOverwriteEnable(true);
            // 实体类添加链式调用
            globalConfig.getEntityConfig().setWithActiveRecord(true);
            // 设置父类已有字段
            //globalConfig.getStrategyConfig().setIgnoreColumns("is_deleted", "created_by", "created_time", "updated_by", "updated_time", "version");

            // 生成接口类
            globalConfig.setControllerGenerateEnable(true);
            // 覆盖之前的文件
            globalConfig.getControllerConfig().setOverwriteEnable(true);
            // 生成接口
            globalConfig.setServiceGenerateEnable(true);
            // 生成实现类
            globalConfig.setServiceImplGenerateEnable(true);

            //设置生成 mapper
            globalConfig.setMapperGenerateEnable(true);
            return globalConfig;
        }
}


