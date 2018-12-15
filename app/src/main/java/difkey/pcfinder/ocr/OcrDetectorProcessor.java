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

    private List<String> blist;
    private ArrayList<String> data;
    private List <Integer> iterations;
    private int banque;
    private int compteur;

    OcrDetectorProcessor(GraphicOverlay<OcrGraphic> ocrGraphicOverlay, OcrCaptureActivity ocrActivity) {
        this.ocrActivity = ocrActivity;
        mGraphicOverlay = ocrGraphicOverlay;

        finder = new recherche();


        this.blist = new ArrayList<String>();
        this.iterations = new ArrayList<Integer>();
        this.data = new ArrayList<String>();

        this.banque = 100;
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
                blist =  Arrays.asList((TB.split(" |\\n|\\.|\\#|\\$|\\[|\\]")));
                gestionBanque(blist);
                Log.d("Processor", "Text detected! " + data);
                Log.d("Processor", "Text detected! " + iterations);

            }
        }
    }

    void resetCache(){
        this.blist = new ArrayList<String>();
        this.iterations = new ArrayList<Integer>();
        this.banque = 100;
        this.compteur = 5;
        this.data = new ArrayList<String>();
    }

    private void gestionBanque(List<String> blist) {
        if (banque!=0){
            feed(blist);
        }else {
            if (compteur >0) {
                //evaporation();
                epuration();
                startDbSearch();
                compteur --;
            }
            else{
                ocrActivity.notFinded();
            }
        }
    }

    private void startDbSearch() {
        for (int i = 0; i < data.size(); i++) {
            finder.searchPcforOCR(data.get(i), ocrActivity);
        }
    }

    private void feed(List<String> blist) {
        for (int i = 0; i< blist.size(); i++){
            if (banque>0) {
                if (data.contains(blist.get(i))) {
                    iterations.set(data.indexOf(blist.get(i)), iterations.get(data.indexOf(blist.get(i))) + 1);
                } else {
                    data.add(blist.get(i));
                    iterations.add(1);
                }
                banque--;
            }else break;
        }
    }
    //
    private  void evaporation(){
        int qtevap = 3;
        for (int i = 0; i< iterations.size(); i++){
            banque += (qtevap > iterations.get(i)) ? qtevap : iterations.get(i);
            iterations.set(i, iterations.get(i) - qtevap);
        }
        for(int i = iterations.size() -1; i>=0; i--){
            if(iterations.get(i) <= 0){
                iterations.remove(i);
                data.remove(i);
                i--;
            }
        }
    }

    private void epuration() {
        int min = Collections.min(iterations);
        while (iterations.indexOf(min) !=-1)
        {
            data.remove(iterations.indexOf(min));
            banque=banque+iterations.get(iterations.indexOf(min));
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
