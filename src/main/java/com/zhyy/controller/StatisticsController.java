package com.zhyy.controller;

import com.zhyy.entity.ImageResponse;
import com.zhyy.entity.People;
import com.zhyy.services.DrugServices;
import com.zhyy.utils.ImageFileUtil;
import com.zhyy.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/statisticsController")
public class StatisticsController
{
	@Autowired
	private DrugServices drugServices;

	/**
	 * @Description 药品请领统计
	 * @author xlx
	 * @Date 下午 17:27 2020/3/2 0002
	 * @Param [time]
	 * @return java.lang.String
	 **/
	@RequestMapping("/claim")
	public @ResponseBody List<People> queryAll(String time){
		String[] time1 = TimeUtil.getTime(time);
		return null;
	}


	@PostMapping("/projectPictureUpload")
	@ResponseBody
	public String projectPictureUpload(HttpServletRequest request, @RequestParam(value = "projectImg",required = true) MultipartFile file){
		System.out.println("进入文件上传");
		ImageResponse hwxResponse = new ImageResponse();
		String jsonstr = "";
		//将图片上传到服务器
		if(file.isEmpty()){
			hwxResponse.setCode(205);
			hwxResponse.setMsg("项目地址不能为空");
			jsonstr=hwxResponse.toString();
			return jsonstr;
		}
		//原始文件名
		String originalFilename = file.getOriginalFilename();
		//文件后缀
		String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
		//图片名称为uuid+图片后缀防止冲突
		String fileName = UUID.randomUUID().toString()+"."+suffix;
		String os = System.getProperty("os.name");
		//文件保存路径
		String filePath="";
		String savePath = request.getSession().getServletContext().getRealPath("/resources/imagehome/");

		if(os.toLowerCase().startsWith("win")){
			//windows下的路径
			//todo 改为相对路径 添加数据库记录
			String realPath = new String("src/main/resources/imagehome/" );
//			savePath = "/imagehome/";
			filePath = realPath;
			//			System.out.println("savePath" + savePath);

		}else {
			filePath = savePath;

			//linux下的路径
			//			filePath="/root/resources/hwximages/";
		}
		try {
			//写入图片
			Boolean writePictureflag = ImageFileUtil.uploadFile(file.getBytes(),filePath,fileName);
			if(writePictureflag == false){
				//上传图片失败
				hwxResponse.setCode(205);
				hwxResponse.setMsg("文件上传失败");
				jsonstr=hwxResponse.toString();
				return jsonstr;
			}
			//上传成功后，将可以访问的完整路径返回
			String fullImgpath = filePath+fileName;
			hwxResponse.setCode(0);
			hwxResponse.setMsg("文件上传成功");
			hwxResponse.setData(fullImgpath);
			System.out.println("文件名：" + fileName);
			System.out.println("文件目录" + filePath);
			System.out.println("文件全目录==========" + fullImgpath);

//			//写入数据库
//			int temp = doctorService.addPicAddress(cpid, fullImgpath);

			//			System.out.println("是否成功" + temp);
			jsonstr=hwxResponse.toString();
			return jsonstr;
		} catch (Exception e) {
			e.printStackTrace();
			//上传图片失败
			hwxResponse.setCode(205);
			hwxResponse.setMsg("文件上传失败");
			jsonstr=hwxResponse.toString();
			return jsonstr;
		}
	}
	/**
	 * 生成证据图片
	 */
	@RequestMapping("/getImage")
	public void getImage( String id , HttpServletResponse response )
	{
		String resouceName = "";
		String suffix = id.substring(id.lastIndexOf(".") + 1);

		if (!id.equals("")&&id!=null)
		{
			resouceName = id;
			try
			{
				File file =new File(resouceName);
				InputStream in=new FileInputStream(file);//实例化FileInputStream
				BufferedImage image = ImageIO.read(in);
				//			response.setContentType("image/png");
				OutputStream outputStream = response.getOutputStream();
				ImageIO.write(image,suffix,outputStream);
				in.close();
				outputStream.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}


	}
}
