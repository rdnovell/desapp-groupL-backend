package ar.edu.unq.groupl.app.model.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListUtil {
	
	public static <T> List<T> toList(Stream<T> stream) {
		return stream.collect(Collectors.toList());
	}
	
}
