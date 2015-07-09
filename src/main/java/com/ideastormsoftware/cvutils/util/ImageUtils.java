package com.ideastormsoftware.cvutils.util;

import com.ideastormsoftware.cvutils.sources.ImageSource;
import com.ideastormsoftware.cvutils.sources.ScaledSource;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import org.imgscalr.Scalr;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public final class ImageUtils {

    public static BufferedImage copy(BufferedImage img) {
        BufferedImage copy = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        copy.getGraphics().drawImage(img, 0, 0, null);
        return copy;
    }

    private static BufferedImage toABGR(BufferedImage image) {
        if (image.getType() == BufferedImage.TYPE_4BYTE_ABGR) {
            return image;
        }
        BufferedImage workImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        workImage.getGraphics().drawImage(image, 0, 0, null);
        return workImage;
    }

    private static BufferedImage toByteGrayscale(BufferedImage image) {
        if (image.getType() == BufferedImage.TYPE_BYTE_GRAY) {
            return image;
        }
        BufferedImage workImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        workImage.getGraphics().drawImage(image, 0, 0, null);
        return workImage;
    }

    public static Mat convertToGrayscaleMat(BufferedImage image) {
        image = toByteGrayscale(image);
        byte[] imageData = (byte[]) image.getRaster().getDataElements(0, 0, image.getWidth(), image.getHeight(), null);
        Mat mat = new Mat(new Size(image.getWidth(), image.getHeight()), CvType.CV_8UC1);
        mat.put(0, 0, imageData);
        return mat;
    }

    public static Mat convertToAbgrMat(BufferedImage image) {
        image = toABGR(image);
        byte[] imageData = (byte[]) image.getRaster().getDataElements(0, 0, image.getWidth(), image.getHeight(), null);
        Mat mat = new Mat(new Size(image.getWidth(), image.getHeight()), CvType.CV_8UC4);
        mat.put(0, 0, imageData);
        return mat;
    }

    public static Mat convertToMat(BufferedImage image) {
        switch (image.getType()) {
            case BufferedImage.TYPE_BYTE_GRAY:
            case BufferedImage.TYPE_USHORT_GRAY:
                return convertToGrayscaleMat(image);
            default:
                return convertToAbgrMat(image);
        }
    }

    public static BufferedImage convertToImage(Mat mat) {
        if (mat == null)
            return emptyImage();
        int bufferedImageType = BufferedImage.TYPE_BYTE_GRAY;
        if (mat.channels() > 1) {
            Mat rgbMat = new Mat();
            Imgproc.cvtColor(mat, rgbMat, Imgproc.COLOR_BGR2RGB);
            bufferedImageType = BufferedImage.TYPE_3BYTE_BGR;
            mat = rgbMat;
        }
        byte[] b = new byte[mat.channels() * mat.cols() * mat.rows()];
        mat.get(0, 0, b); // get all the pixels
        BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), bufferedImageType);
        image.getRaster().setDataElements(0, 0, mat.cols(), mat.rows(), b);
        return image;
    }

    public static Size aspectScaledSize(int sourceWidth, int sourceHeight, int destWidth, int destHeight) {
        double sourceRatio = 1.0 * sourceHeight / sourceWidth;
        double destRatio = 1.0 * destHeight / destWidth;
        if (destRatio > sourceRatio) //dest is narrower than source
        {
            return new Size(destWidth, destWidth * sourceRatio);
        } else {
            return new Size(destHeight / sourceRatio, destHeight);
        }
    }

    public static BufferedImage copyAspectScaled(BufferedImage img, int width, int height) {
        if (img == null) {
            return emptyImage();
        }
        if (img.getWidth() == width && img.getHeight() == height) {
            return img;
        }
        BufferedImage copy = new BufferedImage(width, height, img.getType());
        Graphics2D g = copy.createGraphics();
        drawAspectScaled(g, img, width, height);
        return copy;
    }

    public static BufferedImage emptyImage() {
        return new BufferedImage(320, 240, BufferedImage.TYPE_4BYTE_ABGR);
    }
    
    public static BufferedImage emptyImage(Dimension size)
    {
        return new BufferedImage(size.width, size.height, BufferedImage.TYPE_4BYTE_ABGR);
    }

    public static BufferedImage copyAspectScaled(BufferedImage img, Dimension size) {
        if (img == null)
            return emptyImage(size);
        return Scalr.resize(img, Scalr.Method.BALANCED, size.width, size.height);
//        return copyAspectScaled(img, size.width, size.height);
    }

    public static void drawAspectScaled(Graphics2D g, BufferedImage img, Dimension size) {
        drawAspectScaled(g, img, size.width, size.height);
    }

    public static void drawAspectScaled(Graphics2D g, BufferedImage img, int width, int height) {
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setColor(Color.black);
        g.fillRect(0, 0, width, height);
        Size scaledSize = aspectScaledSize(img.getWidth(), img.getHeight(), width, height);
        Point offset = new Point((width - (int) scaledSize.width) / 2, (height - (int) scaledSize.height) / 2);
        g.drawImage(img, offset.x, offset.y, (int) scaledSize.width, (int) scaledSize.height, null);
    }

    public static ScaledSource scaleSource(final ImageSource source) {
        if (source instanceof ScaledSource) {
            return (ScaledSource) source;
        }
        return (Dimension size) -> copyAspectScaled(source.getCurrentImage(), size);
    }

    private ImageUtils() {
    }

}
