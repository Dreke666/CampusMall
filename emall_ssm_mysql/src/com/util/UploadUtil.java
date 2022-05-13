package com.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;

/**
 * commonsjar��
 * �ϴ�������
 * spring mvc֧��
 */
public class UploadUtil {
	

	/**
	 * ͼƬ�ϴ�
	 * @return �������·��
	 * @param photo ͼƬ�ļ�
	 * @param photoFileName �ļ���
	 * @param savePath �ļ�����·��(�����web��Ŀ¼)
	 * @return
	 * @throws Exception 
	 */
	public static String upload(MultipartFile file) throws Exception{
		// �ж��Ƿ����ϴ��ļ�
		if (Objects.isNull(file) || file.isEmpty() || Objects.isNull(file.getOriginalFilename())) {
			return null;
		}
		String savePath = "upload"; // �����ļ������Ŀ¼
		String fileName = file.getOriginalFilename();
		// �ļ��洢·��
		String path = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/")+savePath;			
		// ��ȡ��ǰ�ļ�����
		String type = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
        // ��ȡ��ǰϵͳʱ���ַ���
		String time = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		// �������ļ���
		String newFileName = time+"."+type;
		// ��ָ��·�������������ļ�
		File savefile = new File(path,newFileName);
		// �������ļ����ļ��в������򴴽�
		if(!savefile.getParentFile().exists()){
			savefile.getParentFile().mkdirs();
		}
		System.out.println("�ϴ��ļ�����·��: "+savefile.getPath());
		file.transferTo(savefile);
		return "../"+savePath+"/"+newFileName;
	}

}
