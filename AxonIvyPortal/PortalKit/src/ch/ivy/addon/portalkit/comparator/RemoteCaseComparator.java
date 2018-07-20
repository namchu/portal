package ch.ivy.addon.portalkit.comparator;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;

import ch.ivy.addon.portalkit.bo.RemoteCase;

public final class RemoteCaseComparator {

	private RemoteCaseComparator() {}

	public static <U extends Comparable<? super U>> Comparator<RemoteCase> naturalOrderNullsFirst(
			Function<RemoteCase, U> function) {
		return Comparator.comparing(function, Comparator.nullsFirst(Comparator.naturalOrder()));
	}

	public static Comparator<RemoteCase> comparatorString(Function<? super RemoteCase, String> function) {
		Collator collator = Collator.getInstance(Locale.GERMAN);
		Function<? super RemoteCase, String> filteredFunction =
				function.andThen(s -> s == null ? StringUtils.EMPTY : s);
		return Comparator.comparing(filteredFunction, collator);
	}
}
