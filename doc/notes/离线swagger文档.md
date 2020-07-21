Provide a method to generate swggeer2 offline documentation, better way is to use .yaml file in swagger hub as ZhaoXuyang did.
# 背景
在上一篇博客介绍了swagger在线文档的生成办法，通过引入相应的maven依赖，配置swaggerConfig类以及添加注解的办法，在springboot项目启动后 [http://127.0.0.1:8080/swagger-ui.html](http://127.0.0.1:8080/swagger-ui.html) 处可以访问项目的在线动态文档。但是项目关闭时，无法获取文档，这时候就需要一份静态的文档。
以下给出两个导出静态接口文档的方法，奇怪的是单独运行方法一不能生成html文档，单独运行方法二无法生成任何文档，可以先运行方法一再运行方法二，且每次更改接口时都要重复这个操作。
# 方法一
## 添加maven依赖

```java
//dependencies处
<dependency>
    <groupId>io.github.swagger2markup</groupId>
    <artifactId>swagger2markup</artifactId>
    <version>1.3.3</version>
</dependency>
//repositories处
<repositories>
    <repository>
        <snapshots>
            <enabled>true</enabled>
            <updatePolicy>always</updatePolicy>
        </snapshots>
        <id>jcenter-releases</id>
        <name>jcenter</name>
        <url>http://jcenter.bintray.com</url>
    </repository>
</repositories>
```
## 添加test类，通过单元测试生成文档

```java
package com.ilife.authservice.docs;

import io.github.swagger2markup.GroupBy;
import io.github.swagger2markup.Language;
import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;
import org.junit.Test;

import java.net.URL;
import java.nio.file.Paths;

public class Docs {
    /**
     * 生成AsciiDocs格式文档
     * @throws Exception
     */
    @Test
    public void generateAsciiDocs() throws Exception {
        //    输出Ascii格式
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.ASCIIDOC)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .withGeneratedExamples()
                .withoutInlineSchema()
                .build();

        Swagger2MarkupConverter.from(new URL("http://127.0.0.1:8080/v2/api-docs"))
                .withConfig(config)
                .build()
                .toFolder(Paths.get("./docs/asciidoc/generated"));
    }

    /**
     * 生成Markdown格式文档
     * @throws Exception
     */
    @Test
    public void generateMarkdownDocs() throws Exception {
        //    输出Markdown格式
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.MARKDOWN)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .withGeneratedExamples()
                .withoutInlineSchema()
                .build();

        Swagger2MarkupConverter.from(new URL("http://localhost:8080/v2/api-docs"))
                .withConfig(config)
                .build()
                .toFolder(Paths.get("./docs/markdown/generated"));
    }
    /**
     * 生成Confluence格式文档
     * @throws Exception
     */
    @Test
    public void generateConfluenceDocs() throws Exception {
        //    输出Confluence使用的格式
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.CONFLUENCE_MARKUP)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .withGeneratedExamples()
                .withoutInlineSchema()
                .build();

        Swagger2MarkupConverter.from(new URL("http://localhost:8080/v2/api-docs"))
                .withConfig(config)
                .build()
                .toFolder(Paths.get("./docs/confluence/generated"));
    }

    /**
     * 生成AsciiDocs格式文档,并汇总成一个文件
     * @throws Exception
     */
    @Test
    public void generateAsciiDocsToFile() throws Exception {
        //    输出Ascii到单文件
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.ASCIIDOC)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .withGeneratedExamples()
                .withoutInlineSchema()
                .build();

        Swagger2MarkupConverter.from(new URL("http://localhost:8080/v2/api-docs"))
                .withConfig(config)
                .build()
                .toFile(Paths.get("./docs/asciidoc/generated/all"));
    }

    /**
     * 生成Markdown格式文档,并汇总成一个文件
     * @throws Exception
     */
    @Test
    public void generateMarkdownDocsToFile() throws Exception {
        //    输出Markdown到单文件
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.MARKDOWN)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .withGeneratedExamples()
                .withoutInlineSchema()
                .build();

        Swagger2MarkupConverter.from(new URL("http://localhost:8080/v2/api-docs"))
                .withConfig(config)
                .build()
                .toFile(Paths.get("./docs/markdown/generated/all"));
    }
}

```
接着运行类单元测试即可在./docs/处生成文档，不过会缺少html文档
# 方法二
## 添加maven依赖
在pom.xml里

```java
//<plugins>处
	<plugin>
                <groupId>org.asciidoctor</groupId>
                <artifactId>asciidoctor-maven-plugin</artifactId>
                <version>1.5.6</version>
                <configuration>
                    <sourceDirectory>./docs/asciidoc/generated</sourceDirectory>
                    <outputDirectory>./docs/asciidoc/html</outputDirectory>
                    <headerFooter>true</headerFooter>
                    <doctype>book</doctype>
                    <backend>html</backend>
                    <sourceHighlighter>coderay</sourceHighlighter>
                    <attributes>
                        <!--菜单栏在左边-->
                        <toc>left</toc>
                        <!--多标题排列-->
                        <toclevels>3</toclevels>
                        <!--自动打数字序号-->
                        <sectnums>true</sectnums>
                    </attributes>
                </configuration>
    </plugin>
//<dependencies>处
    <dependency>
        <groupId>io.springfox</groupId>
        <artifactId>springfox-staticdocs</artifactId>
        <version>2.6.1</version>
    </dependency>

```
## 配置执行命令
在 IDEA-Run-Edit Configurations...处打开窗口，添加一个maven类配置
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200719141244747.png)
在command-line处写下命令名称
![在这里插入图片描述](https://img-blog.csdnimg.cn/2020071914132143.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDYwMjQwOQ==,size_16,color_FFFFFF,t_70)
然后可以在IDEA右侧Run Configurations
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200719141350104.png)
## 成功截图
大功告成，生成的文档如下
![html页面](https://img-blog.csdnimg.cn/20200719141544302.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDYwMjQwOQ==,size_16,color_FFFFFF,t_70)
# 注意
如果出现不能导入的maven依赖时，到Maven网站上寻找合适的版本替换即可
 [https://mvnrepository.com/](https://mvnrepository.com/)
