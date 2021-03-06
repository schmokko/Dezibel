package de.dezibel.data;

import de.dezibel.io.MailUtil;
import java.util.Date;

/**
 * This class represents the application from an artist for a label and vice
 * versa.
 *
 * @author Henner
 * @inv self.text != null && self.creationDate != null && self.label != null &&
 * self.artist != null
 */
public class Application {

    private boolean fromArtist;

    private String text;

    private Date creationDate;

    private Label label;

    private User artist;

    private boolean markedForDeletion = false;

    /**
     * Creates a new application object representing an application from an
     * artist for a label if <code>fromArtist</code> is set to true or the other
     * way around if it's set to false.
     *
     * @param fromArtist Set to true if the applicant is an artist, otherwise
     * false.
     * @param text The message written by the applicant.
     * @param artist The artist who applies or receives an application.
     * @param label The label that applies or receives an application.
     */
    public Application(boolean fromArtist, String text, User artist, Label label) {
        this.fromArtist = fromArtist;
        this.text = text;
        this.label = label;
        this.artist = artist;

        this.creationDate = new Date();

        label.addApplication(this);
        artist.addApplication(this);
    }

    /**
     * Accept the application. (Notify both the label and the artist.)
     */
    public void accept() {
        this.label.addArtist(this.artist);
        this.artist.addArtistLabel(this.label);
        if (this.fromArtist) {
            MailUtil.sendMail("Bewerbung akzeptiert", "Deine Bewerbung beim Label \""
                    + this.label.getName() + "\" wurde akzeptiert.",
                    this.artist.getEmail());
        } else {
            for (User u : this.label.getLabelManagers()) {
                MailUtil.sendMail("Bewerbung akzeptiert", "Der Künstler \""
                        + this.artist.getPseudonym() + "\" wurde erfolgreich angeworben.",
                        u.getEmail());
            }
        }
        delete();
    }

    /**
     * Decline the application. (Notify the application's creator of
     * declination.)
     */
    public void decline() {
        if (this.fromArtist) {
            MailUtil.sendMail("Bewerbung abgelehnt", "Deine Bewerbung beim Label \""
                    + this.label.getName() + "\" wurde abgelehnt.",
                    this.artist.getEmail());
        } else {
            for (User u : this.label.getLabelManagers()) {
                MailUtil.sendMail("Bewerbung abgelehnt", "Der Künstler \""
                        + this.artist.getPseudonym() + "\" wurde nicht angeworben.",
                        u.getEmail());
            }
        }
        delete();
    }

    /**
     * Delete this application object and clear all of its associations.
     */
    public void delete() {
        if (markedForDeletion) {
            return;
        }
        markedForDeletion = true;
        this.artist.deleteApplication(this);
        this.artist = null;
        this.label.deleteApplication(this);
        this.label = null;
        Database.getInstance().deleteApplication(this);
    }

    /**
     * Returns true if this application was written by an artist and false
     * otherwise.
     *
     * @return True if the applicant is an artist, false if the applicant is a
     * label.
     * @pre true
     * @post self.fromArtist = self.fromArtistAtPre
     */
    public boolean isFromArtist() {
        return this.fromArtist;
    }

    public String getText() {
        return this.text;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public Label getLabel() {
        return this.label;
    }

    public User getUser() {
        return this.artist;
    }

    /**
     * Returns true if this application is marked for deletion, false otherwise.
     *
     * @return true if this application is marked for deletion else false
     */
    public boolean isMarkedForDeletion() {
        return markedForDeletion;
    }
}
