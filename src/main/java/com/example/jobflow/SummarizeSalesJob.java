package com.example.jobflow;

import com.asakusafw.vocabulary.flow.Export;
import com.asakusafw.vocabulary.flow.FlowDescription;
import com.asakusafw.vocabulary.flow.Import;
import com.asakusafw.vocabulary.flow.In;
import com.asakusafw.vocabulary.flow.JobFlow;
import com.asakusafw.vocabulary.flow.Out;
import com.asakusafw.vocabulary.flow.util.CoreOperatorFactory;
import com.example.modelgen.dmdl.model.CategorySummary;
import com.example.modelgen.dmdl.model.ItemInfo;
import com.example.modelgen.dmdl.model.SalesDetail;
import com.example.operator.SummarizeSalesOperatorFactory;
import com.example.operator.SummarizeSalesOperatorFactory.JoinItemInfo;
import com.example.operator.SummarizeSalesOperatorFactory.SummarizeByCategory;

@JobFlow(name = "summarizeSalesJob")
public class SummarizeSalesJob extends FlowDescription {
    final In<SalesDetail> sales;
    final In<ItemInfo> item;
    final Out<CategorySummary> categorySummary;
    public SummarizeSalesJob(
            @Import(name = "sales", description = SalesDetailFromJDBC.class)
            In<SalesDetail> sales,
            @Import(name = "item", description = ItemInfoFromJDBC.class)
            In<ItemInfo> item,
            @Export(name = "result", description = CategorySummaryToJDBC.class)
            Out<CategorySummary> categorySummary) {
        this.sales = sales;
        this.item = item;
        this.categorySummary = categorySummary;
    }

    @Override
    protected void describe() {
        CoreOperatorFactory core = new CoreOperatorFactory();
        SummarizeSalesOperatorFactory operator = new SummarizeSalesOperatorFactory();

        JoinItemInfo joinedItem
                = operator.joinItemInfo(item, core.restructure(sales, CategorySummary.class));
        core.stop(joinedItem.missed);
        SummarizeByCategory summarized = operator.summarizeByCategory(joinedItem.updated);
        categorySummary.add(summarized.out);
    }
}
