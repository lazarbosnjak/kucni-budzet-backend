package lazarbosnjak.kucniBudzet.model;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lazarbosnjak.kucniBudzet.enumeration.SupportTokenType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SupportToken {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JdbcTypeCode(Types.VARCHAR)
	private UUID id;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime expiresAt;
	
	private LocalDateTime validatedAt;
	
	private boolean used;
	
	@Enumerated(EnumType.STRING)
	private SupportTokenType type;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "korisnik_id", nullable = false)
	private User user;
}
