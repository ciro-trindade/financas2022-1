package financas.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadService {
	private final Path rootLocation;
	private static final String location = "uploadDir";

	public UploadService() throws IOException {
		rootLocation = Paths.get(location);
		try {
			if (!Files.exists(rootLocation)) {
				Files.createDirectories(rootLocation);
			}
		} catch (IOException e) {
			throw new IOException("Não foi possível criar o diretório " + location);
		}
	}

	public URI storeFile(MultipartFile arquivo) throws IOException, URISyntaxException {
		try {
			if (arquivo.isEmpty()) {
				throw new IOException("Falha: o arquivo está vazio.");
			}
			Path destinationFile = this.rootLocation.resolve(Paths.get(arquivo.getOriginalFilename())).normalize()
					.toAbsolutePath();
			if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
				throw new IOException("Não é possível guardar o arquivo fora do diretório atual.");
			}
			try (InputStream inputStream = arquivo.getInputStream()) {
				Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
				return new URI(destinationFile.toString());
			}
		} catch (IOException e) {
			throw new IOException("Falha ao guardar o arquivo.", e);
		}
	}
}
