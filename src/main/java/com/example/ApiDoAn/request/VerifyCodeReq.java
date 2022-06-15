package com.example.ApiDoAn.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VerifyCodeReq {

    private int verifyCodeEmail;

	public int getVerifyCodeEmail() {
		return verifyCodeEmail;
	}

	public void setVerifyCodeEmail(int verifyCodeEmail) {
		this.verifyCodeEmail = verifyCodeEmail;
	}

}
