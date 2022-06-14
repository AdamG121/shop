package com.adam.shop.helper;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Component
public class FileHelper {
    public void copy(Path path, InputStream inputStream) throws IOException {
        Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
    }
}
