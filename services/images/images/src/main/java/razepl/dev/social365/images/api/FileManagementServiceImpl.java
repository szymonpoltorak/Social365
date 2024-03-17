package razepl.dev.social365.images.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import razepl.dev.social365.images.api.interfaces.FileManagementService;

@Slf4j
@Service
public class FileManagementServiceImpl implements FileManagementService {
    @Override
    public final void saveFile(String filePath, MultipartFile file) {

    }
}
