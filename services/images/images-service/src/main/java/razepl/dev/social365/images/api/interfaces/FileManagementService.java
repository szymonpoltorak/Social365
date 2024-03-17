package razepl.dev.social365.images.api.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface FileManagementService {
    void saveFile(String filePath, MultipartFile file);

    void deleteFile(String imagePath);
}
