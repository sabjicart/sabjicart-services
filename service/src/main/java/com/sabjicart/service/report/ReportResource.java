
package com.sabjicart.service.report;

import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.messages.inoutbound.ResponseUtil;
import com.sabjicart.api.messages.report.ReportResponse;
import com.sabjicart.api.messages.report.WeeklyItemReportResponse;
import com.sabjicart.api.report.cart.CartReportService;
import com.sabjicart.api.report.substation.SubstationReportService;
import com.sabjicart.api.report.weekly.WeeklyItemReportService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
public class ReportResource
{

    CartReportService cartReportService;
    SubstationReportService substationReportService;
    WeeklyItemReportService weeklyItemReportService;

    @GetMapping(value = "/report/cart")
    public ResponseEntity<ReportResponse> getCartReport (
        @RequestParam("substationId")
        long substationId,
        @RequestParam("cartPlateNumber")
        String cartPlateNumber,
        @RequestParam("date")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate date)
    {
        // Declaration
        ReportResponse response = new ReportResponse();

        try {
            response = cartReportService.getCartReport(substationId,
                cartPlateNumber,
                date
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

    @GetMapping(value = "/report/substation")
    public ResponseEntity<ReportResponse> getSubstationReport (
        @RequestParam("substationId")
        long substationId,
        @RequestParam("date")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate date)
    {
        // Declaration
        ReportResponse response = new ReportResponse();

        try {
            response =
                substationReportService.getSubstationReport(substationId, date);
        }
        catch (ServiceException e) {
            ResponseUtil.error(response, e.getMessage());
            return new ResponseEntity<>(response,
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/report/weekly")
    public ResponseEntity<WeeklyItemReportResponse> getWeeklyReport (
        @RequestParam("substationId")
        long substationId,
        @RequestParam(value = "date",
            defaultValue = "1989-12-12")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate date)
    {
        if (date.isEqual(LocalDate.of(1989, 12, 12))) {
            date = LocalDate.now().minusDays(1);
        }
        WeeklyItemReportResponse response = new WeeklyItemReportResponse();
        try {
            response =
                weeklyItemReportService.getWeeklyItemReport(substationId, date);
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
