
class RabinKarp {

    private long Q = BigInteger.probablePrime(31, new Random()).longValue();                         // a large prime
    private int x = 33;
    private long patternHash;
    private String pattern;
    private long XM;        // x^(M-1)


    public RabinKarp(String pattern) {
        this.pattern = pattern;
        this.patternHash = this.hash(pattern, pattern.length());
        XM = 1;
        for (int i = 1; i < pattern.length(); i++) {
            XM = (XM * x) % Q;
        }
    }

    private long hash(String key, int M) {
        // compute hash for key[0.....M-1]
        long h = 0;
        for (int j = 0; j < M; j++) {
            h = (h*x + key.charAt(j)) % Q;
        }
        return h;
    }

    public int search(String text) {
        int T = text.length();
        int P = pattern.length();

        long textHash = this.hash(text, P);
        if (textHash == patternHash) {
            boolean isFound = true;
            for (int j = 0; j < P; j++) {
                if (text.charAt(j) != pattern.charAt(j)) {
                    isFound = false;
                    break;
                }
            }
            if (isFound) return 0;
        }
        for (int i = 0; i < T - P; i++ ) {
            
            textHash = (textHash + Q - ((text.charAt(i) * XM) % Q));
            textHash = (textHash * x + text.charAt(i + P)) % Q;

            if (textHash == patternHash) {
                boolean isFound = true;
                for (int j = 0; j < P; j++) {
                    if (text.charAt(i + j + 1) != pattern.charAt(j)) {
                        isFound = false;
                        break;
                    }
                    
                }
                if (isFound) return i + 1;
            }
            

        }
        return -1;
    }
}