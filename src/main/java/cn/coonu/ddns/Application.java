package cn.coonu.ddns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
//        app.setAddCommandLineProperties(false);// 配置是否读取在执行命令时加入的参数, 如: java -jar xxx.jar  --spring.profiles.active=dev
//        app.setBannerMode(Banner.Mode.OFF); // 除了在配置文件中配置, 还可以在这里配置;
        app.run(args);
//        SpringApplication.run(Application.class, args);
    }

}
