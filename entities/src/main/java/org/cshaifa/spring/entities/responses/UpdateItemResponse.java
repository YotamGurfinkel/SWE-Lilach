package org.cshaifa.spring.entities.responses;

import org.cshaifa.spring.entities.CatalogItem;

public class UpdateItemResponse extends Response {
    private CatalogItem updatedItem = null;

    public UpdateItemResponse(int requestId, boolean success) {
        super(requestId, success);
    }

    public UpdateItemResponse(CatalogItem updatedItem) {
        super(true);
        this.updatedItem = updatedItem;
    }

    public CatalogItem getUpdatedItem() {
        return updatedItem;
    }
}
