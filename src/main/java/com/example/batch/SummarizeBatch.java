package com.example.batch;

import com.asakusafw.vocabulary.batch.Batch;
import com.asakusafw.vocabulary.batch.BatchDescription;
import com.example.jobflow.SummarizeSalesJob;

@Batch(name = "example.summarizeSales")
public class SummarizeBatch extends BatchDescription {

    @Override
    protected void describe() {
        run(SummarizeSalesJob.class).soon();
    }
}
