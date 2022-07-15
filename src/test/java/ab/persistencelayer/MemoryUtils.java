package ab.persistencelayer;

public class MemoryUtils {

    public static long getMemoryUse(){
        putOutTheGarbage();
        long totalMemory = Runtime.getRuntime().totalMemory();
        putOutTheGarbage();
        long freeMemory = Runtime.getRuntime().freeMemory();
        return (totalMemory - freeMemory);
    }

    public static void collectGarbage(){
        try{
            System.gc();
            Thread.currentThread().sleep(100);
            System.runFinalization();
            Thread.currentThread().sleep(100);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public static void putOutTheGarbage() {
        collectGarbage();
        collectGarbage();
    }
}
