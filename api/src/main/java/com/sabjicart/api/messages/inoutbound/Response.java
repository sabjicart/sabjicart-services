
package com.sabjicart.api.messages.inoutbound;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Response
{
    private HttpStatus status;
    private int statusCode;
    private int fetchSize;
    private int pageNumber = 1;
    private int totalRecordsRetrieved = 0;
    private long totalRecords;
    private int totalPages = 1;
    private List<ErrorMessage> errorMessages = new ArrayList();
    private DebugInfo debugInfo;

    public Response (List<String> msgs, HttpStatus status)
    {
        for (String msg : msgs) {
            this.errorMessages.add(new ErrorMessage(ErrorType.Error, msg));
        }
        this.status = status;
        this.statusCode = status.value();
    }

    public Response (List<String> msgs)
    {
        for (String msg : msgs) {
            this.errorMessages.add(new ErrorMessage(ErrorType.Error, msg));
        }
    }

    public void addErrorMessage (ErrorMessage msg)
    {
        this.errorMessages.add(msg);
    }

    public void addErrorMessage (String msg)
    {
        ErrorMessage error = new ErrorMessage();
        error.setErrorType(ErrorType.Error);
        error.setMessage(msg);
        this.errorMessages.add(error);
    }

    public void addErrorMessages (List<ErrorMessage> errorMessageList)
    {
        this.errorMessages.addAll(errorMessageList);
    }

    public boolean hasErrorMessages ()
    {
        return this.errorMessages != null && !this.errorMessages.isEmpty();
    }

    public DebugInfo getDebugInfo ()
    {
        if (this.debugInfo == null) {
            this.debugInfo = new DebugInfo();
        }

        return this.debugInfo;
    }

}
