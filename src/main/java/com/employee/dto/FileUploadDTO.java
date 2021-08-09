package com.employee.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class FileUploadDTO {
    private String fileMeta;

    private MultipartFile file;

}
