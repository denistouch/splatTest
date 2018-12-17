package sample.utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

public class PathTreeItem extends TreeItem<PathItem> {
    private boolean isLeaf = false;
    private boolean isFirstTimeChildren = true;
    private boolean isFirstTimeLeft = true;

    private PathTreeItem(PathItem pathItem) {
        super(pathItem);
        //System.out.println("private PathTreeItem(PathItem pathItem) + pathItem.getPath(): " + pathItem.getPath());
    }

    public static TreeItem<PathItem> createTreeItem(PathItem pathItem) {
        //System.out.println("public static TreeItem<PathItem> createTreeItem(PathItem pathItem) + pathItem.getPath(): " + pathItem.getPath());
        return new PathTreeItem(pathItem);
    }

    @Override
    public ObservableList<TreeItem<PathItem>> getChildren() {
        //System.out.println("public ObservableList<TreeItem<PathItem>> getChildren() ");
        if (isFirstTimeChildren) {
            isFirstTimeChildren = false;
            super.getChildren().setAll(buildChildren(this));
        }
        return super.getChildren();
    }

    @Override
    public boolean isLeaf() {
        if (isFirstTimeLeft) {
            isFirstTimeLeft = false;
            Path path = getValue().getPath();
            isLeaf = !Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS);
            //System.out.println("isLeaf + path: " + path);
        }
        return isLeaf;
    }

    private ObservableList<TreeItem<PathItem>> buildChildren(TreeItem<PathItem> treeItem) {
        //System.out.println("private ObservableList<TreeItem<PathItem>> buildChildren(TreeItem<PathItem> treeItem) ");
        Path path = treeItem.getValue().getPath();
        if (path != null && Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
            ObservableList<TreeItem<PathItem>> children = FXCollections.observableArrayList();
            try (DirectoryStream<Path> dirs = Files.newDirectoryStream(path)) {
                for (Path dir : dirs) {
                    PathItem pathItem = new PathItem(dir);
                    children.add(createTreeItem(pathItem));
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return children;
        }
        return FXCollections.emptyObservableList();
    }
}