package com.wolf.minimq.domain.model.dto;

import com.wolf.minimq.domain.service.store.infra.MappedFile;
import java.io.Serializable;
import java.nio.ByteBuffer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SelectedMappedBuffer implements Serializable {
    private long startOffset;
    private ByteBuffer byteBuffer;
    private int size;
    protected MappedFile mappedFile;
    private boolean isInCache = true;
}