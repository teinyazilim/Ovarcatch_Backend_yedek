package com.tein.overcatchbackend;

import com.tein.overcatchbackend.property.FileStorageProperties;
import com.tein.overcatchbackend.repository.ModuleTypeRepository;
import com.tein.overcatchbackend.service.CreateMockDataService;
import com.tein.overcatchbackend.service.ReminderService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.FileNotFoundException;

@EnableAsync
@EnableScheduling
@EnableConfigurationProperties({
        FileStorageProperties.class
})
@SpringBootApplication
public class OvercatchBackendApplication implements ApplicationRunner {

    private final CreateMockDataService createMockDataService;
    private final ModuleTypeRepository moduleTypeRepository;
    private final ReminderService reminderService;

    @Value("${filePath.conf}")
    public String filePathConf;

    public String filePathConf() {
        return filePathConf;
    }

    @Value("${file.upload-dir}")
    public String fileUploadDir;

    public String fileUploadDir() {
        return fileUploadDir;
    }

    public OvercatchBackendApplication(CreateMockDataService createMockDataService, ModuleTypeRepository moduleTypeRepository, ReminderService reminderService) {
        this.createMockDataService = createMockDataService;
        this.moduleTypeRepository = moduleTypeRepository;
        this.reminderService = reminderService;
    }

    public static void main(String[] args) {
        SpringApplication.run(OvercatchBackendApplication.class, args);
    }

    // spring boot 2.x
//    @Bean
//    public ServletWebServerFactory servletContainer() {
//        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
//            @Override
//            protected void postProcessContext(Context context) {
//                SecurityConstraint securityConstraint = new SecurityConstraint();
//                securityConstraint.setUserConstraint("CONFIDENTIAL");
//                SecurityCollection collection = new SecurityCollection();
//                collection.addPattern("/*");
//                securityConstraint.addCollection(collection);
//                context.addConstraint(securityConstraint);
//            }
//        };
//        tomcat.addAdditionalTomcatConnectors(redirectConnector());
//        return tomcat;
//    }
//
//    private Connector redirectConnector() {
//        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//        connector.setScheme("http");
//        connector.setPort(8081);
//        connector.setSecure(false);
//        connector.setRedirectPort(8443);
//        return connector;
//    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        System.out.println(filePathConf);
        if (moduleTypeRepository.findAll().size() == 0) {
            System.out.println("Mock Datas Inserting..................");
            System.out.println("Please wait for the process to complete.");
            createMockDataService.createMockData();
            System.out.println("Mock Datas Succesfully Inserted..................");
            System.out.println("Initial Setup Configuration in Progress..................");
        }

        //Reminder modülü tetikleyicileri başlangıç
//        System.out.println("Please wait for the process to complete.");
//        reminderService.soleTraderSelfAsessmenttax();
//        System.out.println("soleTraderSelfAsessmenttax process has been set up");
//        reminderService.LimitedCompwithoutvattax();
//        System.out.println("LimitedCompwithoutvattax process has been set up");
//        reminderService.vatReturn();
//        System.out.println("vatReturn process has been set up");
//        reminderService.AccountsDue();
//        System.out.println("AccountsDue process has been set up");
//        reminderService.taxReturnlimitedwithvat();
//        System.out.println("taxReturnlimitedwithvat process has been set up");
//        reminderService.limitedDirectors();
//        System.out.println("limitedDirectors process has been set up");
//        reminderService.confirmationStatements();
//        System.out.println("confirmationStatements process has been set up");
//        System.out.println("Setup is Succesfly Complete..................");
    }
    //Reminder modülü tetikleyicileri bitişi

   /* @Override implements WebApplicationInitializer
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.addListener(HttpSessionEventPublisher.class);
    }*/
}
