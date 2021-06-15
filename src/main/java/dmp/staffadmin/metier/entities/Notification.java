package dmp.staffadmin.metier.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Notification
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNotification;
    @Temporal(TemporalType.DATE) // Pour JPA. Signifie que dans la BD la date aura le type Date et non le type TimeStamp
    @DateTimeFormat(pattern = "yyyy-MM-dd") // Spring formate la date pour nous
    private Date dateCreation;
    private boolean vue;

    @ManyToOne @JoinColumn(name = "ID_DESTINATAIRE")
    private Agent destinataire;
    @ManyToOne @JoinColumn(name = "ID_INITIATEUR")
    private Agent initiateur;
    @ManyToOne
    private NotificationMessage notificationMessage;

}
