package com.tein.overcatchbackend.domain.vm;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseFileUpload {
    private final String processId;

    public ResponseFileUpload(String processId) {
        this.processId = processId;
    }
}
