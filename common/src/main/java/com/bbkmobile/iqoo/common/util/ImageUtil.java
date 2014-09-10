package com.bbkmobile.iqoo.common.util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.MemoryCacheImageInputStream;

import com.sun.imageio.plugins.bmp.BMPImageReader;
import com.sun.imageio.plugins.gif.GIFImageReader;
import com.sun.imageio.plugins.jpeg.JPEGImageReader;
import com.sun.imageio.plugins.png.PNGImageReader;

public class ImageUtil {

    public static String getImageType(String image_path) throws IOException {
        FileInputStream fis = new FileInputStream(image_path);

        int leng = fis.available();
        BufferedInputStream buff = new BufferedInputStream(fis);
        byte[] mapObj = new byte[leng];
        buff.read(mapObj, 0, leng);

        String type = null;
        ByteArrayInputStream bais = null;
        MemoryCacheImageInputStream mcis = null;
        try {
            bais = new ByteArrayInputStream(mapObj);
            mcis = new MemoryCacheImageInputStream(bais);
            Iterator itr = ImageIO.getImageReaders(mcis);
            while (itr.hasNext()) {
                ImageReader reader = (ImageReader) itr.next();
                if (reader instanceof GIFImageReader) {
                    type = "gif";
                } else if (reader instanceof JPEGImageReader) {
                    type = "jpg";
                } else if (reader instanceof PNGImageReader) {
                    type = "png";
                } else if (reader instanceof BMPImageReader) {
                    type = "bmp";
                }
            }
        } finally {
            if (bais != null) {
                try {
                    bais.close();
                } catch (IOException ioe) {
                    throw ioe;
                }
            }

            if (mcis != null) {
                try {
                    mcis.close();
                } catch (IOException ioe) {
                    throw ioe;
                }
            }
        }
        return type;
    }

    /**
     * 对图片进行转换
     * 
     * @param srcPath
     *            源图片路径（绝对路径）
     * @param newPath
     *            新图片路径（绝对路径）
     * @return 保存是否成功
     */
    public static boolean modifyImage(String srcPath, String newPath,
            Integer width, Integer height, String imageType) {
        BufferedImage bufferedImage = null;
        try {
            File of = new File(srcPath);
            if (of.canRead()) {
                bufferedImage = ImageIO.read(of);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        if (bufferedImage != null) {
            bufferedImage = modifyImageSize(bufferedImage, width, height,
                    imageType);
            try {
                ImageIO.write(bufferedImage, imageType, new File(newPath));
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * 处理截图
     */
    public static boolean modifyScreenshot(File file) {
        BufferedImage bufferedImage = null;
        int width;
        int height;
        String filePath = file.getAbsolutePath(); // 截图的绝对路径
        String imageType = "JPG";
        try {
            if (file.canRead()) {
                bufferedImage = ImageIO.read(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (bufferedImage != null) {
            width = bufferedImage.getWidth();
            height = bufferedImage.getHeight();
            if (width > 480 && height > 800) { // 对像素大于480*800的图片压缩，其他的保持原始大小，截图的格式转换为JPG
                width = 480;
                height = 800;
                bufferedImage = modifyImageSize(bufferedImage, width, height,
                        imageType);
            } else {
                bufferedImage = modifyImageSize(bufferedImage, width, height,
                        imageType);
            }
            try {
                File tempFile = new File(filePath + ".tmp"); // 临时图
                ImageIO.write(bufferedImage, imageType, tempFile);
                if (tempFile.length() < file.length()) { // 如果处理后的截图大小比原截图小，就替换原截图，反之，就删除处理后的截图
                    tempFile.renameTo(file);
                } else {
                    tempFile.delete();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private static BufferedImage modifyImageSize(BufferedImage originalImage,
            Integer width, Integer height, String imageType) {
        System.out.println(originalImage.getType());
        int type = BufferedImage.TYPE_INT_RGB;
        if ("JPG".equals(imageType)) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        } else if ("PNG".equals(imageType)) {
            type = BufferedImage.TYPE_4BYTE_ABGR;
        } else if ("GIF".equals(imageType)) {
            type = BufferedImage.TYPE_BYTE_INDEXED;
        } else if ("BMP".equals(imageType)) {
            type = BufferedImage.TYPE_USHORT_565_RGB;
        }
        BufferedImage newImage = new BufferedImage(width, height, type);
        Graphics g = newImage.getGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return newImage;
    }
}
