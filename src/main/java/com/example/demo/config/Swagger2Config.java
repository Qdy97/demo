package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author 75412
 * 通过EnableSwagger2注解来启用Swagger2
 */
//@Profile("dev")
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()//select()函数返回一个ApiSelectorBuilder实例用来控制哪些接口暴露给Swagger来展示
                .apis(RequestHandlerSelectors.basePackage("com.example"))//采用指定扫描的包路径来定义，Swagger会扫描该包下所有Controller定义的API，并产生文档内容（除了被@ApiIgnore注解的请求）
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 用来创建该Api的基本信息（这些基本信息会展现在文档页面中）
     * @return ApiInfo
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Demo服务 RestFul Apis")
                .description("Demo服务系统 rest接口服务")
                .termsOfServiceUrl("https://www.baidu.com/")
                .contact(new Contact("qidy","https://github.com/qiduanyu/demo","13087058175@163.com"))
                .version("1")
                .build();
    }

}
