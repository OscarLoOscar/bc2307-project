package com.hkjava.demo.demofinnhubdb.config;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.Info.Builder;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/*
 * Spring Boot Actuator InfoContributor
 * 
 * aim : contributes custom information (such as its name, description, author, and other details)
 * 
 * to the Actuator Info endpoint
 * 
 * The application map contains information about the application itself, such as its name and description. The author map contains information about the application's author, such as their name and
 * bio.
 */
@Component
public class CustomInfoContributor implements InfoContributor {

    @Autowired
    private Environment env;

    private static final Map<String, Object> application = new HashMap<>();
    private static final Map<String, Object> author = new HashMap<>();

    @Override
    public void contribute(Builder builder) {
        application.put("Name", env.getProperty("info.application.name"));
        application.put("Description",
                env.getProperty("info.application.description"));
        author.put("Author Name",
                env.getProperty("info.application.author.name"));
        author.put("Author Bio",
                env.getProperty("info.application.author.bio"));
        application.put("Author", author);
        builder.withDetail("Application", application);

    }

}
/*
 * CustomInfoContributor 類是 Spring Boot Actuator 的 InfoContributor，用於向 Actuator Info 端點貢獻自定義資訊。Actuator Info 端點提供有關正在運行的應用程式的資訊，例如其名稱、描述、作者和其他詳細資訊。
 * 
 * CustomInfoContributor 類在其建構函式中注入 Spring Boot Environment。這允許它存取應用程式的組態屬性。然後，該類使用組態屬性填充兩個映射：
 * 
 * application 映射包含有關應用程式本身的資訊，例如其名稱和描述。 author 映射包含有關應用程式的作者的資訊，例如其姓名和簡介。 CustomInfoContributor 類然後將 application 映射添加到 Actuator Info 端點。當訪問 Actuator Info 端點時，將會顯示此資訊。
 * 
 * 以下是如何使用 CustomInfoContributor 類的示例：
 * 
 * Java
 * 
 * @SpringBootApplication public class DemoApplication {
 * 
 * public static void main(String[] args) { SpringApplication.run(DemoApplication.class, args); }
 * 
 * @Bean public InfoContributor customInfoContributor() { return new CustomInfoContributor(); } } Use code with caution. Learn more
 * 
 * 一旦 CustomInfoContributor 類註冊為 Spring bean，它將自動將其資訊貢獻給 Actuator Info 端點。您可以通過發送 GET 請求到以下 URL 來訪問 Actuator Info 端點：
 * 
 * http://localhost:8080/actuator/info 響應將包含包含應用程式資訊的 JSON 物件，包括由 CustomInfoContributor 類貢獻的資訊。
 * 
 * 以下是 Actuator Info 端點的 JSON 響應的示例：
 * 
 * JSON { "app": { "name": "Demo Application", "description": "A simple Spring Boot application." }, "author": { "name": "John Doe", "bio": "A software engineer." } } Use code with caution. Learn more
 * 
 * CustomInfoContributor 類可用於向 Actuator Info 端點貢獻任何自定義資訊。這對於提供有關應用程式的環境、組態或其他詳細資訊很有用。
 * 
 * 以下是 CustomInfoContributor 類的用途的一些示例：
 * 
 * 提供有關應用程式版本、構建號或其他元資料的資訊。 提供有關應用程式使用的軟體庫或框架的資訊。 提供有關應用程式使用的環境變量的資訊。 提供有關應用程式的其他自定義資訊。 要使用 CustomInfoContributor 類，您需要將其註冊為 Spring bean。您可以通過在 @Bean 注釋中將類的實例作為返回值來執行此操作。
 * 
 * 例如，以下代碼將 CustomInfoContributor 類註冊為 Spring bean：
 * 
 * Java
 * 
 * @SpringBootApplication public class DemoApplication {
 * 
 * public static void main(String[] args) { SpringApplication.run(DemoApplication.class, args); }
 * 
 * @Bean public InfoContributor customInfoContributor() { return new CustomInfoContributor(); } } Use code with caution. Learn more
 * 
 * 一旦 CustomInfoContributor 類註冊為 Spring bean，它將自動將其資訊貢獻給 Actuator Info 端點。
 */
