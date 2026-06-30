package org.ranking_app.dto.request.item;

import jakarta.annotation.Nullable;

public class ItemSearchRequest {

    @Nullable
    private String query;

    public ItemSearchRequest() {}

    public ItemSearchRequest(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
