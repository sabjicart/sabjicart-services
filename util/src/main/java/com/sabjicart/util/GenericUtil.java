package com.sabjicart.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



@Slf4j
public class GenericUtil
{
    /**get Login userName of User who requested the access*/
    public static String getUserName ()
    {
        return "SYSTEM_USER";
//        Authentication auth =
//            SecurityContextHolder.getContext().getAuthentication();
//        if (auth == null) {
//            return null;
//        }
//        String userName = auth.getName();
//        if (userName == null) {
//            return "None";
//        }
//        return userName;
    }

    public static List<String> getUserRoles ()
    {
        Authentication auth =
            SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        List<String> roles = new ArrayList<>();
        List<GrantedAuthority> role =
            (List<GrantedAuthority>)auth.getAuthorities();
        Iterator iter = role.iterator();
        while (iter.hasNext()) {
            GrantedAuthority authority = (GrantedAuthority)iter.next();
            roles.add(authority.getAuthority());
        }
        return roles;
    }

    public static PropertiesHolder digitsCountEquals (Object num, int count)
    {
        PropertiesHolder genericUtil = new PropertiesHolder();

        PropertiesHolder isNullOrEmpty = isValid(num);
        if (!isNullOrEmpty.isValid()) {
            genericUtil.setValid(false);
            genericUtil.setMessage(isNullOrEmpty.getMessage());
        }
        else if (num.toString().length() != count) {
            genericUtil.setValid(false);
            genericUtil.setMessage(
                "digit count is: " + num.toString().length() + " not equal to "
                    + count);

        }
        else {
            genericUtil.setValid(true);
        }
        return genericUtil;
    }

    public static PropertiesHolder digitsCountGreaterThan (
        Object num, int count)
    {
        PropertiesHolder genericUtil = new PropertiesHolder();

        PropertiesHolder isNullOrEmpty = isValid(num);
        if (!isNullOrEmpty.isValid()) {
            genericUtil.setValid(false);
            genericUtil.setMessage(isNullOrEmpty.getMessage());
        }
        else if (num.toString().length() <= count) {
            genericUtil.setValid(false);
            genericUtil.setMessage("Digit count is:" + num.toString().length()
                + " not greater than " + count);
        }
        else {
            genericUtil.setValid(false);
        }
        return genericUtil;
    }

    public static PropertiesHolder digitsCountLessThan (Object num, int count)
    {
        PropertiesHolder genericUtil = new PropertiesHolder();

        PropertiesHolder isNullOrEmpty = isValid(num);
        if (!isNullOrEmpty.isValid()) {
            genericUtil.setValid(false);
            genericUtil.setMessage(isNullOrEmpty.getMessage());
        }
        else if (num.toString().length() >= count) {
            genericUtil.setValid(false);
            genericUtil.setMessage("Digit count is:" + num.toString().length()
                + " not greater than " + count);
        }
        else {
            genericUtil.setValid(false);
        }
        return genericUtil;
    }

    public static long generateId ()
    {
        return new Date().getTime();
    }

    public static String generateId (String type)
    {
        return type + "-" + new Date().getTime();
    }

    public static String generateUUID ()
    {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        return uuidAsString;
    }

    // Validate Email
    public static PropertiesHolder isValidEmail (String email)
    {
        PropertiesHolder genericUtilResp = new PropertiesHolder();

        PropertiesHolder isNullOrEmpty = isValid(email);
        email = email.trim();
        if (!isNullOrEmpty.isValid()) {
            genericUtilResp.setValid(false);
            genericUtilResp.setMessage(isNullOrEmpty.getMessage());
        }
        else {
            Pattern emailNamePtrn = Pattern.compile(
                "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
            Matcher mtch = emailNamePtrn.matcher(email);
            if (mtch.matches()) {
                genericUtilResp.setValid(true);
            }
            else {
                genericUtilResp.setValid(false);
                genericUtilResp.setMessage("Invalid email address");
            }

        }
        return genericUtilResp;
    }

    //Validate Password for constraint
    public static PropertiesHolder validatePassword (String password)
    {
        PropertiesHolder genericUtilResp = new PropertiesHolder();

        PropertiesHolder isNullOrEmpty = isValid(password);
        if (!isNullOrEmpty.isValid()) {
            genericUtilResp.setValid(false);
            genericUtilResp.setMessage(isNullOrEmpty.getMessage());
        }
        else {
            Pattern passwordPtrn =
                Pattern.compile("^(?=.*[A-Z])(?=.*\\d).{6,}$");
            Matcher mtch = passwordPtrn.matcher(password);
            if (mtch.matches()) {
                genericUtilResp.setValid(true);
            }
            else {
                genericUtilResp.setValid(false);
                //check this properly
                genericUtilResp.setMessage(
                    "Invalid password. Please provide a valid password of at least 6 digit, "
                        + "containing at least one UPPER case and one digit.");
            }

        }
        return genericUtilResp;
    }

    // File Validation foe extensions txt, doc, csv, pdf
    public static PropertiesHolder validateFileExtension (String fileName)
    {
        PropertiesHolder genericUtilResp = new PropertiesHolder();

        PropertiesHolder isNullOrEmpty = isValid(fileName);
        if (!isNullOrEmpty.isValid()) {
            genericUtilResp.setValid(false);
            genericUtilResp.setMessage(isNullOrEmpty.getMessage());
        }
        else {
            Pattern fileNamePtrn =
                Pattern.compile("([^\\s]+(\\.(?i)(txt|doc|csv|pdf))$)");
            Matcher mtch = fileNamePtrn.matcher(fileName);
            if (mtch.matches()) {
                genericUtilResp.setValid(true);
            }
            else {
                genericUtilResp.setValid(false);
                genericUtilResp.setMessage("Invalid file name");
            }

        }
        return genericUtilResp;
    }

    // Image validation for extensions jpg, png, gif, bmp
    public static PropertiesHolder validateImage (String image)
    {
        PropertiesHolder genericUtilResp = new PropertiesHolder();

        PropertiesHolder isNullOrEmpty = isValid(image);
        if (!isNullOrEmpty.isValid()) {
            genericUtilResp.setValid(false);
            genericUtilResp.setMessage(isNullOrEmpty.getMessage());
        }
        else {
            Pattern datePtrn =
                Pattern.compile("([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)");
            Matcher mtch = datePtrn.matcher(image);
            if (mtch.matches()) {
                genericUtilResp.setValid(true);
            }
            else {
                genericUtilResp.setValid(false);
                genericUtilResp.setMessage("Invalid image format");
            }

        }
        return genericUtilResp;
    }

    //extract Ip's from text log
    public static List<String> extractIpsFromText (String largeText)
    {
        String pattern = "(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})";
        return extractValueFromText(largeText, pattern);
    }

    //extract Values from text
    public static List<String> extractValueFromText (
        String largeText, String pattern)
    {
        List<String> values = new ArrayList<String>();
        Pattern ptn = Pattern.compile(pattern);
        Matcher mtch = ptn.matcher(largeText);

        while (mtch.find()) {
            values.add(mtch.group());
        }
        return values;
    }

    //split a string using regular expression
    public static List<String> splitStringUsingPattern (
        String largeText, String pattern)
    {
        List<String> values = new ArrayList<String>();
        Pattern ptn = Pattern.compile(pattern);
        String[] parts = ptn.split(pattern);
        for (String p : parts) {
            System.out.println(p);
            values.add(p);
        }
        return values;
    }

    //validate Object
    public static PropertiesHolder isValid (Object object)
    {
        PropertiesHolder genericUtil = new PropertiesHolder();
        if (object == null) {
            genericUtil.setValid(false);
            genericUtil.setMessage("Input is null");
        }
        else if (object instanceof String) {
            if (((String)object).trim().length() == 0) {
                genericUtil.setValid(false);
                genericUtil.setMessage("Input is Empty");
            }
            else {
                genericUtil.setValid(true);
            }
        }
        else {
            genericUtil.setValid(true);
        }

        return genericUtil;
    }

    //validate Object
    public static boolean isValidObject (Object object)
    {
        if (object == null) {
            return false;
        }
        else if (object instanceof String) {
            return ((String)object).trim().length() != 0;
        }
        return true;
    }

    //validate input string contains Number only
    public static PropertiesHolder isNumeric (String number)
    {
        PropertiesHolder genericUtil = new PropertiesHolder();

        String regex = "\\d+";

        PropertiesHolder isNullOrEmpty = isValid(number);
        if (!isNullOrEmpty.isValid()) {
            genericUtil.setValid(false);
            genericUtil.setMessage(isNullOrEmpty.getMessage());
        }
        else if (number.matches(regex)) {
            genericUtil.setValid(true);
        }
        else {
            genericUtil.setValid(false);
            genericUtil.setMessage("String is not numeric");
        }
        return genericUtil;
    }

    //validate input string contains No Number
    public static PropertiesHolder hasNoNumber (String noNumber)
    {
        PropertiesHolder genericUtil = new PropertiesHolder();

        String regex = "\\d+";

        PropertiesHolder isNullOrEmpty = isValid(noNumber);
        if (!isNullOrEmpty.isValid()) {
            genericUtil.setValid(false);
            genericUtil.setMessage(isNullOrEmpty.getMessage());
        }
        else if (noNumber.matches(regex)) {
            genericUtil.setValid(false);
            genericUtil.setMessage("String contain numeric digits");

        }
        else {
            genericUtil.setValid(true);

        }
        return genericUtil;
    }

    //validate input string contains only Alphabets
    public static PropertiesHolder hasOnlyAlphabets (String alphabets)
    {
        PropertiesHolder genericUtil = new PropertiesHolder();

        String regex = "^[a-zA-Z][a-zA-Z\\s]*$";

        PropertiesHolder isNullOrEmpty = isValid(alphabets);
        if (!isNullOrEmpty.isValid()) {
            genericUtil.setValid(false);
            genericUtil.setMessage(isNullOrEmpty.getMessage());
        }
        else if (alphabets.matches(regex)) {
            genericUtil.setValid(true);

        }
        else {
            genericUtil.setValid(false);
            genericUtil.setMessage(
                "Please provide correct input with no number and no special character");
        }
        return genericUtil;
    }

    //validate input string contains Special Character only
    public static PropertiesHolder isSpecialChar (String specialChar)
    {
        PropertiesHolder genericUtil = new PropertiesHolder();

        String regex = "[" + "-/@#!*$%^&.'_+={}()" + "]+";

        PropertiesHolder isNullOrEmpty = isValid(specialChar);
        if (!isNullOrEmpty.isValid()) {
            genericUtil.setValid(false);
            genericUtil.setMessage(isNullOrEmpty.getMessage());
        }
        else if (specialChar.matches(regex)) {
            genericUtil.setValid(true);

        }
        else {
            genericUtil.setValid(false);
            genericUtil.setMessage(
                "Please provide correct input with only special character");
        }
        return genericUtil;
    }

    //validate input string for Mobile Number
    public static PropertiesHolder isValidMobileNumber (String mobileNumber)
    {
        PropertiesHolder genericUtil = new PropertiesHolder();

        PropertiesHolder isNullOrEmpty = isValid(mobileNumber);
        mobileNumber = mobileNumber.trim();
        if (!isNullOrEmpty.isValid()) {
            genericUtil.setValid(false);
            genericUtil.setMessage(isNullOrEmpty.getMessage());
        }
        else if (mobileNumber.trim().length() >= 10) {

            Pattern mobPtrn = Pattern.compile(
                "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$");
            Matcher mtch = mobPtrn.matcher(mobileNumber);
            if (mtch.matches()) {

                genericUtil.setValid(true);
            }
        }
        else {
            genericUtil.setValid(false);
            genericUtil.setMessage(
                "Please provide valid 10 digits mobile number");
        }
        return genericUtil;
    }

    // Covert time "12:20" to 1220 Hours
    public static int parseTimeInHours (String time)
    {

        int timeInHours = -1;
        if (time != null) {
            String[] splitHours = time.split(":");
            if (splitHours.length != 2) {
                return timeInHours;
            }
            timeInHours =
                Integer.valueOf(splitHours[0]) * 100 + Integer.valueOf(
                    splitHours[1]);
        }
        return timeInHours;

    }

    public static String getNumericString (int n)
    {
        // chose a Character random from this String
        String AlphaNumericString = "0123456789";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            int index = (int)(AlphaNumericString.length() * Math.random());
            // add Character one by one in end of sb
            sb.append(AlphaNumericString.charAt(index));
        }

        return sb.toString();
    }

    public static String constructFullName (String firstName, String lastName)
    {

        // Declaration
        String fullName;

        // Initialization
        fullName = "";

        if (!StringUtil.nullOrBlankOrEmptyString(firstName)) {
            fullName = firstName;
        }
        if (!StringUtil.nullOrBlankOrEmptyString(lastName)) {
            if (!StringUtil.nullOrBlankOrEmptyString(fullName)) {
                fullName = fullName + " " + lastName;
            }
            else {
                fullName = lastName;
            }
        }
        return fullName;
    }
}
