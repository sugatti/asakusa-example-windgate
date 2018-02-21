package com.example.operator;

import java.util.List;

import com.asakusafw.runtime.value.Date;
import com.asakusafw.runtime.value.DateTime;
import com.asakusafw.runtime.value.DateUtil;
import com.asakusafw.vocabulary.model.Key;
import com.asakusafw.vocabulary.operator.Fold;
import com.asakusafw.vocabulary.operator.MasterJoinUpdate;
import com.asakusafw.vocabulary.operator.MasterSelection;
import com.example.modelgen.dmdl.model.CategorySummary;
import com.example.modelgen.dmdl.model.ItemInfo;

public abstract class SummarizeSalesOperator {
    private final Date dateBuffer = new Date();

    @MasterSelection
    public ItemInfo selectAvailableItem(List<ItemInfo> candidates, CategorySummary sales) {
        DateTime dateTime = sales.getSalesDateTime();
        dateBuffer.setElapsedDays(DateUtil.getDayFromDate(
                dateTime.getYear(), dateTime.getMonth(), dateTime.getDay()));
        for (ItemInfo item : candidates) {
            if (item.getBeginDate().compareTo(dateBuffer) <= 0
                    && dateBuffer.compareTo(item.getEndDate()) <= 0) {
                return item;
            }
        }
        return null;
    }

    @MasterJoinUpdate(selection = "selectAvailableItem")
    public void joinItemInfo(
            @Key(group = "item_code") ItemInfo info,
            @Key(group = "item_code") CategorySummary sales) {
        sales.setCategoryCodeOption(info.getCategoryCodeOption());
        sales.setCategoryNameOption(info.getCategoryNameOption());
        sales.setSellingPrice(sales.getAmount() * info.getUnitPrice());
    }

    @Fold
    public void summarizeByCategory(@Key(group = "category_code") CategorySummary left, CategorySummary right) {
        left.setAmount(left.getAmount() + right.getAmount());
        left.setSellingPrice(left.getSellingPrice() + right.getSellingPrice());
    }
}
