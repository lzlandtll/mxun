# 镜像基础环境
FROM openjdk:17-jdk

# 将jar包复制到镜像的工作目录中
COPY mxun-chatai.jar /usr/local/

# 镜像的工作目录
WORKDIR /usr/local

# 启动容器时执行的命令
CMD java -jar mxun-chatai.jar