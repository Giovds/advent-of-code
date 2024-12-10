package com.giovds.day9;

import com.giovds.Day;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Day9 extends Day {
    public static void main(String[] args) {
        new Day9();
    }

    @Override
    protected Number part1() {
        final char[] diskMap = inputRows.getFirst().toCharArray();

        int totalSize = 0;
        final LinkedList<DiskSpace> diskSpaces = new LinkedList<>();
        for (int i = 0, fileId = 0; i < diskMap.length; i++) {
            final int length = Integer.parseInt(Character.toString(diskMap[i]));
            // every other number is a file
            if (i % 2 == 0) {
                diskSpaces.add(new DiskSpace(fileId++, length));
            } else {
                diskSpaces.add(new DiskSpace(-1, length));
            }
            totalSize += length;
        }

        final List<FileIndex> fileOrder = new ArrayList<>(totalSize);
        int i = 0;
        while (i < totalSize) {
            final DiskSpace nextDiskSpace = diskSpaces.poll();
            if (nextDiskSpace == null) break; // list is empty so we're done

            if (nextDiskSpace.isFile()) {
                for (int j = 0; j < nextDiskSpace.size(); j++) {
                    fileOrder.add(new FileIndex(nextDiskSpace.fileId(), i++)); // increment next index
                }
            } else {
                // It's an empty space
                // Search next file from the end of the list and fill the empty space
                int emptySpaceLeft = nextDiskSpace.size();
                while (emptySpaceLeft > 0) {
                    final DiskSpace nextFile = diskSpaces.pollLast();
                    if (nextFile == null) break;
                    if (!nextFile.isFile()) continue; // must be a file

                    int fileSize = nextFile.size();
                    int spaceToUse = Math.min(emptySpaceLeft, fileSize);

                    for (int j = 0; j < spaceToUse; j++) {
                        fileOrder.add(new FileIndex(nextFile.fileId(), i++)); //increment index
                    }

                    emptySpaceLeft -= spaceToUse;
                    if (fileSize > spaceToUse) {
                        // There is still a part of the file left, we add it to the back of the list
                        diskSpaces.addLast(new DiskSpace(nextFile.fileId(), fileSize - spaceToUse));
                    }

                    if (emptySpaceLeft <= 0) break; // no more space so we are done
                }
            }
        }
        return fileOrder.stream()
                .mapToLong(FileIndex::calculateHash)
                .sum();
    }

    @Override
    protected Number part2() {
        final char[] diskMap = inputRows.getFirst().toCharArray();
        final List<DiskSpace> diskSpaces = new ArrayList<>();
        for (int i = 0, fileId = 0; i < diskMap.length; i++) {
            final int length = Integer.parseInt(Character.toString(diskMap[i]));
            // every other number is a file
            if (i % 2 == 0) {
                diskSpaces.add(new DiskSpace(fileId++, length));
            } else {
                diskSpaces.add(new DiskSpace(-1, length));
            }
        }

        // Start looking for a file from the end of the list
        for (int fileIndex = diskSpaces.size() - 1; fileIndex >= 0; fileIndex--) {
            final DiskSpace file = diskSpaces.get(fileIndex);
            if (!file.isFile()) {
                continue;
            }

            // if it's a file we start looking for a free space at the start, unless we have passed the file index
            // to avoid placing the files at the end again
            for (int freeIndex = 0; freeIndex < fileIndex; freeIndex++) {
                final DiskSpace freeSpace = diskSpaces.get(freeIndex);
                if (freeSpace.isFile() || freeSpace.size() < file.size()) {
                    // It's a file or there is no room for it
                    continue;
                }

                // Replace the free space with the file
                diskSpaces.set(freeIndex, new DiskSpace(file.fileId(), file.size()));
                if (freeSpace.size() > file.size()) {
                    // There is room left
                    // add the remaining free space
                    diskSpaces.add(freeIndex + 1, new DiskSpace(-1, freeSpace.size() - file.size()));
                    // remove the file
                    diskSpaces.remove(fileIndex + 1);
                    // add empty space where the file was originally
                    diskSpaces.add(fileIndex + 1, new DiskSpace(-1, file.size()));
                } else {
                    // replace the file with empty space as they had the same size
                    diskSpaces.set(fileIndex, new DiskSpace(-1, file.size()));
                }
                break;
            }
        }

        // determine the index
        final List<FileIndex> order = new ArrayList<>(diskSpaces.size());
        int fileIndexCount = 0;
        for (final DiskSpace diskSpace : diskSpaces) {
            for (int i = 0; i < diskSpace.size(); i++) {
                order.add(new FileIndex(diskSpace.fileId(), fileIndexCount++));
            }
        }

        return order.stream()
                .filter(x -> x.fileId() != -1)
                .mapToLong(FileIndex::calculateHash)
                .sum();
    }
}
