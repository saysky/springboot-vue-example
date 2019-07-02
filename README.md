# springboot-vue-example
## 关于本项目
 文章介绍：[SpringBoot + Vue Element UI 实现前后端分离](https://liuyanzhao.com/9511.html) <br/>
这个小项目是一个 springboot 和 vue 的案例，是5月份一个同学找博主做的毕设，服务器管理信息，由用户申请服务器和释放服务器，查看消息。
当时需求很简单，一个服务器信息的管理，一个消息管理。作为理解前后端项目分离，我觉得足够了。


- server_frontend 是 vue 项目，请使用 WebStorm 之类前端工具打开，请确保已安装 node 和 npm
- ServerManager 是 springboot 项目，请使用 IDEA 等后端工具打开，确保安装了 MySQL5.x，jdk1.8



## 项目启动
- 后端项目
  1. 创建数据库 server_manager，并导入 server_manager.sql 文件
  2. 使用 IDEA 以 Maven 方式导入 ServerManager 项目
  3. 修改 application.yml 里的配置信息，即数据库账号密码
  4. 启动项目，端口在在 8080
- 前端项目·
  1. 执行 `npm install` 下载依赖，如果下载失败，请删除 package-lock.json 文件，执行 `npm cache clean --force` 命令，然后重新尝试
  2. 执行 `npm run start`
  3. 启动成功后，端口在9090，默认会跳转到登录页面，账号密码在数据库的 user 表中可见
 
  
