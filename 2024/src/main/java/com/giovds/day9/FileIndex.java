package com.giovds.day9;

record FileIndex(int fileId, int index) {
    long calculateHash() {
        return (long) fileId * index;
    }
}
