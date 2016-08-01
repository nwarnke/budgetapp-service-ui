package com.dto;

public class Subcategory {
    private Integer subcategoryId;
    private String subcategoryName;
    private Integer subcategoryExpenses;
    private Integer subcategoryLimit;

    public Integer getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(Integer subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public String getSubcategoryName() {
        return subcategoryName;
    }

    public void setSubcategoryName(String subcategoryName) {
        this.subcategoryName = subcategoryName;
    }

    public Integer getSubcategoryExpenses() {
        return subcategoryExpenses;
    }

    public void setSubcategoryExpenses(Integer subcategoryExpenses) {
        this.subcategoryExpenses = subcategoryExpenses;
    }

    public Integer getSubcategoryLimit() {
        return subcategoryLimit;
    }

    public void setSubcategoryLimit(Integer subcategoryLimit) {
        this.subcategoryLimit = subcategoryLimit;
    }
}
