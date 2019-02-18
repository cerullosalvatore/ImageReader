import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.Buffer;
import java.util.Random;

/**
 * Represents an object of type Image and methods to access private parameters.
 */
public class Immagine {
    private String name;
    private String path;
    private byte[] byteStream;

    /**
     * The constructor of this class requires the path of the directory containing the image and the name of the image
     * @param p the path of the directory where the image is located;
     * @param n the name of the image file including its extension (.jpg or .jpeg)
     * @throws IOException
     */
    public Immagine(String p, String n) throws IOException {
        BufferedImage imageTemp = ImageIO.read(new File(p.concat("/"+n)));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(imageTemp, "jpg", baos);
        this.byteStream = baos.toByteArray();
        this.path = path;
        this.name = name;
    }

    /**
     *
     * @return Returns the ByteStream associated with the image
     */
    public byte[] getByteStream(){
        return getByteStream();
    }

    /**
     * Return the BufferredImmage of the object
     * @return
     * @throws IOException
     */
    public BufferedImage getBufferedImage() throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(this.byteStream);
        BufferedImage imageBuffered = ImageIO.read(bais);
        return imageBuffered;
    }

    /**
     * This function modifies an image. This change consists of a random change of the Blue RGB value.
     * @throws IOException
     */
    public void modificaImmagine() throws IOException {
        int param = new Random().nextInt(255);
        BufferedImage modImage = this.getBufferedImage();
        for(int i = 0 ; i < modImage.getWidth() ; i++){
            for(int j = 0; j < modImage.getHeight() ; j++){
                Color c = new Color(modImage.getRGB(i,j));
                modImage.setRGB(i , j , new Color(c.getRed(), c.getGreen(), param).getRGB());
            }
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(modImage, "jpg", baos);
        this.byteStream = baos.toByteArray();
    }


}
