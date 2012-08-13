package pl.softmil.test.utils.waituntil.blueprint;

import java.io.File;

import pl.softmil.test.utils.waituntil.Until;

public class UntilFolderIsNotEmpty implements Until<Object> {
	private final String folderPath;

	public UntilFolderIsNotEmpty(String folderPath) {
		validateFolderPath(folderPath);
		this.folderPath = folderPath;
	}

	private void validateFolderPath(String folderPathToCheck) {
		File f = new File(folderPathToCheck);
		if (!f.exists()) {
			throw new IllegalArgumentException("Path not exists: "
					+ folderPathToCheck);
		}
		if (!f.isDirectory()) {
			throw new IllegalArgumentException(folderPathToCheck
					+ " is not a path to directory");
		}

	}

	@Override
	public boolean isTrue(Object t) {
		File file = new File(folderPath);
		String[] list = file.list();
		return list.length > 0;
	}

	@Override
	public Object getContext() {
		return null;
	}

	@Override
	public String toString() {
		return "UntilFolderIsNotEmpty [folderPath=" + folderPath + "]";
	}
	
}
