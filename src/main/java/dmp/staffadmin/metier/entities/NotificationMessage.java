package dmp.staffadmin.metier.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public class NotificationMessage
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMessage;
    private String objet;
    private String message;
}
