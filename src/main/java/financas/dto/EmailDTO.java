package financas.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmailDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String to;
	private String subject;
	private String text;
	private String attachment;
}
