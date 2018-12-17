package sample.utilities;
import java.nio.file.Path;

public class PathItem {
    private Path path;

    public PathItem(Path path) {
        //System.out.println("public PathItem(Path path) + path: " + path);
        this.path = path;
    }

    public Path getPath() {
        //System.out.println("public Path getPath() + path: " + path);
        return path;
    }

    @Override
    public String toString() {
        //System.out.println("public String toString() + path: " + path);
        if (path.getFileName() == null) {
            return path.toString();
        } else {
            return path.getFileName().toString();
        }
    }
}