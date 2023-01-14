
package com.sabjicart.api.shared;

public enum TransactionType
{
    CREDIT("CREDIT"),

    DEBIT("DEBIT");
    private final String name;

    TransactionType(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false
        return name.equals(otherName);
    }

    public String toString ()
    {
        return this.name;
    }
}

