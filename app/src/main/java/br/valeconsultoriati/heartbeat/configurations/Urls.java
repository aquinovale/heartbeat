package br.valeconsultoriati.heartbeat.configurations;

public class Urls
    {
        private static final String BASE_URL_PROD = "http://heartbeat.valeconsultoriati.com";


        public static String getBaseUrl()
        {
            return BASE_URL_PROD.toString();
        }

        //public static String getEndingPoint(Urls location) {
        //    return getBaseUrl() + location.toString();
       // }

        @Override
        public String toString() {
            return getBaseUrl();
        }
    }


