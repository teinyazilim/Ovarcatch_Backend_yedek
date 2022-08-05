package com.tein.overcatchbackend.enums;

public enum ModuleTypeEnum {
    COMPANY_CREATE("Company Application"),
    ADMIN("Company Application"),
    PAYROLL("Company Application"),
    VAT("Company Application"),
    COMPANYYEAREND("Company Application"),
    SOLETRADERACCOUNTS("Company Application"),
    SELFASSESMENT("Company Application"),
    VISAEXTENSION("Company Application"),
    MANAGER("Company Application"),
    LETTER_MODULE("Letter"),
    BANK_STATEMENT_CONVERSION("Company Application"),
    INVOICE_MODULE("Company Application"),
    CUSTOMER_ORDER_MODULE("Company Application"),
    VISA_DOCUMENT_MODULE("Company Application"),
    PAYROL_MODULE("Company Application"),
    REMINDER_MODULE("Company Application"),
    HELP_MODULE("Support"),
    INVOICE_DELETE("Invoice Delete"),
    INVOICE_UPDATE("Invoice Update"),
    EXPENSE_UPDATE("Expense Update"),
    EXPENSE_DELETE("Expenses Delete"),
    INCOME_DELETE ("Income Delete"),
    INCOME_UPDATE ( "Income Update"),
    SUPPORT_ADMIN("All Tasks and Support");

    private java.lang.String name;

    ModuleTypeEnum(String name) {
        this.name = name;
    }

    public java.lang.String getName() {
        return name;
    }

}
