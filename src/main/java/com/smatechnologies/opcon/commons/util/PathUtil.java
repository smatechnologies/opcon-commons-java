package com.smatechnologies.opcon.commons.util;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;


/**
 * Utility that allows get the current jar location or the jar location of specified class
 * 
 * @author Pierre PINON
 */
public class PathUtil {

    public static final String RESOURCE_SEPARATOR = "/";

    /**
     * Get Jar location
     *
     * @return Jar dir Path
     */
    public static Path getJarDir() {
        return getJarDir(null);
    }

    /**
     * Get Jar location of specified class
     *
     * @param clazz Own class if used if null
     * @return Jar Dir Path
     */
    public static Path getJarDir(Class<?> clazz) {
        if (clazz == null) {
            clazz = PathUtil.class;
        }

        try {
            return Paths.get(clazz.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
        } catch (URISyntaxException e) {
            //Should never happen
            return new File(clazz.getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile().toPath();
        }
    }

    public static Path getProgramFiles() {
        return Optional.ofNullable(System.getenv("ProgramFiles")).map(Paths::get).orElse(null);
    }

    public static Path getProgramFilesX86() {
        return Optional.ofNullable(System.getenv("ProgramFiles(X86)")).map(Paths::get).orElse(null);
    }

    public static Path getProgramData() {
        return Optional.ofNullable(System.getenv("ProgramData")).map(Paths::get).orElse(null);
    }

    public static Path getSystemDrive() {
        return Optional.ofNullable(System.getenv("SystemDrive")).map(env -> Paths.get(env + File.separator)).orElse(null);
    }
}
