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
     * The constructor of this class requires the path of the directory containing the image and the name of the image.
     * @param p the path of the directory in which the image is located;
     * @param n the name of the image file including its extension (.jpg or .jpeg)
     * @throws IOException
     */
    public Immagine(String p, String n) throws IOException {
        BufferedImage imageTemp = ImageIO.read(new File(p.concat("/"+n)));  //Reading the image
        ByteArrayOutputStream baos = new ByteArrayOutputStream();           //Implementation of an ArrayOutputStream to convert the image into a byteStream
        ImageIO.write(imageTemp, "jpg", baos);                  //Writing of the image in the ArrayOutputStream
        this.byteStream = baos.toByteArray();                               //Converting the array to a ByteStream
        this.path = path;       //Set the path of the Image object
        this.name = name;       //Set the name of the Image object
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
        ByteArrayInputStream bais = new ByteArrayInputStream(this.byteStream);  //Realizzo un ByteArrayInputStream su cui leggere
        BufferedImage imageBuffered = ImageIO.read(bais);                       //Leggo la BufferedImage
        return imageBuffered;   //Ritorno il valore
    }

    /**
     * This function modifies an image. This change consists of a random change of the Blue RGB value.
     * @throws IOException
     */
    public void modificaImmagine() throws IOException {
        int param = new Random().nextInt(255);          //Generation of a Random value between 0 and 255 which will replace the Blue value of the RGB channels of all the pixels of the image
        BufferedImage modImage = this.getBufferedImage();      //Implementation of a BufferedImage of support
        for(int i = 0 ; i < modImage.getWidth() ; i++){        //Scanning the entire image column by column from 0 to the width of the image
            for(int j = 0; j < modImage.getHeight() ; j++){    //Scanning the entire image line by line from 0 to the maximum height of the image
                //Within the for loop we can work with the pixel i, j of the image
                Color c = new Color(modImage.getRGB(i,j));     //Making a Color object with the RGB model of the original image pixel
                modImage.setRGB(i , j , new Color(c.getRed(), c.getGreen(), param).getRGB());   //Make the change setting for the pixel i, j the model c but with the parameter Blue = param
            }
        }

        //Saving changes as Byte Stram within the current object.
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(modImage, "jpg", baos);
        this.byteStream = baos.toByteArray();
    }


}
