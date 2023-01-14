package com.sabjicart.api.messages.metadata.cart;

import com.sabjicart.api.messages.inoutbound.Request;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemMetaRequest extends Request
{
    List<ItemMetaPojo> itemMetaPojos;
}