package com.example.spring.integration.sftp.config;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import java.io.File;

@MessagingGateway
public interface SFTPFileUploadGateWay {

    @Gateway(requestChannel = "uploadFile")
    public void uploadFile(File file);
}
