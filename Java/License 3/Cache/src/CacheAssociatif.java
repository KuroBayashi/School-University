
public class CacheAssociatif {
    
    private final static int BLOCK_SIZE = 5;  // 32 bytes = 2^5
    private final int N_ENTRIES;
    private final int N_ROWS;
    
    private int[][][] cache;
    
    public CacheAssociatif(int n, int entries) {
        N_ROWS = n;
        N_ENTRIES = entries;
        
        this.cache = new int[(int) Math.pow(2, N_ROWS)][N_ENTRIES][2];
        
        for (int i = 0; i < this.cache.length; ++i) {
            for(int j = 0; j < N_ENTRIES; ++j) {
                this.cache[i][j] = new int[] {0, -1};
            }
        }
    }
    
    public boolean get(int address) {
        int position = this.getPosition(address);
        
        for (int i = 0; i < N_ENTRIES; ++i) {
            if (this.cache[position][i][1] == this.getEtiquette(address))
                return true;
        }
        
        return false;
    }
    
    public void add(int address) {
        int position = this.getPosition(address);
        int etiquette = this.getEtiquette(address);
        int index = -1; // index of "address"
        int prev = -1;  // previous "LRU" 
        
        // Get previous data
        for (int i = 0; i < N_ENTRIES; ++i) {
            if (this.cache[position][i][1] == etiquette) {
                index = i;
                prev = this.cache[position][i][0];
                break;
            }
        }
        
        // Update
        for (int i = 0; i < N_ENTRIES; ++i) {
            if (i == index && this.cache[position][i][0] == 0)
                break;
            
            if (-1 == index) {
                if (this.cache[position][i][0] == N_ENTRIES-1) {
                    this.cache[position][i][1] = etiquette;
                    this.cache[position][i][0] = 0;
                }
                else {
                    ++this.cache[position][i][0];
                }
            }
            else {
                if (i == index)
                    this.cache[position][i][0] = 0;
                else if (this.cache[position][i][0] < prev)
                    ++this.cache[position][i][0];
            }
        }
    }
    
    public int getEtiquette(int address) {
        return address >>> (BLOCK_SIZE + N_ROWS);
    }
    
    public int getPosition(int address) {
        return (address >>> BLOCK_SIZE) & (cache.length - 1);
    }
}
