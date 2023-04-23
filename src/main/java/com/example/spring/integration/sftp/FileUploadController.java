package com.example.spring.integration.sftp;

import com.example.spring.integration.sftp.config.SFTPFileUploadGateWay;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
@AllArgsConstructor
public class FileUploadController {


    private final SFTPFileUploadGateWay sftpFileUploadGateWay;

    @GetMapping("/upload")
    public String uploadGreeting(@RequestParam("greeting") String greeeting) throws IOException {
        String content = "Hello Greeting received " + greeeting;
        File file = new File("/tmp/myfile.txt");
        FileUtils.writeStringToFile(file, content, StandardCharsets.UTF_8);
        sftpFileUploadGateWay.uploadFile(file);

        return content;
    }
}
