package com.codelab.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class S3Service {

	@Autowired
	private AmazonS3 s3Client;

	@Value("${cloud.aws.bucket.name}")
	private String bucketName;

	public List<String> getAllBuckets() {
		List<String> buckets = new ArrayList<>();
		try {
			List<Bucket> listBucketResp = s3Client.listBuckets();
			if (listBucketResp != null && !listBucketResp.isEmpty()) {
				listBucketResp.stream().forEach(b -> buckets.add(b.getName()));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return buckets;
	}

	public void uploadFile(MultipartFile file) throws IOException, Exception {
		try {
			String fileName = file.getOriginalFilename();
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(file.getSize());
			metadata.setContentType("application/vnd.ms-excel");
			s3Client.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata));
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("Error while upload file to s3");
		}
	}

}
