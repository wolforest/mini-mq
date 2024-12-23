package com.wolf.minimq.domain.service.store.infra;

public interface MappedFileQueue {
    boolean load();
    void checkSelf();

    /**
     * get or create available MappedFile
     * which available space > messageSize
     *
     * @param messageSize messageSize
     * @return mappedFile
     */
    MappedFile getAvailableMappedFile(int messageSize);

    /**
     * get the mappedFile contains the offset
     *
     * @param offset offset
     * @return mappedFile | null
     */
    MappedFile getMappedFileByOffset(long offset);

    /**
     * create or find mappedFile contains offset
     * @param offset offset
     * @return MappedFile
     */
    MappedFile createMappedFileForOffset(long offset);

}
