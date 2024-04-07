package com.codelab.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codelab.service.S3Service;

@RestController
@RequestMapping("/v1")
public class S3Controller {

	@Autowired
	private S3Service s3Service;

	@GetMapping("/buckets")
	public ResponseEntity<List<String>> getAllBuckets() {
		return ResponseEntity.ok(s3Service.getAllBuckets());
	}

	@PostMapping("/upload")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
		try {
			s3Service.uploadFile(file);
			return ResponseEntity.ok("File uploaded successfully to S3!");
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file to S3.");
		}

	}

}
