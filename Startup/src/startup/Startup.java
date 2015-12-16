package startup;

import java.io.IOException;

/**
 * Startup klasse om meerdere banken / balies / GUI's tegelijk op te starten,
 * ieder in een aparte JVM.
 */
public class Startup {

    public static void main(String[] args) throws IOException, InterruptedException {

        Runtime.getRuntime().exec("java -jar ../CentraleBank/dist/centralebank.jar");

        Runtime.getRuntime().exec("java -jar ../Bank/dist/bank.jar \"ING Bank\" INGB 1099");
        //Runtime.getRuntime().exec("java -jar ../Bank/dist/bank.jar \"RABO Bank\" RABO 2099");

        Runtime.getRuntime().exec("java -jar ../Balie/dist/balie.jar \"ING Bank\" INGB 1098");
        //Runtime.getRuntime().exec("java -jar ../Balie/dist/balie.jar \"RABO Bank\" RABO 2098");

        Thread.sleep(2000);
        Runtime.getRuntime().exec("java -jar ../GUI/dist/gui.jar \"ING Bank\" INGB 1098");
        //Runtime.getRuntime().exec("java -jar ../GUI/dist/gui.jar \"RABO Bank\" RABO 2098");

    }

}
