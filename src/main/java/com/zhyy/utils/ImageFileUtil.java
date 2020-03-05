package com.zhyy.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @program: SmartMedicine
 * @ClassName ImageFileUtil
 * @description: 图片上传工具类
 * @author: xlx
 * @create: 2020-03-04 16:45
 * @Version 1.0
 **/
public class ImageFileUtil
{
	public static Boolean uploadFile(byte[] file, String filePath, String fileName) throws Exception {
		FileOutputStream out = null;
		try {
			File targetFile = new File(filePath);
			//如果目录不存在，创建目录
			if(!targetFile.exists()){
				targetFile.mkdirs();
			}
			out = new FileOutputStream(filePath+fileName);
			out.write(file);
			out.flush();
			//写入成功
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			//写入失败
			return false;
		} finally {
			out.close();
		}
	}
}