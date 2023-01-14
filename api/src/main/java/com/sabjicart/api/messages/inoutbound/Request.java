
package com.sabjicart.api.messages.inoutbound;

import lombok.Data;

@Data
public class Request
{

    private String user;
    private long requestCreationTimeStamp;
    private int fetchSize;
    private int pageNumber;
}
