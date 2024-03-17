package razepl.dev.social365.images.api.interfaces;

import org.springframework.web.multipart.MultipartFile;

@FunctionalInterface
public interface FileManagementService {
    void saveFile(String filePath, MultipartFile file);
}
