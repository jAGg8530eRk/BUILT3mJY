// 代码生成时间: 2025-09-21 15:53:38
package com.example.filesync;

import io.micronaut.context.annotation.Requires;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import javax.inject.Singleton;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Requires(env = "sync") // Activate this service only in environments where the 'sync' environment variable is set
@Controller("/api/sync")
@Singleton
public class FileSyncService {

    private static final String SOURCE_DIR = "/path/to/source/directory";
    private static final String TARGET_DIR = "/path/to/target/directory";

    // Synchronizes the source directory with the target directory
    @Get("/sync/{fileName}")
    public HttpResponse<String> syncFile(@PathVariable @NonNull String fileName) {
        try {
            File sourceFile = new File(SOURCE_DIR + File.separator + fileName);
            File targetFile = new File(TARGET_DIR + File.separator + fileName);

            // Check if the source file exists
            if (!sourceFile.exists()) {
                return HttpResponse.badRequest("Source file does not exist.");
            }

            // Create target directory if it does not exist
            File targetDir = targetFile.getParentFile();
            if (!targetDir.exists()) {
                targetDir.mkdirs();
            }

            // Copy file from source to target
            Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return HttpResponse.ok("File synchronized successfully.");
        } catch (IOException e) {
            // Log and handle the error appropriately
            return HttpResponse.serverError("Error synchronizing file: " + e.getMessage());
        }
    }

    // Helper method to copy a file from source to target
    private void copyFile(File sourceFile, File targetFile) throws IOException {
        try (InputStream in = new FileInputStream(sourceFile);
             OutputStream out = new FileOutputStream(targetFile)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        }
    }
}
