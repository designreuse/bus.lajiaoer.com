package com.demo.web.servlet.driver;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import com.demo.utils.ConfigUtils;
import com.demo.web.servlet.BaseServlet;

//@MultipartConfig(location = "upload/image/driver")
@WebServlet(asyncSupported = true, urlPatterns = { "/driver/image/upload" })
public class DriverImageUploadServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	// private static final MultipartConfig config;
	// private static final Logger logger = LoggerFactory.getLogger(DriverImageUploadServlet.class);
	// private static final String UPLOAD_IMAGE_DRIVER_LOCATION;
	//
	// static {
	// config = DriverImageUploadServlet.class.getAnnotation(MultipartConfig.class);
	// String filePath = DriverImageUploadServlet.class.getClassLoader().getResource("/upload/image/driver").getFile();
	// try {
	// filePath = URLDecoder.decode(filePath, "UTF-8");
	// } catch (UnsupportedEncodingException e) {
	// logger.error(e.getMessage());
	// }
	//
	// UPLOAD_IMAGE_DRIVER_LOCATION = filePath;
	// }

	@Override
	protected void doJspGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	@Override
	protected void doJspPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String realPath = request.getServletContext().getRealPath("/upload/image/driver");
		// 在解析请求之前先判断请求类型是否为文件上传类型
		// boolean isMultipart = ServletFileUpload.isMultipartContent(request);

		// 文件上传处理工厂
		FileItemFactory factory = new DiskFileItemFactory();

		// 创建文件上传处理器
		ServletFileUpload upload = new ServletFileUpload(factory);

		// 开始解析请求信息
		List<FileItem> items = null;
		try {
			items = upload.parseRequest(request);
		} catch (FileUploadException e) {
			e.printStackTrace();
		}

		// 对所有请求信息进行判断
		Iterator<FileItem> iter = items.iterator();
		while (iter.hasNext()) {
			FileItem item = iter.next();
			// 信息为普通的格式
			if (!item.isFormField()) {
				String fileName = item.getName();
				String saveName = System.currentTimeMillis() + "." + FilenameUtils.getExtension(fileName);
				File file = new File(realPath, saveName);
				try {
					item.write(file);
				} catch (Exception e) {
					e.printStackTrace();
				}

				StringBuilder sb = new StringBuilder();
				sb.append("{\"files\": [");
				sb.append("{");
				sb.append("\"name\":").append("\"").append(saveName).append("\"").append(",");
				sb.append("\"size\":").append(item.getSize()).append(",");
				sb.append("\"url\":").append("\"").append(ConfigUtils.getStringValue("static.image.host")).append("/upload/image/driver/").append(saveName).append("\"")
						.append(",");
				sb.append("\"thumbnailUrl\":").append("\"").append(ConfigUtils.getStringValue("static.image.host")).append("/upload/image/driver/").append(saveName).append("\"");
				sb.append("}");
				sb.append("]}");
				response.getWriter().print(sb.toString());
				response.getWriter().flush();
				break;
			}
		}
		/*
		 * // Part part = null; try { String realPath = request.getServletContext().getRealPath("/upload/image/driver"); // <input name="file" size="50" type="file" /> // part =
		 * request.getPart("files[]"); InputStream is = request.getInputStream(); byte[] ba = IOUtils.toByteArray(is); FileUtils.writeByteArrayToFile(new File(realPath), ba); }
		 * catch (IllegalStateException ise) { // 上传文件超过注解所标注的maxRequestSize或maxFileSize值 // if (config.maxRequestSize() == -1L) { // logger.info(
		 * "the Part in the request is larger than maxFileSize"); // } else if (config.maxFileSize() == -1L) { // logger.info("the request body is larger than maxRequestSize"); //
		 * } else { // logger.info("the request body is larger than maxRequestSize, or any Part in the request is larger than maxFileSize"); // }
		 * 
		 * // forwardErrorPage(request, response, "上传文件过大，请检查输入是否有误！"); return; } catch (IOException ieo) { // 在接收数据时出现问题 logger.error(
		 * "I/O error occurred during the retrieval of the requested Part"); } catch (Exception e) { logger.error(e.toString()); e.printStackTrace(); }
		 * 
		 * // if (part == null) { // // forwardErrorPage(request, response, "上传文件出现异常，请检查输入是否有误！"); // return; // }
		 * 
		 * // 得到文件的原始名称，eg ：测试文档.pdf // String fileName = UploadUtils.getFileName(part); String fileName = part.getHeader("content-disposition"); // if
		 * (StringUtils.isBlank(fileName)) { // return null; // }
		 * 
		 * fileName = StringUtils.substringBetween(fileName, "filename=\"", "\"");
		 * 
		 * logger.info("contentType : " + part.getContentType()); logger.info("fileName : " + fileName); logger.info("fileSize : " + part.getSize()); logger.info("header names : "
		 * ); for (String headerName : part.getHeaderNames()) { logger.info(headerName + " : " + part.getHeader(headerName)); }
		 * 
		 * String saveName = System.currentTimeMillis() + "." + FilenameUtils.getExtension(fileName);
		 * 
		 * logger.info("save the file with new name : " + saveName);
		 * 
		 * // 因在注解中指定了路径，这里可以指定要写入的文件名 // 在未执行write方法之前，将会在注解指定location路径下生成一临时文件 part.write(saveName);
		 * 
		 * StringBuilder sb = new StringBuilder(); sb.append("{\"files\": ["); sb.append("{"); sb.append("\"name\":").append("\"").append(saveName).append("\"").append(",");
		 * sb.append("\"size\":").append(part.getSize()).append(",");
		 * sb.append("\"url\":").append("\"").append("http://www.lajiaoer.com/upload/image/driver/").append(saveName).append("\"").append(",");
		 * sb.append("\"thumbnailUrl\":").append("\"").append("http://www.lajiaoer.com/upload/image/driver/").append(saveName).append("\""); sb.append("}"); sb.append("]}");
		 * response.getWriter().print(sb.toString()); response.getWriter().flush();
		 */
	}

}
