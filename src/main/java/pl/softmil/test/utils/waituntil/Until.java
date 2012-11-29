package pl.softmil.test.utils.waituntil;

public interface Until<T> {
	boolean isTrue(T t);

	T getContext();
}
