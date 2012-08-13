package pl.softmil.test.utils.waituntil.blueprint;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class UntilFolderIsNotEmptyTest {
	private static final Object NEVER_MIND = new Object();

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	@Test(expected = IllegalArgumentException.class)
	public void exceptionForNonExistingFolderName() {
		new UntilFolderIsNotEmpty(generateNonExistingFolderName());
	}

	@Test(expected = IllegalArgumentException.class)
	public void exceptionForFileNameInsteadOfDirectoryName() throws IOException {
		String existingFilePath = temporaryFolder.newFile("foo")
				.getCanonicalPath();
		assertThat("file to be tested not exists",
				new File(existingFilePath).exists(), is(true));
		new UntilFolderIsNotEmpty(existingFilePath);
	}

	@Test
	public void isTrueForNonEmptyFolder() throws IOException {
		File tempFile = createNewTempFile();

		assertThat("file to be tested not exists", tempFile.exists(), is(true));
		UntilFolderIsNotEmpty untilFolderIsNotEmpty = new UntilFolderIsNotEmpty(
				tempFile.getParent());

		assertThat("this package location can not be null",
				untilFolderIsNotEmpty.isTrue(NEVER_MIND), is(true));
	}

	private File createNewTempFile() throws IOException {
		return temporaryFolder.newFile("foo");
	}

	@Test
	public void isFalseForEmptyFolder() throws IOException {
		File newTempFolder = temporaryFolder.newFolder("foolder");

		assertThat("file to be tested not exists", newTempFolder.exists(),
				is(true));
		UntilFolderIsNotEmpty untilFolderIsNotEmpty = new UntilFolderIsNotEmpty(
				newTempFolder.getCanonicalPath());
		assertThat("this new freshly created folder location must be null",
				untilFolderIsNotEmpty.isTrue(NEVER_MIND), is(false));

	}

	private String generateNonExistingFolderName() {
		return "noe_existing_fiel" + System.currentTimeMillis();
	}

}
