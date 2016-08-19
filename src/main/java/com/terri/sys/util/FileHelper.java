package com.terri.sys.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * 操作文件的帮助类<br/>
 * @createdate 2007-12-06
 */
public class FileHelper {

    static final int BUFFER = 2048;

    /**
     * 根据指定的相对于上下文的文件相对路径找出绝对路径
     *
     * @param contextPath
     *            相对于应用系统的上下文路径
     * @return String 绝对路径
     */
    public static String getFileRealPath(String contextPath) {
        // contextPath = contextPath.replaceAll("(/)|(\\\\)",
        // File.separator);//不可用这个方法替换，会报
        // java.lang.StringIndexOutOfBoundsException: String index out of range:
        // 1。因为会循环替换
        String[] aa = contextPath.split("(/)|(\\\\)");
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < aa.length; i++) {
            sb.append(aa[i]);
            if (i < aa.length - 1)
                sb.append(File.separator);
        }
        if (!sb.toString().startsWith(File.separator))
            sb.append(File.separator + sb.toString());
        return SpringHelper.getServletContext().getRealPath(File.separator)
                + sb.toString();
    }

    /**
     * copy文件
     * @param source
     * @param dist
     * @throws Exception
     */
    public static void copy(File source,File dist) throws Exception{
        InputStream in = new FileInputStream(source);
        BufferedInputStream bin = new BufferedInputStream(in,BUFFER);

        OutputStream out = new FileOutputStream(dist);
        BufferedOutputStream bout = new BufferedOutputStream(out,BUFFER);

        byte[] buf = new byte[1024];
        int readLen = -1;
        while ((readLen = bin.read(buf, 0, 1024)) != -1) {
            bout.write(buf, 0, readLen);
        }
        bout.close();
        out.close();
        bin.close();
        in.close();
    }

    /**
     * 根据文件路径和内容创建文件
     *
     * @param filePath
     * @param content
     * @throws Exception
     */
    public static void createFile(String filePath, String content)
            throws Exception {
        File file = new File(filePath);
        OutputStream out = new FileOutputStream(file);
        PrintWriter writer = new PrintWriter(out);
        writer.println(content);
        writer.close();
    }

    /**
     * 新建目录
     *
     * @param folderPath
     *            目录
     * @return 返回目录创建后的路径
     */
    public static String createFolder(String folderPath) {
        String txt = folderPath;
        try {
            java.io.File myFilePath = new java.io.File(txt);
            txt = folderPath;
            if (!myFilePath.exists()) {
                myFilePath.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return txt;
    }

    /**
     * 多级目录创建
     *
     * @param folderPath
     *            准备要在本级目录下创建新目录的目录路径 例如 c:/myf
     * @param paths
     *            无限级目录参数
     * @return 返回创建文件后的路径 例如 c:/myfa/c
     */
    public static String createFolders(String folderPath, String paths) {
        String txts = folderPath;
        try {
            String txt;
            txts = folderPath;
            StringTokenizer st = new StringTokenizer(paths, File.separator);
            for (int i = 0; st.hasMoreTokens(); i++) {
                txt = st.nextToken().trim();
                if (txts.lastIndexOf("/") != -1) {
                    txts = createFolder(txts + txt);
                } else {
                    txts = createFolder(txts + txt + "/");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return txts;
    }

    /**
     * 以指定编码格式读取指定的文件
     *
     * @param filePath
     * @param charset
     * @return
     */
    public static String readFile(InputStream in, String charset) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(in, charset));
            StringBuffer sb = new StringBuffer("");
            String data = null;
            while ((data = br.readLine()) != null) {
                sb.append(data.trim()+"\n");
            }

            String str = sb.toString().trim();
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException e) {
            }
        }
        return "";
    }

    /**
     * 判断指定的文件是否存在
     *
     * @param filePath
     * @return
     */
    public static boolean isExist(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public static String savePicture(String filePath, String name,
            InputStream stream) {
        if (isExist(filePath + name)) {
            String fileName = String.valueOf(System.currentTimeMillis());
            String str = name.substring(name.lastIndexOf("."));
            name = fileName + str;
        }
        OutputStream bos = null;
        try {
            bos = new FileOutputStream(filePath + name);// 建立一个上传文件的输出流
            int length = 0;
            byte[] buffer = new byte[8192];
            while ((length = stream.read(buffer, 0, 8192)) != -1) {
                bos.write(buffer, 0, length);// 将文件写入服务器
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return name;
    }

    /**
     * 删除文件夹
     * @param folderPath 文件夹完整绝对路径
     */
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); // 删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); // 删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  删除指定文件夹下所有文件
     * @param path 文件夹完整绝对路径
     * @return
     */
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);// 再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 删除文件
     * @param folderPath 文件夹完整绝对路径
     */
    public static void delFile(String folderPath) {
        try {
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            if(myFilePath.isFile())
                myFilePath.delete(); // 删除文件
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 复制文件、文件夹
     * @param sourceFile源文件或文件夹
     * @param replaPath替换路径
     * @param intiPathOut源文件夹初始化路径（用来替换整个文件夹时截取文件夹里面的路径）
     * @throws IOException
     */
    public static void replaFile(String sourceFile, String replaPath)
            throws IOException {
        // 原文件地址
/*        File oldFile = new File(sourceFile);
        if(!oldFile.exists()){
            System.out.println("源文件"+sourceFile+"不存在");
            return;
        }
        if(!oldFile.isDirectory()){
            // new一个新文件夹
            File fnewpath = new File(replaPath);
            // 判断文件夹是否存在
            if (!fnewpath.exists())
                fnewpath.mkdirs();
            // 将文件移到新文件里
            File fnew = new File(replaPath + File.separator+ oldFile.getName());
            oldFile.renameTo(fnew);
        }else{
            //先打包
            ZipHelper.createZip(sourceFile, sourceFile+"_.jar");
            ZipHelper.releaseZipToFile(sourceFile+"_.jar", replaPath+oldFile.getName());// 替换到目标文件夹
        }*/

    }
    public static void main(String[] args) throws IOException{
        FileHelper cp = new FileHelper();
        cp.replaFile("D:/temp","D:/11111111");
        System.out.println ("复制完毕");
    }

}
