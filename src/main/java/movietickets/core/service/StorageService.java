package movietickets.core.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * Storage service, used for storing and
 * retrieving files.
 *
 * @author Seregy
 */
public interface StorageService {
    /**
     * Stores file in storage.
     *
     * @param file file to be stored
     * @param name file's name
     * @param folder folder path inside storage
     */
    void store(MultipartFile file, String name, String folder);

    /**
     * Stores file in storage.
     *
     * @param file file to be stored
     * @param name file's name
     * @param folder folder path inside storage
     */
    void store(File file, String name, String folder);

    /**
     * Retrieves file from storage as byte array.
     *
     * @param name file's name
     * @param folder folder path inside storage
     * @return file's bytes
     */
    byte[] loadAsByteArray(String name, String folder);

    /**
     * Retrieves file from storage as {@link File}.
     *
     * @param name file's name
     * @param folder folder path inside storage
     * @return file
     */
    File loadAsFile(String name, String folder);
}
