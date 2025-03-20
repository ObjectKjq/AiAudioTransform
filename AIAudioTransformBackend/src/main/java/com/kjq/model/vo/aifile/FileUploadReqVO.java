package com.kjq.model.vo.aifile;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


@Data
public class FileUploadReqVO {

    private MultipartFile file;

}
