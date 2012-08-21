package pl.softmil.test.utils.waituntil;

public class TimeoutException extends RuntimeException {

    public TimeoutException(long maxMillisToWait, Until<?> until) {
        super("waiting for " + maxMillisToWait +" millis , until " + until.toString() );
    }

}
