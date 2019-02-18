import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * This class implements Runnable and can be used to create a Thread that performs the 3 processing operations of an Image object.
 */
public class RemoveTask implements Runnable{
    private ConcurrentLinkedDeque<String> immagini;     //We use a ConcurrentLinkedQueue to represent our image list
    private String path;                                //The path in which the images are contained
    private String pathDirOut;
    /**
     * The constructor of RemoveTask to which we must pass a CuncurrentLinkedQueue and the path of the images
     */
    public RemoveTask(ConcurrentLinkedDeque<String> immagini, String pathDir, String pathDirOut){
        this.immagini = immagini;
        this.path = pathDir;
        this.pathDirOut = pathDirOut;
    }

    /**
     * Override of the Runnable Interface Run method in which to insert the instructions that each thread must execute
     */
    @Override
    public void run() {
        while(!immagini.isEmpty()){
            String name = immagini.pollFirst();

            try {
                //Image Loading
                Immagine imm= new Immagine(path, name);
                System.out.println(Thread.currentThread().getName() + " - "  + "\tLoaded \t- " + "Image: " + name);

                //Image transformation
                imm.modificaImmagine();
                System.out.println(Thread.currentThread().getName() + " - " + "\tModified - " + "Image: " + name);

                //Image writing
                ImageIO.write(imm.getBufferedImage(), "jpg", new File(this.pathDirOut.concat(name)));
                System.out.println(Thread.currentThread().getName() + " - " + "\tWritten \t- " + "Image: " + name);

            } catch (IOException e) {
                //e.printStackTrace();
                System.out.println(Thread.currentThread().getName() + " - An error occurred during image processing: " + name );
                Main.iErr.incrementAndGet(); //If an error occurs, 1 is added to the error counter
            }catch (OutOfMemoryError ex){
                //In case an error occurs during the processing of an image due to its size the user warns.
                System.out.println(Thread.currentThread().getName() + " - Error during the elaboration of the immaigne " + name + " . The memory is finished|");
                Main.iErr.incrementAndGet(); //If an error occurs, 1 is added to the error counter
            }catch (NullPointerException e){
                System.out.println(e);
            }
            Main.imProc.incrementAndGet(); //At the end of the image processing (even if with error) I add 1 to the counter of the processed images
        }
    }
}
