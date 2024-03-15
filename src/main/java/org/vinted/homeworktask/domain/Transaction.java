package org.vinted.homeworktask.domain;

import java.util.Date;

public class Transaction {
    private Date packageDate;
    private String packageSizeCode;
    private String providerCode;

    public Date getPackageDate() {
        return packageDate;
    }

    public void setPackageDate(Date packageDate) {
        this.packageDate = packageDate;
    }

    public String getPackageSizeCode() {
        return packageSizeCode;
    }

    public void setPackageSizeCode(String packageSizeCode) {
        this.packageSizeCode = packageSizeCode;
    }

    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }
}
