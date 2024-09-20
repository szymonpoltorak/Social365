package razepl.dev.social365.images.api.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface FileManagementService {

    void saveFile(Path filePath, MultipartFile file);

    void deleteFile(Path imagePath);

    void createProfileFolder(String username);

}
