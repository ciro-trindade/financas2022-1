package financas.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import financas.service.S3Service;
import financas.service.UploadService;

@RestController
@RequestMapping("/upload")
public class UploadController {

	@Autowired
	private S3Service s3service;
	
	@Autowired
	private UploadService localService;
	

	@PostMapping("/s3")
	public ResponseEntity<Void> uploadFile(@RequestParam(name = "file") MultipartFile file) {
		URI uri = s3service.upload(file);
		return ResponseEntity.created(uri).build();
	}

	@PostMapping("/local")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Void> upload(@RequestParam("file") MultipartFile arquivo) {
		try {
			URI uri = localService.storeFile(arquivo);
			return ResponseEntity.created(uri).build();
		} catch (IOException | URISyntaxException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
