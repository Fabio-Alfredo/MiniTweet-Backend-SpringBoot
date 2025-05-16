package com.task.minitweet.services.impl;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.task.minitweet.exceptions.HttpError;
import com.task.minitweet.services.contract.CloudinaryService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    /**
     * Este método sube una imagen a Cloudinary.
     * @param file La imagen a subir.
     * @param folder La carpeta donde se guardará la imagen.
     * @return La URL de la imagen subida.
     */
    @Override
    public String uploadImage(MultipartFile file, String folder) {
        try{
            HashMap<Object, Object> options = new HashMap<>();
            options.put("folder", folder);
            Map uploadFile = cloudinary.uploader().upload(file.getBytes(), options);
            String publicId = (String) uploadFile.get("public_id");
            return cloudinary.url().secure(true).generate(publicId);

        }catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("Error uploading image to Cloudinary", e);
        }
    }

    /**
     * Este método elimina una imagen de Cloudinary.
     * @param publicId El ID público de la imagen a eliminar.
     * @return El resultado de la eliminación.
     */
    @Override
    public String deleteImage(String publicId) {
        try {
            Map deleteFile = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            String result = (String) deleteFile.get("result");
            if (!result.equals("ok")) {
                throw new HttpError(HttpStatus.NOT_FOUND, "Error deleting image from Cloudinary");
            }
            return result;
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("Error deleting image from Cloudinary", e);
        }
    }
}
