import javax.imageio.ImageIO;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


public class Main {
    static AtomicInteger imProc = new AtomicInteger(0);          //Counter of images processed correctly
    static AtomicInteger iErr = new AtomicInteger(0);            //Counter of images processed with errors

    public static void main(String args[]){
        long timeStart;                     //start time
        long timeFinish;                    //finish time
        float timeTotal = 0;                //time total

        String pathDir = "/home/salvatore/Scrivania/IMG/Dir1";                   //Reading directory path
        String pathDirOut = "/home/salvatore/Scrivania/IMG/Modificate/";    //Write directory path

        //Read the contents of the folder and acquire the list of files in jpeg and jpg

        File dir = new File(pathDir);
        String[] files = dir.list(new FilenameFilter() {
            public boolean accept(File dir, String filename) {
                return (filename.endsWith(".jpg") || filename.endsWith(".jpeg"));
            }});

        if(files.length == 0 || files==null){
            System.out.println("The selected directory does not contain JPEG files or does not exist.");
        }else{
            System.out.println(files.length + " Images within the directory have been identified.");
            ConcurrentLinkedDeque<String> immagini = new ConcurrentLinkedDeque<String>();

            for(int i = 0; i < files.length; i++){
                immagini.push(files[i]);
            }

            timeStart = System.nanoTime();  //Acquisition of image processing start time
            int numT = Runtime.getRuntime().availableProcessors();  //Acquisition of the number of available processors
            Thread[] threads = new Thread[numT];                    //Initialization of a set of threads of the numT dimension


            System.out.println("-----   Processing started with " + numT + " Threads   -----");
            //Avvio i Thread
            for(int i = 0 ; i < numT ; i++){
                threads[i] = new Thread(new RemoveTask(immagini, pathDir, pathDirOut));
                threads[i].start();
            }

            //Make sure all threads complete the processing
            try {
                for(int i = 0 ; i < numT; i++){
                    threads[i].join();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            timeFinish = (System.nanoTime());
            timeTotal = (float)(timeFinish - timeStart) / 1000000000;
            System.out.println("The time taken to process " + imProc + " / " + files.length + " images is: " + timeTotal + "seconds. Verified errors: " + iErr.get());

        }
    }
}
