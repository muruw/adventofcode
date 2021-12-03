package _2021.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {

  public static <T> List<String> readLinesFromResourceFile(String resourceFileName, Class<T> className) throws URISyntaxException, IOException {
    URL resource = className.getResource(resourceFileName);
    List<String> result;
    try (Stream<String> lines = Files.lines(Paths.get(resource.toURI()))) {
      result = lines.collect(Collectors.toList());
    }
    return result;
  }

}
