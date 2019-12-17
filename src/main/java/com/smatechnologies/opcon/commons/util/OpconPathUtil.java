package com.smatechnologies.opcon.commons.util;

import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;


/**
 * @author Pierre PINON
 */
public class OpconPathUtil {

    private final static String GLOBAL_OPCON_DIRECTORY = "OpConXps";

    private OpconPathUtil() {
    }

    public static Path getVarDirectoryPath(Path currentDirPath) {
        Objects.requireNonNull(currentDirPath, "CurrentDirPath cannot be null");

        //Workaround because Core doesn't get the real "Program Files" directories but "[SysDrive]://Program Files" and "[SysDrive]://Program Files (x86)"

//        OSInfo osInfo = new OSInfo(PathUtil.getSystemDrive(), PathUtil.getProgramFiles(), PathUtil.getProgramFilesX86(), PathUtil.getProgramData());
        Path systemDrive = PathUtil.getSystemDrive();
        Path programFiles = Optional.ofNullable(systemDrive).map(path -> path.resolve("Program Files")).orElse(null);
        Path programFilesX86 = Optional.ofNullable(systemDrive).map(path -> path.resolve("Program Files (x86)")).orElse(null);

        OSInfo osInfo = new OSInfo(systemDrive, programFiles, programFilesX86, PathUtil.getProgramData());

        return getVarDirectoryPath(currentDirPath, osInfo);
    }

    public static Path getVarDirectoryPath(Path currentDirPath, OSInfo osInfo) {
        Objects.requireNonNull(currentDirPath, "CurrentDirPath cannot be null");

        currentDirPath = currentDirPath.toAbsolutePath().normalize();

        //If not Windows, Var Directory is the same
        if (osInfo == null || osInfo.getSystemDrivePath() == null || osInfo.getProgramFilesPath() == null || osInfo.getProgramDataPath() == null) {
            return currentDirPath;
        } else {
            if (currentDirPath.startsWith(osInfo.getProgramFilesPath().resolve(GLOBAL_OPCON_DIRECTORY))) {

                Path pathPart = osInfo.getProgramFilesPath().resolve(GLOBAL_OPCON_DIRECTORY).relativize(currentDirPath);
                return osInfo.getProgramDataPath().resolve(GLOBAL_OPCON_DIRECTORY).resolve(pathPart);
            }
            if (currentDirPath.startsWith(osInfo.getProgramFilesX86Path().resolve(GLOBAL_OPCON_DIRECTORY))) {

                Path pathPart = osInfo.getProgramFilesX86Path().resolve(GLOBAL_OPCON_DIRECTORY).relativize(currentDirPath);
                return osInfo.getProgramDataPath().resolve(GLOBAL_OPCON_DIRECTORY).resolve(pathPart);
            }
            if (currentDirPath.startsWith(osInfo.getProgramFilesPath()) || currentDirPath.startsWith(osInfo.getProgramFilesX86Path())) {

                Path pathPart = currentDirPath.getRoot().relativize(currentDirPath);
                return osInfo.getProgramDataPath().resolve(GLOBAL_OPCON_DIRECTORY).resolve(pathPart);
            }
            if (currentDirPath.startsWith(osInfo.getSystemDrivePath().resolve(GLOBAL_OPCON_DIRECTORY))) {

                Path pathPart = osInfo.getSystemDrivePath().resolve(GLOBAL_OPCON_DIRECTORY).relativize(currentDirPath);
                return osInfo.getProgramDataPath().resolve(GLOBAL_OPCON_DIRECTORY).resolve(pathPart);
            }

            if (currentDirPath.getRoot().equals(osInfo.getSystemDrivePath())) {
                return osInfo.getProgramDataPath().resolve(GLOBAL_OPCON_DIRECTORY).resolve(osInfo.getSystemDrivePath().relativize(currentDirPath));
            } else { //If not in the System Drive, Var Directory is the same
                return currentDirPath;
            }
        }
    }

    public static class OSInfo {
        private final Path systemDrivePath;
        private final Path programFilesPath;
        private final Path programFilesX86Path;
        private final Path programDataPath;

        public OSInfo(Path systemDrivePath, Path programFilesPath, Path programFilesX86Path, Path programDataPath) {
            this.systemDrivePath = Optional.ofNullable(systemDrivePath).map(Path::normalize).orElse(null);
            this.programFilesPath = Optional.ofNullable(programFilesPath).map(Path::normalize).orElse(null);
            this.programFilesX86Path = Optional.ofNullable(programFilesX86Path).map(Path::normalize).orElse(null);
            this.programDataPath = Optional.ofNullable(programDataPath).map(Path::normalize).orElse(null);
        }

        public Path getSystemDrivePath() {
            return systemDrivePath;
        }

        public Path getProgramFilesPath() {
            return programFilesPath;
        }

        public Path getProgramFilesX86Path() {
            return programFilesX86Path;
        }

        public Path getProgramDataPath() {
            return programDataPath;
        }
    }
}
