package pl.softmil.test.utils.waituntil;

public class WaitUntilTimes {
    public final long maxMillisToWait;
    public final long sleepInterval;
    
    public static WaitUntilTimes withMaxAndSleepInteval(long maxMillisToWait,long sleepInterval){
        return new WaitUntilTimes(maxMillisToWait, sleepInterval);
    }
    
    private WaitUntilTimes(long maxMillisToWait, long sleepInterval) {
        super();
        this.maxMillisToWait = maxMillisToWait;
        this.sleepInterval = sleepInterval;
    }

}