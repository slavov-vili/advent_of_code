package day07;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import utils.AdventOfCodeUtils;

public class Day07Main {
	public static final Predicate<String> isCdDir = line -> line.startsWith("$ cd");
	public static final Predicate<String> isLs = line -> line.equals("$ ls");
	public static final Predicate<String> isDir = line -> line.startsWith("dir");

	public static void main(String[] args) {
		List<String> input = AdventOfCodeUtils.readInput(Day07Main.class);
		var fileSystem = parseFileSystem(input);
		fileSystem.updateSizes();

		System.out.println("Sum of directories with size <= 100000: " + solveA(fileSystem));

		System.out.println("Size of directory to delete: " + solveB(fileSystem));
	}

	public static int solveA(FileSystemElement fileSystem) {
		return fileSystem.getChildrenWhere(el -> !el.isFile() && el.size <= 100000).stream()
				.mapToInt(FileSystemElement::getSize).sum();
	}

	public static int solveB(FileSystemElement fileSystem) {
		var neededSpace = 30000000 - (70000000 - fileSystem.getSize());
		return fileSystem.getChildrenWhere(el -> !el.isFile() && el.size >= neededSpace).stream()
				.mapToInt(FileSystemElement::getSize).min().getAsInt();
	}

	public static FileSystemElement parseFileSystem(List<String> lines) {
		var rootDir = FileSystemElement.getRootDirectory();
		var curDir = rootDir;
		for (String line : lines) {
			var parts = line.split(" ");

			if (isCdDir.test(line)) {
				var nextDir = parts[2];

				if (FileSystemElement.ROOT_MARKER.equals(nextDir)) {
					curDir = rootDir;
				} else if ("..".equals(nextDir)) {
					curDir = curDir.getParent().get();
				} else {
					curDir = curDir.getChild(nextDir);
				}
			} else if (isLs.test(line)) {
				continue;
			} else if (isDir.test(line)) {
				var dirName = parts[1];
				curDir.addChild(FileSystemElement.getEmptyDirectory(dirName, Optional.of(curDir)));
			} else {
				var fileName = parts[1];
				var fileSize = Integer.parseInt(parts[0]);
				curDir.addChild(FileSystemElement.getFile(fileName, fileSize, Optional.of(curDir)));
			}
		}

		return rootDir;
	}

	public static class FileSystemElement {
		public static final String ROOT_MARKER = "/";

		private String name;
		private int size;
		private Optional<FileSystemElement> parent;
		private Map<String, FileSystemElement> childrenMap;

		public FileSystemElement(String name, int size, Optional<FileSystemElement> parent,
				Map<String, FileSystemElement> childrenMap) {
			this.setName(name);
			this.setSize(size);
			this.setParent(parent);
			this.setChildrenMap(childrenMap);
		}

		public static FileSystemElement getFile(String name, int size, Optional<FileSystemElement> parent) {
			return new FileSystemElement(name, size, parent, new HashMap<>());
		}

		public static FileSystemElement getRootDirectory() {
			return getEmptyDirectory(ROOT_MARKER, Optional.empty());
		}

		public static FileSystemElement getEmptyDirectory(String name, Optional<FileSystemElement> parent) {
			return getDirectory(name, parent, new HashMap<>());
		}

		public static FileSystemElement getDirectory(String name, Optional<FileSystemElement> parent,
				Map<String, FileSystemElement> children) {
			return new FileSystemElement(name, 0, parent, children);
		}

		public void updateSizes() {
			if (this.isFile())
				return;

			this.getChildren().forEach(FileSystemElement::updateSizes);
			this.setSize(this.getChildren().stream().mapToInt(FileSystemElement::getSize).sum());
		}

		public List<FileSystemElement> getChildrenWhere(Predicate<FileSystemElement> filter) {
			if (this.isFile())
				return List.of();

			var result = new ArrayList<FileSystemElement>();
			this.getChildren().stream().filter(filter).forEach(result::add);
			this.getChildren().stream().map(child -> child.getChildrenWhere(filter)).forEach(result::addAll);

			return result;
		}

		public FileSystemElement addChild(FileSystemElement newChild) {
			return this.getChildrenMap().put(newChild.getName(), newChild);
		}

		public FileSystemElement getChild(String childName) {
			return this.getChildrenMap().get(childName);
		}

		// Assume no empty directories exist
		public boolean isFile() {
			return this.getChildrenMap().isEmpty();
		}

		public Collection<FileSystemElement> getChildren() {
			return this.getChildrenMap().values();
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getSize() {
			return size;
		}

		public void setSize(int size) {
			this.size = size;
		}

		public Optional<FileSystemElement> getParent() {
			return parent;
		}

		public void setParent(Optional<FileSystemElement> parent) {
			this.parent = parent;
		}

		public Map<String, FileSystemElement> getChildrenMap() {
			return childrenMap;
		}

		public void setChildrenMap(Map<String, FileSystemElement> childrenMap) {
			this.childrenMap = childrenMap;
		}

	}

}
