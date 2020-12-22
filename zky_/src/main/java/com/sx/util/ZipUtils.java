package com.sx.util;

import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * 压缩包工具
 */


public class ZipUtils {

    private ZipUtils() {
    }

    public static void doCompress(String srcFile, String zipFile) throws IOException {
        doCompress(new File(srcFile), new File(zipFile));
    }

    /**
     * 文件压缩
     *
     * @param srcFile 目录或者单个文件
     * @param zipFile 压缩后的ZIP文件
     */
    public static void doCompress(File srcFile, File zipFile) throws IOException {
        ZipOutputStream out = null;
        try {
            out = new ZipOutputStream(new FileOutputStream(zipFile));
            doCompress(srcFile, out);
        } catch (Exception e) {
            throw e;
        } finally {
            out.close();//记得关闭资源
        }
    }

    public static void doCompress(String filelName, ZipOutputStream out) throws IOException {
        doCompress(new File(filelName), out);
    }

    public static void doCompress(File file, ZipOutputStream out) throws IOException {
        doCompress(file, out, "");
    }

    public static void doCompress(File inFile, ZipOutputStream out, String dir) throws IOException {
        if (inFile.isDirectory()) {
            File[] files = inFile.listFiles();
            if (files != null && files.length > 0) {
                for (File file : files) {
                    String name = inFile.getName();
                    if (!"".equals(dir)) {
                        name = dir + "/" + name;
                    }
                    ZipUtils.doCompress(file, out, name);
                }
            }
        } else {
            ZipUtils.doZip(inFile, out, dir);
        }
    }

    public static void doZip(File inFile, ZipOutputStream out, String dir) throws IOException {
        String entryName = null;
        if (!"".equals(dir)) {
            entryName = dir + "/" + inFile.getName();
        } else {
            entryName = inFile.getName();
        }
        ZipEntry entry = new ZipEntry(entryName);
        out.putNextEntry(entry);

        int len = 0;
        byte[] buffer = new byte[1024];
        FileInputStream fis = new FileInputStream(inFile);
        while ((len = fis.read(buffer)) > 0) {
            out.write(buffer, 0, len);
            out.flush();
        }
        out.closeEntry();
        fis.close();
    }


    /**
     * 解压缩
     *
     * @param name    压缩包目录
     * @param newpath 解压到目录
     * @throws Exception
     */
    public static void unZip(String name, String newpath) throws Exception {

        boolean isWindowZip = false;

        FileInputStream fis = new FileInputStream(name);
        ZipInputStream zins = new ZipInputStream(fis);
        try {
            while (zins.getNextEntry() != null) {
                zins.closeEntry();
            }
        } catch (Exception ex) {
            isWindowZip = true;
        } finally {
            zins.close();
            fis.close();
        }
        if (isWindowZip) {
            fis = new FileInputStream(name);
            zins = new ZipInputStream(fis, Charset.forName("GBK"));
        } else {
            fis = new FileInputStream(name);
            zins = new ZipInputStream(fis);
        }

        ZipEntry entry = null;
        while ((entry = zins.getNextEntry()) != null) {
            String entryName = entry.getName();
            String descFileDir = newpath + entryName;
            if (entry.isDirectory()) {
                new File(descFileDir).mkdir();
                continue;
            } else {
                new File(descFileDir).getParentFile().mkdir();
            }
            File file = new File(descFileDir);
            OutputStream os = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = zins.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
            os.close();
            zins.closeEntry();
        }
        zins.close();
        fis.close();
    }

    /**
     * @param srcFile
     * @param destDirPath
     * @throws RuntimeException
     */


    public static void unZipFiles(File srcFile, String destDirPath) throws RuntimeException {
        long start = System.currentTimeMillis();
        // 判断源文件是否存在
        if (!srcFile.exists()) {
            throw new RuntimeException(srcFile.getPath() + "所指文件不存在");
        }
        // 开始解压
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(srcFile);
            Enumeration<?> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                System.out.println("解压" + entry.getName());
                // 如果是文件夹，就创建个文件夹
                if (entry.isDirectory()) {
                    String dirPath = destDirPath + "/" + entry.getName();
                    File dir = new File(dirPath);
                    dir.mkdirs();
                } else {
                    // 如果是文件，就先创建一个文件，然后用io流把内容copy过去
                    File targetFile = new File(destDirPath + "/" + entry.getName());
                    // 保证这个文件的父文件夹必须要存在

                    if (!targetFile.getParentFile().exists()) {

                    }
                    targetFile.createNewFile();
                    // 将压缩文件内容写入到这个文件中
                    InputStream is = zipFile.getInputStream(entry);
                    FileOutputStream fos = new FileOutputStream(targetFile);
                    int len;
                    byte[] buf = new byte[1024];
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    // 关流顺序，先打开的后关闭
                    fos.close();
                    is.close();
                }
            }
            long end = System.currentTimeMillis();
            System.out.println("解压完成，耗时：" + (end - start) + " ms");
        } catch (Exception e) {
            throw new RuntimeException("unzip error from ZipUtils", e);
        } finally {
            if (zipFile != null) {
                try {
                    zipFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void multipartFileToFile(MultipartFile file) throws Exception {

        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }


    }

    public static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 解压带密码的压缩包
     * @param source   原始文件路径
     * @param dest     解压路径
     * @param password 解压文件密码(可以为空)
     */
    public static void  unZip(String source, String dest, String password) {
        try {
            File zipFile = new File(source);
            net.lingala.zip4j.core.ZipFile zFile = new net.lingala.zip4j.core.ZipFile(zipFile); // 首先创建ZipFile指向磁盘上的.zip文件
            zFile.setFileNameCharset("GBK");
            File destDir = new File(dest); // 解压目录
            if (!destDir.exists()) {// 目标目录不存在时，创建该文件夹
                destDir.mkdirs();
            }
            if (zFile.isEncrypted()) {
                zFile.setPassword(password.toCharArray()); // 设置密码
            }
            zFile.extractAll(dest); // 将文件抽出到解压目录(解压)
            List<FileHeader> headerList = zFile.getFileHeaders();
            List<File> extractedFileList = new ArrayList<File>();
            for (FileHeader fileHeader : headerList) {
                if (!fileHeader.isDirectory()) {
                    extractedFileList.add(new File(destDir, fileHeader.getFileName()));
                }
            }
            File[] extractedFiles = new File[extractedFileList.size()];
            extractedFileList.toArray(extractedFiles);
            for (File f : extractedFileList) {
                System.out.println(f.getAbsolutePath() + "文件解压成功!");
            }
        } catch (ZipException e) {
            e.printStackTrace();
        }


    }


}