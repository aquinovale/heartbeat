package br.valeconsultoriati.heartbeat.configurations;

public enum Urls
    {

//        SERVICES("/services");
        SERVICES("/status.php");

        private String value;
//        private static final String BASE_URL_PROD = "http://valeconsultoriati.com";
        private static final String BASE_URL_PROD = "http://192.168.0.11";

        Urls(String value) {
            this.value = value;
        }

        private static String getBaseUrl()
        {
            return BASE_URL_PROD.toString();
        }

        public static String getEndingPoint(Urls location) {
            return getBaseUrl() + location.toString();
        }

        @Override
        public String toString() {
            return this.value;
        }
    }


