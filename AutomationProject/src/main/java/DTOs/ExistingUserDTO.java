package DTOs;

public class ExistingUserDTO
{
    /* =============================================================================== */
    /* ----------------------------- [ATTRIBUTES] ------------------------------------ */
    /* =============================================================================== */
    private String title;
    private String name;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String day;
    private String month;
    private String year;
    private String company;
    private String address1;
    private String address2;
    private String country;
    private String state;
    private String city;
    private String zipcode;
    private String mobileNumber;


    /* ============================================================================ */
    /* ----------------------------- [GETTERS] ------------------------------------ */
    /* ============================================================================ */

    /**
     *
     * @return Title for Account Info
     */
    public String getTitle()
    {
        return title;
    }

    /**
     *
     * @return User's display name for signup
     */
    public String getName()
    {
        return name;
    }

    /**
     *
     * @return User's email address
     */
    public String getEmail()
    {
        return email;
    }

    /**
     *
     * @return Account password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     *
     * @return First name for address section
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     *
     * @return Last name for address section
     */
    public String getLastName()
    {
        return lastName;
    }

    /**
     *
     * @return Day of birth
     */
    public String getDay() {
        return day;
    }

    /**
     *
     * @return Month of birth
     */
    public String getMonth() {
        return month;
    }

    /**
     *
     * @return Year of birth
     */
    public String getYear() {
        return year;
    }

    /**
     *
     * @return Company name
     */
    public String getCompany()
    {
        return company;
    }

    /**
     *
     * @return Primary address line
     */
    public String getAddress1()
    {
        return address1;
    }

    /**
     *
     * @return Secondary address line
     */
    public String getAddress2()
    {
        return address2;
    }

    /**
     *
     * @return Country name
     */
    public String getCountry()
    {
        return country;
    }

    /**
     *
     * @return State
     */
    public String getState()
    {
        return state;
    }

    /**
     *
     * @return City name
     */
    public String getCity()
    {
        return city;
    }

    /**
     *
     * @return Zip/postal code
     */
    public String getZipcode()
    {
        return zipcode;
    }

    /**
     *
     * @return Mobile phone number
     */
    public String getMobileNumber()
    {
        return mobileNumber;
    }

}
