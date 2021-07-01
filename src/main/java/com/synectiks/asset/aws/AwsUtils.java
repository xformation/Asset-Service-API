package com.synectiks.asset.aws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClient;
import com.amazonaws.services.identitymanagement.model.GetUserResult;
import com.amazonaws.services.organizations.AWSOrganizations;
import com.amazonaws.services.organizations.AWSOrganizationsClientBuilder;
import com.amazonaws.services.organizations.model.DescribeOrganizationRequest;
import com.amazonaws.services.organizations.model.DescribeOrganizationResult;
import com.amazonaws.services.organizations.model.DescribeOrganizationalUnitRequest;
import com.amazonaws.services.organizations.model.DescribeOrganizationalUnitResult;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.amazonaws.services.securitytoken.model.GetCallerIdentityRequest;
import com.amazonaws.services.securitytoken.model.GetCallerIdentityResult;

public class AwsUtils {

	private static final Logger logger = LoggerFactory.getLogger(AwsUtils.class);
	
	public static AmazonS3 getAmazonS3Client(String accessKey, String secretKey, String s3Region) {
		logger.info("Getting AWS S3 client");
		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
		AmazonS3ClientBuilder s3ClientBuilder = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials));
		s3ClientBuilder.setRegion(s3Region);
		AmazonS3 s3Client = s3ClientBuilder.build();
		logger.info("AWS connection established");
		return s3Client;
	}

	@SuppressWarnings("deprecation")
	public static AmazonIdentityManagement getAmazonIdentityManagement(String accessKey, String secretKey, String s3Region) {
		logger.info("Getting AWS AmazonIdentityManagementClient");
//		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
//		AmazonIdentityManagementClient iamClient = new AmazonIdentityManagementClient(credentials);
		AmazonIdentityManagement aim = AmazonIdentityManagementClient.builder()
				.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
				.withRegion(s3Region)
				.build();
		return aim;
	}
	
	public static GetUserResult getAWSUser(String accessKey, String secretKey, String s3Region) {
	    AmazonIdentityManagement aim = getAmazonIdentityManagement(accessKey, secretKey, s3Region);
	    return aim.getUser();
	}
	
	public static AWSSecurityTokenService getAWSSecurityTokenService(String accessKey, String secretKey, String s3Region) {
		AWSSecurityTokenService awsSecurityTokenService = AWSSecurityTokenServiceClientBuilder.standard().withCredentials(
	            new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey))).withRegion(s3Region).build();
	    return awsSecurityTokenService;
	}
	
	public static String getAwsAccountId(String accessKey, String secretKey, String s3Region) {
	    GetCallerIdentityResult callerIdentity = getAWSSecurityTokenService(accessKey, secretKey, s3Region)
	    		.getCallerIdentity(new GetCallerIdentityRequest());
	    return callerIdentity.getAccount();
	}
	
	
	public static AWSOrganizations getAwsOrganizationClient(String accessKey, String secretKey, String s3Region) {
		logger.info("Getting AWS organization client");
		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
		AWSOrganizationsClientBuilder s3ClientBuilder = AWSOrganizationsClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials));
		s3ClientBuilder.setRegion(s3Region);
		return s3ClientBuilder.build();
	}
	
	public static com.amazonaws.services.organizations.model.Organization getAwsOrganization(String accessKey, String secretKey, String s3Region) {
		AWSOrganizations awsOrg = getAwsOrganizationClient(accessKey, secretKey, s3Region);
		DescribeOrganizationResult r = awsOrg.describeOrganization(new DescribeOrganizationRequest());
		com.amazonaws.services.organizations.model.Organization org = r.getOrganization();
		return org;
	}
	
	public static com.amazonaws.services.organizations.model.OrganizationalUnit getAwsOrganizationUnit(String accessKey, String secretKey, String s3Region) {
		AWSOrganizations awsOrg = getAwsOrganizationClient(accessKey, secretKey, s3Region);
		DescribeOrganizationalUnitResult r = awsOrg.describeOrganizationalUnit(new DescribeOrganizationalUnitRequest());
		com.amazonaws.services.organizations.model.OrganizationalUnit ou = r.getOrganizationalUnit();
		return ou;
	}
	

}
