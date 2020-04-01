package com.example.demo.services;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.demo.dao.AppTicketRepository;
import com.example.demo.dao.AppUserRepository;
import com.example.demo.entities.AppTicket;
import com.example.demo.entities.AppUser;
import com.github.sarxos.webcam.Webcam;

@Service
public class TicketServiceImpl implements TicketService {
	@Autowired
	private AppTicketRepository appTicketRepository;

	@Autowired
	AppUserRepository userRepository;
	private static final String SUFFIX = "/";

	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public AppTicket saveTicket(String username) {
		System.out.println("user========" + username);
		AppUser user = userRepository.findByUserName("admin");

		AppTicket appTicket = new AppTicket();

		AWSCredentials credentials = new BasicAWSCredentials("AKIAIDAC3CRR2LVZKZ5A",
				"gKhYWyPpc/S0rWgiswb2wjnCOSMT0qVzv4RxcmIZ");
		System.out.println("credentials" + credentials);

		AmazonS3 s3client = new AmazonS3Client(credentials);
		System.out.println("s3client" + s3client);
		String bucketName = "java-nbucket";
		s3client.createBucket(bucketName);

		// list buckets
		for (Bucket bucket : s3client.listBuckets()) {
			System.out.println(" - " + bucket.getName());
		}
		Webcam webcam = Webcam.getDefault();
		webcam.open();

		// get image
		BufferedImage image = webcam.getImage();

		// save image to PNG file
		try {
			ImageIO.write(image, "PNG", new File("test.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// create folder into bucket
		int nombreAleatoire = (int)(Math.random() *1000);
		String folderName = user.getUserName()+nombreAleatoire;
		appTicket.setTicketName("ticket"+folderName);
		createFolder(bucketName, folderName, s3client);

		// upload file to folder and set it to public
		String fileName = folderName + SUFFIX + "ticket1.jpg";
		s3client.putObject(new PutObjectRequest(bucketName, fileName, new File("C:\\Ticket\\ticket1.jpg"))
				.withCannedAcl(CannedAccessControlList.PublicRead));
		appTicket.setTicketUrl(((AmazonS3Client) s3client).getResourceUrl(bucketName, fileName));	
	
		appTicket.setIdUser(user.getId());
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo("ranim.mahfoudhi@agil-it.fr");

		msg.setSubject("Testing");
		String body =    " Employee Name: " + user.getUserName() +"\n" +
	          " Ticket Name: " + appTicket.getTicketName() +"\n" +
				"Ticket Url:"+appTicket.getTicketUrl()+"\n" +
	          " NOTE : This is an automated message. Please do not reply."+ "\n" +  "\n" ;                       
		
		
		msg.setText(body);

		javaMailSender.send(msg);

		return appTicketRepository.save(appTicket);
	}

	public static void createFolder(String bucketName, String folderName, AmazonS3 client) {
		// create meta-data for your folder and set content-length to 0
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(0);
		// create empty content
		InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
		// create a PutObjectRequest passing the folder name suffixed by /
		PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, folderName + SUFFIX, emptyContent,
				metadata);
		// send request to S3 to create folder
		client.putObject(putObjectRequest);
	}

	@Override
	public List<AppTicket> getAllTickets() {
		// TODO Auto-generated method stub
		return appTicketRepository.findAll();
	}
}
