package dominio.prenda;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "colores")
@Getter
@Setter(AccessLevel.PUBLIC)
@RequiredArgsConstructor
@NoArgsConstructor

public class Color {

	public static Color of(String deColor) {
		final Color color = new Color(deColor);
		return color;
	}

	@Id
	@GeneratedValue
    @Column(name = "IdColor", unique = true)
    private int idColor;

	@NonNull
	@Column(name = "deColor")
	private String deColor;
	
}
