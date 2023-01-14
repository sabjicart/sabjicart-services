
package com.sabjicart.api.messages.transaction;

import com.sabjicart.api.messages.inoutbound.Response;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionCategoryResponse extends Response
{
    List<TransactionCategoryPojo> transactionCategoryPojos;
}
