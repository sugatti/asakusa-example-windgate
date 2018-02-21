package com.example.jobflow;

import com.example.modelgen.dmdl.jdbc.AbstractItemInfoJdbcImporterDescription;

public class ItemInfoFromJDBC extends AbstractItemInfoJdbcImporterDescription {

    @Override
    public String getProfileName() {
        return "example";
    }
}
