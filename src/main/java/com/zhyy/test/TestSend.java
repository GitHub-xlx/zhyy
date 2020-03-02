package com.zhyy.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;

/**
 *  @author Owner
 *  springMail发送邮件
 *  SendMail.java
 */
public class TestSend
{
	public ApplicationContext ctx = null;
	public TestSend() {
		ctx = new FileSystemXmlApplicationContext("src/main/resources/applicationContext.xml");
	}
	/**
	 * 主测试方法
	 *
	 * @throws MessagingException
	 */
//	public static void main(String[] args) {
//		new TestSend().sendMail();
//	}
	/**
	 * 发送简单邮件
	 */
	public void sendMail(String drugCode) {
		JavaMailSender sender = (JavaMailSender) ctx.getBean("mailSender");// 获取JavaMailSender
		SimpleMailMessage mail = new SimpleMailMessage();
		try {
			mail.setTo("1147175250@qq.com");// 接受者
			mail.setFrom("1147175250@qq.com");// 发送者
			mail.setSubject("库存不足警告!");// 主题
			mail.setText(drugCode+"不足，请及时添加");// 邮件内容
			sender.send(mail);
			System.out.println("发送完毕");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}