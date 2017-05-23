package github.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 图片验证码
 */
public class Captcha {
	
	/**
	 * 宽度
	 */
	private int width;
	/**
	 * 高度
	 */
	private int height;
	/**
	 * 内容
	 */
	private String code;
	/**
	 * 个数
	 */
	private int num;
	
	private static final  Random  ram = new Random();
	
	private  static Captcha captcha;

	public Captcha() {
		num=4;
		code="abcdefghjkmnpqrstuvwxyz2356789";
	}
	
	public static Captcha getInstance(){
		if(captcha==null){
			captcha = new Captcha();
		}
		return captcha;
	}
	

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	
	
	public void set(int width,int height,int num,String code){
		this.width=width;
		this.height= height;
		this.setCode(code);
		this.setNum(num);
	}
	
	public void set(int width,int height){
		this.width = width;
		this.height = height;
	}
	
	
	public String generateCheckCode(){
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<num;i++){
			sb.append(code.charAt(ram.nextInt(code.length())));
		}
		return sb.toString();
	}
	
	public BufferedImage generateCheckImg(String checkcode){
		//创建图片对象
				BufferedImage img=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
				//获取图片对象的画笔
				Graphics2D  graphic=img.createGraphics();
				graphic.setColor(Color.WHITE);
				graphic.fillRect(0, 0, width, height);
				graphic.setColor(Color.BLACK);
				graphic.drawRect(0, 0, width-1, height-1);
				Font font=new Font("宋体",Font.BOLD+Font.ITALIC,(int)(height*0.8));
				graphic.setFont(font);
				for(int i=0;i<num;i++){
					//产生随机颜色的数字
					graphic.setColor(new Color(ram.nextInt(255),ram.nextInt(255),ram.nextInt(255)));
					//产生的数字的宽度和高度
					graphic.drawString(String.valueOf(checkcode.charAt(i)), i*(width/num)+4, (int)(height*0.8));
				}
				//加一些点
				for(int i=0;i<(width+height);i++){
					graphic.setColor(new Color(ram.nextInt(255),ram.nextInt(255),ram.nextInt(255)));
					graphic.drawOval(width, height, 1, 1);
				}
				//加一些线
				for(int i=0;i<4;i++){
					graphic.setColor(new Color(ram.nextInt(255),ram.nextInt(255),ram.nextInt(255)));
					graphic.drawLine(0, ram.nextInt(height), width, ram.nextInt(height));
				}
				return img;
	}

}
