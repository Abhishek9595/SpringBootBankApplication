package com.technoelevate.springboot.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SwaggerPropDetails {

	@Value("${swagger.title}")
	private String title;
	@Value("${swagger.doc}")
	private String doc;
	@Value("${swagger.project.version}")
	private String version;
	@Value("${swagger.termsofservice}")
	private String termsOfService;
	@Value("${swagger.contact.username}")
	private String username;
	@Value("${swagger.contact.website}")
	private String website;
	@Value("${swagger.contact.email}")
	private String email;
	@Value("${swagger.api.license}")
	private String license;
	@Value("${swagger.api.licenseurl}")
	private String licenseurl;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDoc() {
		return doc;
	}

	public void setDoc(String doc) {
		this.doc = doc;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getTermsOfService() {
		return termsOfService;
	}

	public void setTermsOfService(String termsOfService) {
		this.termsOfService = termsOfService;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getLicenseurl() {
		return licenseurl;
	}

	public void setLicenseurl(String licenseurl) {
		this.licenseurl = licenseurl;
	}

	public SwaggerPropDetails() {
		super();
	}

}
