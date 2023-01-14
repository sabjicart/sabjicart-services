package com.sabjicart.api.messages.report;

import com.sabjicart.api.messages.inoutbound.Response;
import com.sabjicart.api.report.WeeklyItemReportPojo;
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
public class WeeklyItemReportResponse extends Response

{
    private List<WeeklyItemReportPojo> weeklyItemReportPojoList;
}

