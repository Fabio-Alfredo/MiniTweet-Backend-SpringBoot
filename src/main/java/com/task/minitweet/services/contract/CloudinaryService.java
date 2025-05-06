package com.task.minitweet.services.contract;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
    String uploadImage(MultipartFile file, String folder);
    String deleteImage(String publicId);
}
