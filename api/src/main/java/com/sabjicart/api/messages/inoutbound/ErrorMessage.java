
package com.sabjicart.api.messages.inoutbound;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage
{

    private ErrorType errorType;
    private String message;
}

