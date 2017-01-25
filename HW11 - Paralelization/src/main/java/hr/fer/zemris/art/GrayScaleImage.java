package hr.fer.zemris.art;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class GrayScaleImage {
    public static int SHADES_OF_GREY = 256;

    private int width;
    private int height;
    private byte[] data;

    public GrayScaleImage(int width, int height) {
        this.width = width;
        this.height = height;
        this.data = new byte[height * width];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void clear(byte color) {
        int index = 0;
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                data[index] = color;
                index++;
            }
        }
    }

    public byte[] getData() {
        return data;
    }

    public void rectangle(int x, int y, int w, int h, byte color) {
        int xs = x;
        int xe = x + w - 1;
        int ys = y;
        int ye = y + h - 1;
        // Ako sigurno ne crtam:
        if (width <= xs || height <= ys || xe < 0 || ye < 0) return;
        if (xs < 0) xs = 0;
        if (ys < 0) ys = 0;
        if (xe >= width) xe = width - 1;
        if (ye >= height) ye = height - 1;
        for (int yl = ys; yl <= ye; yl++) {
            int index = yl * width + xs;
            for (int xl = xs; xl <= xe; xl++) {
                data[index] = color;
                index++;
            }
        }
    }

    public void save(File file) throws IOException {
        BufferedImage bim = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        int[] buf = new int[1];
        WritableRaster r = bim.getRaster();
        int index = 0;
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                buf[0] = (int) data[index] & 0xFF;
                r.setPixel(w, h, buf);
                index++;
            }
        }
        try {
            ImageIO.write(bim, "png", file);
        } catch (IOException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new IOException(ex);
        }
    }

    public static GrayScaleImage load(File file) throws IOException {
        BufferedImage bim = ImageIO.read(file);
        if (bim.getType() != BufferedImage.TYPE_BYTE_GRAY) {
            throw new IOException("Slika nije grayscale.");
        }
        GrayScaleImage im = new GrayScaleImage(bim.getWidth(), bim.getHeight());
        try {
            int[] buf = new int[1];
            WritableRaster r = bim.getRaster();
            int index = 0;
            for (int h = 0; h < im.height; h++) {
                for (int w = 0; w < im.width; w++) {
                    r.getPixel(w, h, buf);
                    im.data[index] = (byte) buf[0];
                    index++;
                }
            }
        } catch (Exception ex) {
            throw new IOException("Slika nije grayscale.");
        }
        return im;
    }

}
