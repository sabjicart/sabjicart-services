
package com.sabjicart.service.transaction;

import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.messages.inoutbound.Response;
import com.sabjicart.api.messages.inoutbound.ResponseUtil;
import com.sabjicart.api.messages.transaction.AccountInfoRequest;
import com.sabjicart.api.messages.transaction.AccountInfoResponse;
import com.sabjicart.api.messages.transaction.BalanceResponse;
import com.sabjicart.api.messages.transaction.TransactionCategoryResponse;
import com.sabjicart.api.transaction.balance.BalanceInfoService;
import com.sabjicart.api.transaction.category.TransactionCategoryService;
import com.sabjicart.api.transaction.info.AccountInfoService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
public class TransactionResource
{
    TransactionCategoryService transactionCategoryService;
    AccountInfoService accountInfoService;
    BalanceInfoService balanceInfoService;

    @GetMapping("/transaction/category")
    public ResponseEntity<TransactionCategoryResponse> getTransactionCategories ()
    {
        TransactionCategoryResponse response =
            new TransactionCategoryResponse();
        try {
            response = transactionCategoryService.getTransactionCategories();
        }
        catch (ServiceException e) {
            ResponseUtil.error(response, e.getMessage());
            return new ResponseEntity<>(response,
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/transaction/account")
    public ResponseEntity<Response> updateTransactionInfo (
        @RequestBody
        @Valid AccountInfoRequest accountInfoRequest)
    {
        Response response = new Response();
        try {
            response =
                accountInfoService.updateTransactionInfo(accountInfoRequest);
        }
        catch (ServiceException e) {
            ResponseUtil.error(response, e.getMessage());
            return new ResponseEntity<>(response,
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/transaction/account")
    public ResponseEntity<AccountInfoResponse> getTransactionInfo (
        @RequestParam("fromDate")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate fromDate,
        @RequestParam("toDate")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate toDate,
        @RequestParam("page")
        int page,
        @RequestParam("fetchSize")
        int fetchSize)
    {
        AccountInfoResponse response = new AccountInfoResponse();
        try {
            response = accountInfoService.getBankStatement(fromDate,
                toDate,
                page,
                fetchSize
            );
        }
        catch (ServiceException e) {
            ResponseUtil.error(response, e.getMessage());
            return new ResponseEntity<>(response,
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/transaction/balance")
    public ResponseEntity<BalanceResponse> getCurrentBalance ()
    {
        BalanceResponse response = new BalanceResponse();
        try {
            response = balanceInfoService.getCurrentBalance();
        }
        catch (ServiceException e) {
            ResponseUtil.error(response, e.getMessage());
            return new ResponseEntity<>(response,
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
