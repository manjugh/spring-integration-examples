package com.example.spring.integration.sftp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.sftp.outbound.SftpMessageHandler;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.messaging.MessageHandler;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

@Configuration
public class SFTPConfig {

    @Bean
    public DefaultSftpSessionFactory sftpSessionFactory() {
        File file = null;
        try {
            file = ResourceUtils.getFile("C:\\Users\\xxhanumm\\OneDrive - Tietoevry\\Desktop\\ftp-keys\\original\\id_rsa");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Resource ppkFile = new FileSystemResource(file);
        DefaultSftpSessionFactory sftpSessionFactory = new DefaultSftpSessionFactory();
        sftpSessionFactory.setHost("10.246.89.83");
        sftpSessionFactory.setPort(22);
        sftpSessionFactory.setUser("ctpd2ftp");
        //sftpSessionFactory.setPassword("password");
        sftpSessionFactory.setAllowUnknownKeys(true);
        sftpSessionFactory.setPrivateKey(ppkFile);
       // sftpSessionFactory.setPrivateKeyPassphrase("core");
        return sftpSessionFactory;
    }

    @Bean
    @ServiceActivator(inputChannel = "uploadFile")
    MessageHandler uploadHanlder() {
        SftpMessageHandler sftpMessageHandler = new SftpMessageHandler(sftpSessionFactory());
        sftpMessageHandler.setRemoteDirectoryExpression(new LiteralExpression("/receive"));
        sftpMessageHandler.setFileNameGenerator(message -> {
            File file = (File) message.getPayload();
            return file.getName();
        });
        return sftpMessageHandler;
    }
}
