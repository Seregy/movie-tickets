package movietickets.core.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

/**
 * Local storage service, uses local file system for storing resources.
 * Root path can be specified with system property 'FILE_UPLOAD_LOCATION'.
 * Default path is relative.
 *
 * @author Seregy
 */
@Service
public class StorageServiceLocal implements StorageService {
    private static final String RESOURCE_FOLDER = "/resources/";

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void store(final MultipartFile file,
                      final String name,
                      final String folder) {
        Path path = Paths.get(getRootPath().toString(),
                folder,
                name);
        File stored = path.toFile();
        if (Files.exists(path) || stored.mkdirs()) {
            try {
                file.transferTo(stored);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void store(final File file,
                      final String name,
                      final String folder) {
        Path path = Paths.get(getRootPath().toString(),
                folder,
                name);
        File stored = path.toFile();
        if (Files.exists(path) || stored.mkdirs()) {
            try {
                Files.copy(file.toPath(),
                        path,
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public byte[] loadAsByteArray(final String name,
                                  final String folder) {
        Path path = Paths.get(getRootPath().toString(), folder, name);
        if (Files.isReadable(path)) {
            try {
                return Files.readAllBytes(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public File loadAsFile(final String name,
                     final String folder) {
        Path path = Paths.get(getRootPath().toString(), folder, name);
        if (Files.isReadable(path)) {
            return path.toFile();
        }
        return null;
    }

    /**
     * Returns root resource path,
     * appending path from system property 'FILE_UPLOAD_LOCATION'
     * (if it's set).
     *
     * @return root path
     */
    private Path getRootPath() {
        if (System.getProperty("FILE_UPLOAD_LOCATION") != null) {
            return Paths.get(System.getProperty("FILE_UPLOAD_LOCATION"),
                    RESOURCE_FOLDER);
        } else {
            return Paths.get(RESOURCE_FOLDER);
        }
    }
}
