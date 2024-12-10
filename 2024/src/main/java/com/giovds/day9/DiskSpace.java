package com.giovds.day9;

record DiskSpace(int fileId, int size) {
    boolean isFile() {
        return fileId != -1;
    }
}
