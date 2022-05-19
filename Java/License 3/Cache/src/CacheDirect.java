
public class CacheDirect {
    private final static int BLOCK_SIZE = 5;  // 32 bytes = 2^5
    private final int N_ROWS;

    private int[] cache;
    
    public CacheDirect(int n) {
        N_ROWS = n;
        this.cache = new int[(int) Math.pow(2, n)];
        
        for (int i = 0; i != this.cache.length; ++i)
            this.cache[i] = -1;
    }
    
    public boolean get(int address) {
        return this.getEtiquette(address) == this.cache[this.getPosition(address)];
    }
    
    public void add(int address) {
        this.cache[this.getPosition(address)] = this.getEtiquette(address);
    }
    
    public int getEtiquette(int address) {
        return address >>> (BLOCK_SIZE + N_ROWS);
    }
    
    public int getPosition(int address) {
        return (address >>> BLOCK_SIZE) & (this.cache.length - 1);
    }
}
