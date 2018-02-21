package com.example.jobflow;

import com.example.modelgen.dmdl.jdbc.AbstractSalesDetailJdbcImporterDescription;

public class SalesDetailFromJDBC extends AbstractSalesDetailJdbcImporterDescription {

    @Override
    public String getProfileName() {
        return "example";
    }

    @Override
    public String getCondition() {
        return "SALES_DATE_TIME between '${DATE} 00:00:00' and '${DATE} 23:59:59'";
    }
}
