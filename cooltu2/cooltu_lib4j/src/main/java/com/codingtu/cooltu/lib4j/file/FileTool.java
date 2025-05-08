package com.codingtu.cooltu.lib4j.file;

import com.codingtu.cooltu.constant.FileType;
import com.codingtu.cooltu.lib4j.data.data.FileInfo;
import com.codingtu.cooltu.lib4j.file.read.FileReader;
import com.codingtu.cooltu.lib4j.function.OnFilePass;
import com.codingtu.cooltu.lib4j.json.JsonTool;
import com.codingtu.cooltu.lib4j.tool.CountTool;
import com.codingtu.cooltu.lib4j.tool.StringTool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

public class FileTool {

    public static final String SEPARATOR = File.separator;

    public static String addPrexSeparator(String dir) {
        return SEPARATOR + dir;
    }

    /**************************************************
     *
     * 获取项目目录
     *
     **************************************************/
    public static String getProjectDir() {
        return System.getProperty("user.dir");
    }


    public static File getDefaultFile() {
        return new File(getProjectDir(), "files\\files");
    }

    public static File getFileInProject(String path) {
        return new File(getProjectDir(), path);
    }

    /**************************************************
     *
     * 读取
     *
     **************************************************/
    public static BufferedReader getBufferedReader(File file) throws Exception {
        return getBufferedReader(file, "UTF-8");
    }

    public static BufferedWriter getBufferedWriter(File file) throws Exception {
        return getBufferedWriter(file, "UTF-8");
    }

    public static BufferedReader getBufferedReader(File file, String charsetName) throws Exception {
        return new BufferedReader(new InputStreamReader(new FileInputStream(file), charsetName));
    }

    public static BufferedWriter getBufferedWriter(File file, String charsetName) throws Exception {
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charsetName));
    }

    /**************************************************
     *
     * 创建目录
     *
     **************************************************/
    public static void createParentDir(File file) {
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
    }

    public static void createParentDir(String path) {
        createParentDir(new File(path));
    }

    public static void createDir(String path) {
        createDir(new File(path));
    }

    public static void createDir(File dir) {
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**************************************************
     *
     * toFileInfo
     *
     **************************************************/
    public static FileInfo toFileInfo(File file) {
        FileInfo info = new FileInfo();
        info.file = file;
        info.isDir = file.isDirectory();
        info.name = file.getName();
        info.simpleName = info.name;
        if (!info.isDir) {
            int lastIndexOf = info.name.lastIndexOf(".");
            if (lastIndexOf >= 0) {
                info.simpleName = info.name.substring(0, lastIndexOf);
                info.type = info.name.substring(lastIndexOf + 1);
            }
        }
        return info;
    }

    public static String simpleName(File file) {
        String simpleName = file.getName();
        if (!file.isDirectory()) {
            int lastIndexOf = simpleName.lastIndexOf(".");
            if (lastIndexOf >= 0) {
                simpleName = simpleName.substring(0, lastIndexOf);
            }
        }
        return simpleName;
    }

    public static String type(File file) {
        String type = FileType.NONE;
        if (!file.isDirectory()) {
            String fileName = file.getName();
            int lastIndexOf = fileName.lastIndexOf(".");
            if (lastIndexOf >= 0) {
                type = fileName.substring(lastIndexOf + 1);
            }
        } else {
            type = FileType.DIR;
        }
        return type;
    }

    public static String getRename(File file, String oldDirPath, String newDirPath) {
        return newDirPath + file.getAbsolutePath().substring(oldDirPath.length());
    }

    public static File getRenameFile(File file, String oldDirPath, String newDirPath) {
        return new File(getRename(file, oldDirPath, newDirPath));
    }

    /**************************************************
     *
     *
     *
     **************************************************/

    public static class LengthInfo {
        public long length;
        public String size;
        public String unit;

        @Override
        public String toString() {
            return size + " " + unit;
        }
    }

    public static LengthInfo lengthFormat(long length) {
        return lengthFormat(length, 1000, 2, true);
    }

    public static LengthInfo lengthFormat(long length, int divider, int bit, boolean trim) {
        LengthInfo lengthInfo = new LengthInfo();
        lengthInfo.length = length;

        double d = length / 1024d;
        if (d < divider) {
            lengthInfo.size = StringTool.formatDouble(d, bit, trim);
            lengthInfo.unit = "KB";
            return lengthInfo;
        }
        d = d / 1024d;
        if (d < divider) {
            lengthInfo.size = StringTool.formatDouble(d, bit, trim);
            lengthInfo.unit = "MB";
            return lengthInfo;
        }
        d = d / 1024d;
        if (d < divider) {
            lengthInfo.size = StringTool.formatDouble(d, bit, trim);
            lengthInfo.unit = "GB";
            return lengthInfo;
        }

        d = d / 1024d;
        lengthInfo.size = StringTool.formatDouble(d, bit, trim);
        lengthInfo.unit = "TB";
        return lengthInfo;
    }

    public static <T> T readFileToDataObj(Class<T> tClass, File file) {
        return JsonTool.toDataObj(tClass, readFileToStr(file));
    }

    public static <T> List<T> readFileToDataObjs(Class<T> tClass, File file) {
        return JsonTool.toDataObjs(tClass, readFileToStr(file)).toList();
    }

    public static String readFileToStr(File file) {
        return FileReader.from(file).readToStr();
    }


    public static long obtainTotalLength(File file, OnFilePass onFilePass) {
        long len = 0;
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < CountTool.count(files); i++) {
                len += obtainTotalLength(files[i], onFilePass);
            }
        } else {
            if (onFilePass != null && onFilePass.pass(file)) {
                return 0;
            }
            len = file.length();

        }
        return len;
    }
}
