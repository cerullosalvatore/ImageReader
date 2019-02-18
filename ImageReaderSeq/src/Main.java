import javax.imageio.ImageIO;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.LinkedList;

public class Main {
    public static void main(String args[]){

        long timeStart;         //start time
        long timeFinish;        //finish time
        float timeTotal = 0;    //time total
        int imProc = 0;         //Counter of images processed correctly
        int iErr = 0;           //Counter of images processed with errors


        String pathDir = "/home/salvatore/Scrivania/IMG/Dir7";                //Path della directory da cui caricare le immagini (Non inserire il "/" finale)
        String pathDirOut = "/home/salvatore/Scrivania/IMG/Modificate/"; //Path della directory in cui salvare le immagini  (Inserire il "/" finale)

        File dir = new File(pathDir);

        //Read the contents of the folder and acquire the list of files in jpeg and jpg
        String[] files = dir.list(new FilenameFilter() {
            public boolean accept(File dir, String filename) {
                return (filename.endsWith(".jpg") || filename.endsWith(".jpeg"));
            }});

        if(files == null){
            System.out.println("The selected directory does not contain JPEG files or does not exist.");
        }else{
            System.out.println(files.length + " Images within the directory have been identified.\n---------     Elaboration Start     ---------");
            //We carry out the operations in the order: READING -> OPERATION -> WRITING

            timeStart = System.nanoTime();
            for(int i = 0 ; i < files.length ; i++){
                try {
                    System.out.print("IMAGES " + i + " : ");

                    //Image Loading
                    Immagine imm= new Immagine(pathDir, files[i]);
                    System.out.print("Loaded -> ");

                    //Image Modification
                    imm.modificaImmagine();
                    System.out.print("Modified -> ");

                    //Image writing
                    ImageIO.write(imm.getBufferedImage(), "jpg", new File(pathDirOut.concat(files[i])));
                    System.out.println("Written ;");

                } catch (IOException e) {

                    //In case an error occurs during the processing of an image due to its size the user warns.
                    System.out.println("An error occurred during image processing: " + i + " - File name: " + files[i]);
                    iErr++; //Increase the error counter;

                }catch (OutOfMemoryError ex){

                    //In case an error occurs during the processing of an image due to its size the user warns.
                    System.out.println("An error occurred during image processing: " + i + " . The memory is finished|");
                    iErr++; //Increase the error counter

                }

                if(i==(files.length-1)){

                    timeFinish = System.nanoTime();
                    timeTotal = (float)(timeFinish - timeStart) / 1000000000;

                }
            }

            imProc = files.length - iErr;
            System.out.println("The system has employed " + timeTotal + " seconds to perform " + imProc + "/" + files.length + " images elaborations.");
        }
    }
}
