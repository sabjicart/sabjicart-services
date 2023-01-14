
package com.sabjicart.api.messages.inoutbound;

import lombok.Data;

@Data
public class DebugInfo
{
    private Long requestProcessingTime;
    private String additionalInfo;
}
