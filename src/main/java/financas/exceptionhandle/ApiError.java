package financas.exceptionhandle;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiError {
	private int status;
	private LocalDateTime dataHora; 
	private String path;
	private String messagem;
	private List<ErrorField> campos;
}
