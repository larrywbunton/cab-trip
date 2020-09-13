package com.cabtrip.client;

public class TripClientApp {
    private static final String ARG_CLEAR_CACHE = "-clearcache";
    private static final String ARG_CSV_FILE = "-csvfile";
    private static final String ARG_NO_CACHE = "-nocache";
    private static final String ARG_BASE_URL = "-url";

    /**
     * Cab Trip Client Main.
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        System.out.println("Cab Trip Client");

        if (args.length < 1) {
            usage();
            System.exit(0);
        }

        boolean clearCacheFlag = false;
        boolean useCacheFlag = true;
        String csvFilename = null;
        String baseUrl = "http://localhost:8080";
        boolean baseUrlModifiedFlag = false;

        for (int i = 0; i < args.length; i++) {
            boolean isLastArg = (i == (args.length - 1));

            switch (args[i]) {
                case ARG_CLEAR_CACHE:
                    if (csvFilename != null || clearCacheFlag) {
                        usage();
                    }
                    clearCacheFlag = true;
                    continue;

                case ARG_CSV_FILE:
                    if (isLastArg || csvFilename != null || clearCacheFlag) {
                        usage();
                    }
                    csvFilename = args[++i].trim();
                    if (csvFilename.startsWith("-")) {
                        System.out.println("Error no file specified after " + ARG_CSV_FILE + "\n");
                        usage();
                    }
                    continue;

                case ARG_NO_CACHE:
                    useCacheFlag = false;
                    continue;

                case ARG_BASE_URL:
                    if (isLastArg || !baseUrlModifiedFlag) {
                        usage();
                    }
                    baseUrlModifiedFlag = true;
                    baseUrl = args[++i].trim();
                    continue;

                default:
                    usage();
            }
        }

        TripClient client = new TripClientImpl(baseUrl);

        if (clearCacheFlag) {
            client.clearCache();

        } else {
            client.tripCount(csvFilename, useCacheFlag);
        }
    }

    private static void usage() {
        System.out.println("Usage:");
        System.out.println("java -jar client.jar -clearcache");
        System.out.println("java -jar client.jar -csvfile <csv filename>");
        System.out.println("java -jar client.jar -csvfile <csv filename> -nocache");
        System.out.println("-baseUrl <CabTrip Server baseUtl http://<server.dns>:<port>>");
        System.out.println("Default setting http://localhost:8080");
        System.exit(1);
    }

}
