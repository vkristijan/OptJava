package hr.fer.zemris.art;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class ThreadLocalImageProvider implements IImgProvider {
    private ThreadLocal<GrayScaleImage> threadLocal = new ThreadLocal<>();

    private int width;
    private int height;

    public ThreadLocalImageProvider(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public GrayScaleImage getImage() {
        GrayScaleImage img = threadLocal.get();

        if (img == null){
            img = new GrayScaleImage(width, height);
        }

        return img;
    }
}
