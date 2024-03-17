package razepl.dev.social365.images.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import razepl.dev.social365.images.api.interfaces.FileManagementService;
import razepl.dev.social365.images.exceptions.FileCouldNotBeCreatedException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Slf4j
@Service
public class FileManagementServiceImpl implements FileManagementService {

    @Override
    public final void saveFile(String filePath, MultipartFile file) {
        try {
            File out = new File(filePath);

            file.transferTo(out);
        } catch (IOException exception) {
            log.error(exception.getLocalizedMessage());

            throw new FileCouldNotBeCreatedException(String.format("File on path '%s' was not able to be created!/", filePath));
        }
    }

    @Override
    public final void deleteFile(String imagePath) {
        try {
            File file = new File(imagePath);

            Files.delete(file.toPath());
        } catch (IOException exception) {
            log.error(exception.getLocalizedMessage());

            throw new FileCouldNotBeCreatedException(String.format("File on path '%s' was not able to be deleted!", imagePath));
        }
    }
}
