
package com.sabjicart.api.shared;

public enum Denomination
{
    WEIGHT ("WEIGHT"),
    NUMBER ("NUMBER");

    private final String name;

    Denomination (String s)
    {
        name = s;
    }

    public boolean equalsName (String otherName)
    {
        // (otherName == null) check is not needed because name.equals(null) returns false
        return name.equals(otherName);
    }

    public String toString ()
    {
        return this.name;
    }

}
