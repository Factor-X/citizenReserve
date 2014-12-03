package eu.factorx.citizens.util.security;

import java.util.*;

/**
 * Created by florian on 2/12/14.
 */
public class LoginAttemptManager {

    private final static int MAX_ATTEMPT = 5;
    //in millisecond
    private final static long DURATION = 10 * 1000;

    private static final HashMap<String, Attempts> attemptByAccountMap = new HashMap<>();

    private static final HashMap<String, Attempts> attemptByIpMap = new HashMap<>();


    public static void failedAttemptLogin(String email, String ip) {
        cleanMaps();

        if (attemptByAccountMap.containsKey(email)) {
            attemptByAccountMap.get(email).increment();
        } else {
            attemptByAccountMap.put(email, new Attempts());
        }

        if (attemptByIpMap.containsKey(ip)) {
            attemptByIpMap.get(ip).increment();
        } else {
            attemptByIpMap.put(ip, new Attempts());
        }
    }

    public static boolean tooManyAttempts(String email, String ip) {
        if ((attemptByAccountMap.containsKey(email) && attemptByAccountMap.get(email).tooManyAttempt()) ||
                (attemptByAccountMap.containsKey(ip) && attemptByAccountMap.get(ip).tooManyAttempt())) {
            return true;
        }
        return false;
    }

    private static class Attempts {

        private Integer attemptNb;
        private Date lastAttempt;

        public Attempts() {
            attemptNb = 1;
            lastAttempt = new Date();
        }

        public void increment() {
            if (new Date().getTime() > (lastAttempt.getTime() + DURATION)) {
                attemptNb = 1;
            } else {
                attemptNb++;
            }
            lastAttempt = new Date();
        }

        public Integer getAttemptNb() {
            return attemptNb;
        }

        public Date getLastAttempt() {
            return lastAttempt;
        }

        @Override
        public String toString() {
            return "Attempts{" +
                    "attemptNb=" + attemptNb +
                    ", lastAttempt=" + lastAttempt +
                    '}';
        }

        public boolean tooManyAttempt() {
            if (new Date().getTime() <= (lastAttempt.getTime() + DURATION) && attemptNb>=MAX_ATTEMPT) {
                return true;
            }
            return false;

        }
    }

    private static void cleanMaps() {
        List<String> emailToRemove = new ArrayList<>();
        for (Map.Entry<String, Attempts> stringAttemptsEntry : attemptByAccountMap.entrySet()) {
            if (new Date().getTime() > (stringAttemptsEntry.getValue().getLastAttempt().getTime() + DURATION)) {
                emailToRemove.add(stringAttemptsEntry.getKey());
            }
        }
        for (String email : emailToRemove) {
            attemptByAccountMap.remove(email);
        }

        List<String> ipToRemove = new ArrayList<>();
        for (Map.Entry<String, Attempts> stringAttemptsEntry : attemptByIpMap.entrySet()) {
            if (new Date().getTime() > (stringAttemptsEntry.getValue().getLastAttempt().getTime() + DURATION)) {
                ipToRemove.add(stringAttemptsEntry.getKey());
            }
        }

        for (String ip : ipToRemove) {
            attemptByIpMap.remove(ip);
        }

    }


}
