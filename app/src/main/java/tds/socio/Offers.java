

/**
 * Created by laks on 31-01-2015.
 */

package tds.socio;

import com.orm.SugarRecord;
import java.util.Date;

public class Offers extends SugarRecord<Offers> {

    Long OfferId;
    Integer Icon;
    String Sender;
    String Subject;
    String Description;
    Date ReceivedTime;
    Boolean isRead;
    Boolean isFavourite;

    public Offers() {}

    public Offers(Long offerId, Integer icon, String sender, String subject, String description, Date receivedTime, Boolean isRead, Boolean isFavourite) {
        OfferId = offerId;
        Icon = icon;
        Sender = sender;
        Subject = subject;
        Description = description;
        ReceivedTime = receivedTime;
        this.isRead = isRead;
        this.isFavourite = isFavourite;
    }

    public Long getOfferId() {
        return OfferId;
    }

    public void setOfferId(Long offerId) {
        OfferId = offerId;
    }

    public Integer getIcon() {
        return Icon;
    }

    public void setIcon(Integer icon) {
        Icon = icon;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Date getReceivedTime() {
        return ReceivedTime;
    }

    public void setReceivedTime(Date receivedTime) {
        ReceivedTime = receivedTime;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public Boolean getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(Boolean isFavourite) {
        this.isFavourite = isFavourite;
    }
}
