// 代码生成时间: 2025-09-17 15:35:02
package com.example.organizer;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Factory
public class FolderOrganizer {

    /**
     * 根据文件夹路径和目标路径进行文件结构整理。
     *
     * @param sourcePath 源文件夹路径
     * @param targetPath 目标文件夹路径
     * @return boolean 整理成功返回true，否则返回false。
     * @throws IOException 如果发生IO异常
     */
    public boolean organize(String sourcePath, String targetPath) throws IOException {
        Path source = Paths.get(sourcePath);
        Path target = Paths.get(targetPath);

        // 检查源文件夹是否存在
        if (!Files.isDirectory(source)) {
            throw new IllegalArgumentException("Source path is not a directory: " + sourcePath);
        }

        // 检查目标文件夹是否存在，如果不存在则创建
        if (!Files.exists(target)) {
            Files.createDirectories(target);
        }

        try (Stream<Path> files = Files.walk(source)) {
            for (Path file : files.filter(Files::isRegularFile)) {
                Path relativePath = source.relativize(file);
                Path targetFile = target.resolve(relativePath);
                Path targetDir = targetFile.getParent();

                // 创建目标文件夹
                Files.createDirectories(targetDir);
                // 移动文件
                Files.move(file, targetFile);
            }
        }

        return true;
    }
}
