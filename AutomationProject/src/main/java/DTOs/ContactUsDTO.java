package DTOs;

public class ContactUsDTO
{
    /* =============================================================================== */
    /* ----------------------------- [ATTRIBUTES] ------------------------------------ */
    /* =============================================================================== */
    private String name;
    private String email;
    private String subject;
    private String message;
    private String attachmentPath;


    /* ============================================================================ */
    /* ----------------------------- [GETTERS] ------------------------------------ */
    /* ============================================================================ */

    /**
     *
     * @return name for Contact Us Form
     */
    public String getName()
    {
        return name;
    }

    /**
     *
     * @return email for Contact Us Form
     */
    public String getEmail()
    {
        return email;
    }

    /**
     *
     * @return subject for Contact Us Form
     */
    public String getSubject()
    {
        return subject;
    }

    /**
     *
     * @return message for Contact Us Form
     */
    public String getMessage()
    {
        return message;
    }

    /**
     *
     * @return attachmentPath for Contact Us Form
     */
    public String getAttachmentPath()
    {
        return attachmentPath;
    }
}
