package razepl.dev.social365.images.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import razepl.dev.social365.images.api.interfaces.FileManagementService;
import razepl.dev.social365.images.exceptions.FileCouldNotBeCreatedException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Service
public class FileManagementServiceImpl implements FileManagementService {

    public static final String DEFAULT_USER_MAIL = "nouser@example.com";

    @Override
    public final void saveFile(Path filePath, MultipartFile file) {
        try {
            File out = new File(filePath.toString());
            File parentDir = out.getParentFile();

            log.info("Out file: {}, Parent dir: {}", out, parentDir);

            if (!parentDir.exists()) {
                boolean isDirectoryCreated = parentDir.mkdirs();

                if (!isDirectoryCreated && !parentDir.exists()) {
                    throw new IOException("Failed to create directory: %s".formatted(parentDir.getAbsolutePath()));
                }
            }
            file.transferTo(out);

        } catch (IOException exception) {
            log.error(exception.getLocalizedMessage());

            throw new FileCouldNotBeCreatedException(String.format("File on path '%s' was not able to be created!/", filePath));
        }
    }

    @Override
    public final void deleteFile(Path imagePath) {
        try {
            String imagePathString = imagePath.toString();

            if (imagePathString.contains(DEFAULT_USER_MAIL)) {
                return;
            }
            File file = new File(imagePathString);

            Files.delete(file.toPath());

        } catch (IOException exception) {
            log.error(exception.getLocalizedMessage());

            throw new FileCouldNotBeCreatedException(String.format("File on path '%s' was not able to be deleted!", imagePath));
        }
    }

    @Override
    public final void createProfileFolder(String username) {
        Path profileFolderPath = Path.of(ImagesServiceImpl.IMAGE_VOLUME_PATH, username);

        log.info("Creating profile folder for user: {}", username);

        if (Files.exists(profileFolderPath)) {
            return;
        }
        try {
            Files.createDirectory(profileFolderPath);
        } catch (IOException exception) {
            log.error(exception.getLocalizedMessage());

            throw new FileCouldNotBeCreatedException(String.format("Profile folder for user '%s' was not able to be created!", username));
        }
    }

}
