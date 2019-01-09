package net.menwei.config;

//swagger2的配置文件，在项目的启动类的同级文件建立

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableWebMvc
public class Swagger2Config implements WebMvcConfigurer {
    //swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("net.menwei.controller"))//为当前包路径
                .paths(PathSelectors.any())
                .build();
    }

    //构建 api文档的详细信息函数,注意这里的注解引用的是哪个
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot 测试使用 Swagger2 构建RESTful API")//页面标题
                .contact(new Contact("menwei", "http://www.menwei.net", ""))//创建人
                .version("1.0")//版本号
                .description("API 描述")
                .build();
    }

    /**
     * 将Swagger2 的swagger-ui.html加入资源路径下,否则Swagger2静态页面不能访问。要想使资源能够访问，可以有两种方法
     * 一：需要配置类继承WebMvcConfigurationSupport 类，实现addResourceHandlers方法。
     * 但是实现了WebMvcConfigurationSupport以后，Spring Boot的 WebMvc自动配置就会失效，具体表现比如访问不到
     * 静态资源（js，css等）了，这是因为WebMvc的自动配置都在WebMvcAutoConfiguration类中，但是类中有这个注解
     *
     * @param registry
     * @ConditionalOnMissingBean({WebMvcConfigurationSupport.class})，意思是一旦在容器中检测到 WebMvcConfigurationSupport这个类，WebMvcAutoConfiguration类中的配置都不生效。
     * 所以一旦我们自己写的配置类继承了WebMvcConfigurationSupport，相当于容器中已经有了WebMvcConfigurationSupport，
     * 所有默认配置都不会生效，都得自己在配置文件中配置。
     * 二：继承WebMvcConfigurer接口，这里采用此方法 网上有人说使用该方法会导致date编译等问题，可能在配置文件中得到解决，
     * 本人没有碰到，不多做解释
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}