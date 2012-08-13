package pl.softmil.test.utils.waituntil;

public class TimeoutException extends RuntimeException {

    public TimeoutException(long maxMillisToWait, Until<?> until) {
        super("have been waiting for "+ until.toString() + " for " + maxMillisToWait +" millis" );
    }

}
