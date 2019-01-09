package net.menwei.utils;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;

public class FileUtil {
    public FileUtil() {
    }

    public static boolean delAllFile(String folderPath) {
        boolean flag = false;
        File file = new File(folderPath);
        if (!file.exists()) {
            return flag;
        } else if (!file.isDirectory()) {
            return flag;
        } else {
            String[] tempList = file.list();
            File temp = null;

            for (int i = 0; i < tempList.length; ++i) {
                if (folderPath.endsWith(File.separator)) {
                    temp = new File(folderPath + tempList[i]);
                } else {
                    temp = new File(folderPath + File.separator + tempList[i]);
                }

                if (temp.isFile()) {
                    temp.delete();
                }

                if (temp.isDirectory()) {
                    delAllFile(folderPath + "/" + tempList[i]);
                    delFolder(folderPath + "/" + tempList[i]);
                    flag = true;
                }
            }

            return flag;
        }
    }

    public static boolean delFile(String filePath) {
        boolean flag = false;
        File file = new File(filePath);
        if (!file.exists()) {
            return flag;
        } else {
            flag = (new File(filePath)).delete();
            return flag;
        }
    }

    public static boolean delFolder(String folderPath) {
        try {
            delAllFile(folderPath);
            String filePath = folderPath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete();
            return true;
        } catch (Exception var3) {
            var3.printStackTrace();
            return false;
        }
    }

    public static String readFile(String curfile) {
        File f = new File(curfile);

        try {
            if (!f.exists()) {
                throw new Exception();
            } else {
                FileReader cf = new FileReader(curfile);
                BufferedReader is = new BufferedReader(cf);
                String filecontent = "";
                String str = is.readLine();

                while (str != null) {
                    filecontent = filecontent + str;
                    str = is.readLine();
                    if (str != null) {
                        filecontent = filecontent + "\n";
                    }
                }

                is.close();
                cf.close();
                return filecontent;
            }
        } catch (Exception var6) {
            System.err.println("不能读属性文件: " + curfile + " \n" + var6.getMessage());
            return "";
        }
    }

    public static String getFileExt(String filePathName) {
        int pos = filePathName.lastIndexOf('.');
        return pos != -1 ? filePathName.substring(pos + 1, filePathName.length()) : "";
    }

    public static int getFileSize(String filename) {
        try {
            File fl = new File(filename);
            int length = (int) fl.length();
            return length;
        } catch (Exception var3) {
            return 0;
        }
    }

    public static boolean copyFile(String srcPath, String destPath) {
        try {
            File fl = new File(srcPath);
            int length = (int) fl.length();
            FileInputStream is = new FileInputStream(srcPath);
            FileOutputStream os = new FileOutputStream(destPath);
            byte[] b = new byte[length];
            is.read(b);
            os.write(b);
            is.close();
            os.close();
            return true;
        } catch (Exception var7) {
            return false;
        }
    }

    public static boolean copyAllFile(String srcFolderPath, String destFolderPath) {
        boolean flag = false;
        File srcFile = new File(srcFolderPath);
        if (!srcFile.exists()) {
            return flag;
        } else if (!srcFile.isDirectory()) {
            return flag;
        } else {
            File destFile = new File(destFolderPath);
            if (!destFile.isDirectory()) {
                destFile.mkdirs();
            }

            String[] srcList = srcFile.list();
            String tempSrc = null;
            String tempDest = null;

            for (int i = 0; i < srcList.length; ++i) {
                if (srcFolderPath.endsWith(File.separator)) {
                    tempSrc = srcFolderPath + srcList[i];
                    tempDest = destFolderPath + srcList[i];
                } else {
                    tempSrc = srcFolderPath + File.separator + srcList[i];
                    tempDest = destFolderPath + File.separator + srcList[i];
                }

                flag = copyFile(tempSrc, tempDest);
            }

            return flag;
        }
    }
}
