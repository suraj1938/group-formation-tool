package com.app.group15.PasswordPolicyManagement;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;

public class PasswordPolicyDaoMock extends PasswordPolicyAbstractDao{

	@Override
	public List<PasswordPolicy> getAll() throws SQLException, AwsSecretsManagerException {
		PasswordPolicy p1=new PasswordPolicy();
		p1.setPolicyName("Minimum length");
		p1.setPolicyValue("6");
		PasswordPolicy p2=new PasswordPolicy();
		p2.setPolicyName("Maximum length");
		p2.setPolicyValue("10");
		PasswordPolicy p3=new PasswordPolicy();
		p3.setPolicyName("Minimum Upper Case");
		p3.setPolicyValue("2");
		PasswordPolicy p4=new PasswordPolicy();
		p4.setPolicyName("Minimum lower case");
		p4.setPolicyValue("2");
		PasswordPolicy p5=new PasswordPolicy();
		p5.setPolicyName("Minimum Symbols");
		p5.setPolicyValue("2");
		PasswordPolicy p6=new PasswordPolicy();
		p6.setPolicyName("Char Not Allowed");
		p6.setPolicyValue("%$#");
		List<PasswordPolicy> list=new ArrayList();
		list.add(p1);
		list.add(p2);
		list.add(p3);
		list.add(p4);
		list.add(p5);
		list.add(p6);
		return list;
	}

	@Override
	public List<PasswordPolicy> getActivePasswordPolicy() throws SQLException, AwsSecretsManagerException {
		// TODO Auto-generated method stub
		return null;
	}

}
