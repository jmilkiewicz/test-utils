package pl.softmil.test.utils.waituntil.blueprint;

import java.io.File;
import java.util.*;

public class UntilFileIsStable implements pl.softmil.test.utils.waituntil.Until<Map<String, List<Long>>> {
	private String pathName;
    private int stabilizationThreshold;
    private static final String fileSizeKeyName = "size";

    public UntilFileIsStable(String pathName, int stabilizationThreshold) {
        super();
        this.pathName = pathName;
        this.stabilizationThreshold = stabilizationThreshold;
    }

    @Override
    public boolean isTrue(Map<String, List<Long>> prev) {
        boolean result = false;
        File file = readFile();
        if (file.exists()) {
            long fileLength = file.length();
            addNewFileSizeToHistory(fileLength, prev);
            if (isStabilizationThresholdSatsfied(prev)) {
                result = true;
            }

        }
        return result;
    }

    private boolean isStabilizationThresholdSatsfied(
            Map<String, List<Long>> prev) {
        boolean result = false;
        List<Long> fileSizeHistory = getFileSizeHistory(prev);
        if (fileSizeHistory.size() >= stabilizationThreshold) {
            result = lastXElemsWithSameValue(fileSizeHistory);
        }
        return result;
    }

    private void addNewFileSizeToHistory(long length,
            Map<String, List<Long>> prev) {
        getFileSizeHistory(prev).add(length);        
    }
    
    private boolean lastXElemsWithSameValue(List<Long> fileSizeHistory) {
        List<Long> lastXelems = getLastXElems(fileSizeHistory);
        return new HashSet<Long>(lastXelems).size() == 1;
    }

    private List<Long> getLastXElems(List<Long> fileSizeHistory) {
        int historySize = fileSizeHistory.size();
        return fileSizeHistory.subList(historySize - stabilizationThreshold,
                historySize);
    }

    private List<Long> getFileSizeHistory(Map<String, List<Long>> prev) {
        return prev.get(fileSizeKeyName);
    }

    private File readFile() {
        return new File(pathName);
    }

    @Override
    public Map<String, List<Long>> getContext() {
        Map<String, List<Long>> result = new HashMap<String, List<Long>>();
        result.put(fileSizeKeyName, new LinkedList<Long>());
        return result;
    }
    
    @Override
	public String toString() {
		return "UntilFileIsStable [pathName=" + pathName
				+ ", stabilizationThreshold=" + stabilizationThreshold + "]";
	}

}
