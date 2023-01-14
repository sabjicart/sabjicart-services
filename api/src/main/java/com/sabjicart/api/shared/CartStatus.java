
package com.sabjicart.api.shared;

public enum CartStatus {
    LOAD("LOAD"),
    UNLOAD("UNLOAD"),
    SALEDATA("SALEDATA");

    private final String name;

    CartStatus(String s) {
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
