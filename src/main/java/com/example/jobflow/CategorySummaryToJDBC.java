package com.example.jobflow;

import com.example.modelgen.dmdl.jdbc.AbstractCategorySummaryJdbcExporterDescription;

public class CategorySummaryToJDBC extends AbstractCategorySummaryJdbcExporterDescription {

    @Override
    public String getProfileName() {
        return "example";
    }

}
