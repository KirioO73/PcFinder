package difkey.pcfinder.ocr;

import android.util.Log;
import android.util.SparseArray;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import difkey.pcfinder.ocr.camera.GraphicOverlay;
import difkey.pcfinder.db.com.recherche;

/**
 * A very simple Processor which receives detected TextBlocks and adds them to the overlay
 * as OcrGraphics.
 */
public class OcrDetectorProcessor implements Detector.Processor<TextBlock> {

    private GraphicOverlay<OcrGraphic> mGraphicOverlay;
    private OcrCaptureActivity ocrActivity;

    private recherche finder;

    private List<String> detectedString;
    private ArrayList<String> data;
    private List <Integer> iterations;
    private int bank;
    private int compteur;

    OcrDetectorProcessor(GraphicOverlay<OcrGraphic> ocrGraphicOverlay, OcrCaptureActivity ocrActivity) {
        this.ocrActivity = ocrActivity;
        mGraphicOverlay = ocrGraphicOverlay;

        finder = new recherche();


        this.detectedString = new ArrayList<String>();
        this.iterations = new ArrayList<Integer>();
        this.data = new ArrayList<String>();

        this.bank = 100;
        this.compteur = 5;
    }

    /**
     * Called by the detector to deliver detection results.
     * If your application called for it, this could be a place to check for
     * equivalent detections by tracking TextBlocks that are similar in location and content from
     * previous frames, or reduce noise by eliminating TextBlocks that have not persisted through
     * multiple detections.
     */
    @Override
    public void receiveDetections(Detector.Detections<TextBlock> detections) {
        String TB;
        mGraphicOverlay.clear();
        SparseArray<TextBlock> items = detections.getDetectedItems();
        for (int i = 0; i < items.size(); ++i) {
            TextBlock item = items.valueAt(i);
            if (item != null && item.getValue() != null) {
                TB = item.getValue();
                detectedString =  Arrays.asList((TB.split(" |\\n|\\.|\\#|\\$|\\[|\\]")));
                bankProcessing(detectedString);
                Log.d("Processor", "Text detected! " + data);
                Log.d("Processor", "Text detected! " + iterations);

            }
        }
    }

    /**
     * Reset all the data of the algorithm, for restart cleanly
     */
    void resetCache(){
        this.detectedString = new ArrayList<String>();
        this.iterations = new ArrayList<Integer>();
        this.bank = 100;
        this.compteur = 5;
        this.data = new ArrayList<String>();
    }


    /**
     * Add Strings to the bank data if the bank have token to give
     * else, it will clean the bank with an algorithm
     *
     * @param detectedStrings the string to add in the bank
     */
    private void bankProcessing(List<String> detectedStrings) {
        if (bank !=0){
            feed(detectedStrings);
        }else {
            if (compteur >0) {
                //evaporation();
                purification();
                startDbSearch();
                compteur --;
            }
            else{
                ocrActivity.notFinded();
            }
        }
    }

    /**
     * Launch Database research on the relevant data
     */
    private void startDbSearch() {
        for (int i = 0; i < data.size(); i++) {
            finder.searchPcforOCR(data.get(i), ocrActivity);
        }
    }

    /**
     * Add a list of string to the bank data,
     * if bank already knew this string, add an iteration,
     * else add the string in the data
     *
     * @param detectedStrings the string to add in the bank
     */
    private void feed(List<String> detectedStrings) {
        for (int i = 0; i< detectedStrings.size(); i++){
            if (bank >0) {
                if (data.contains(detectedStrings.get(i))) {
                    iterations.set(data.indexOf(detectedStrings.get(i)), iterations.get(data.indexOf(detectedStrings.get(i))) + 1);
                } else {
                    data.add(detectedStrings.get(i));
                    iterations.add(1);
                }
                bank--;
            }else break;
        }
    }

    /**
     * Reduce an amount of iteration of all the data's strings, and put this value back into the bank
     * if it go under 0 it is suppressed from the data
     */
    private  void evaporation(){
        int amountOfEvaporation = 3;
        for (int i = 0; i < iterations.size(); i++){
            bank += (amountOfEvaporation > iterations.get(i)) ? amountOfEvaporation : iterations.get(i);
            iterations.set(i, iterations.get(i) - amountOfEvaporation);
        }
        for(int i = iterations.size() -1; i>=0; i--){
            if(iterations.get(i) <= 0){
                iterations.remove(i);
                data.remove(i);
                i--;
            }
        }
    }


    /**
     * Remove all the string with min iteration from the bank data
     * And put back their iteration value into the bank
     */
    private void purification() {
        int min = Collections.min(iterations);
        while (iterations.indexOf(min) !=-1)
        {
            data.remove(iterations.indexOf(min));
            bank = bank +iterations.get(iterations.indexOf(min));
            iterations.remove(iterations.indexOf(min));
        }
    }



    /**
     * Frees the resources associated with this detection processor.
     */
    @Override
    public void release() {
        mGraphicOverlay.clear();
    }
}
