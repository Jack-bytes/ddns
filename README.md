# ddns
### 阿里云动态域名修改
    使用前需在环境变量文件(.bash_profile)中配置:
    ALIYUN_ACCESS_KEY_ID=xxxxxxx
    ALIYUN_ACCESS_KEY_SECRET=xxxxxxxx
    export ALIYUN_ACCESS_KEY_ID ALIYUN_ACCESS_KEY_SECRET

### 排除了Tomcat依赖
    由于实际使用不需要Tomcat, 所以排除, 免得占用端口和内存;