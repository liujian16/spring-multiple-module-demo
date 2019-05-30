package com.liujian.order;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(path="order/")
public class OrderController {

	@Value("${app.test.config1}")
	String aaa;
	
	Logger log = LoggerFactory.getLogger(OrderController.class);
	
	
	@Autowired
	VideoInfoRepository repository;
	
	@GetMapping("test")
	public String test() {
		return "Order Controller Test. config1:" + aaa;
	}

	@PostMapping("upload")
	public String upload(@RequestParam("file") MultipartFile file, @RequestParam("name") String name) throws Exception{
		
		Files.copy(file.getInputStream(), new File("/Users/liujian/Downloads/111").toPath(), StandardCopyOption.REPLACE_EXISTING);
		
		log.info("param name: {}" , name);
		return "OK";
	}
	
	@PostMapping("upload2")
	public String upload2(@RequestParam("file") MultipartFile file, @RequestPart("info") @Valid VideoInfo info) throws Exception{
		
		File dir = new File("/Users/liujian/Downloads/shake/");
		dir.mkdirs();
		String ext = "";
		try {
			ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		}catch(Exception e) {
			log.info("failed to get ext, originalFileName: {}", file.getOriginalFilename(), e) ;
		}

		Path temp = Files.createTempFile(dir.toPath(), "sh", ext, PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rw-rw-rw-")));
		
		
		
		Files.copy(file.getInputStream(), temp, StandardCopyOption.REPLACE_EXISTING);

		info.setThumbnailPath(temp.getFileName().toString());
		
		repository.save(info);
		
		log.info("videoinfo, name:{}, path: {}, size: {}" , info.getName(), info.getPath(), info.getSize());
		
		return "OK";
	}
	
	@GetMapping("files")
	public List<VideoInfo> listFiles(@RequestParam String userId, 
			@RequestParam(name = "page", defaultValue="0", required = false)int page, 
			@RequestParam(name = "size", defaultValue="50", required = false) int size,
			UriComponentsBuilder uriBuilder,
			HttpServletRequest request,
			HttpServletResponse response
			) {
		PageRequest pageable = PageRequest.of(page, size);
		
		Page<VideoInfo> files = repository.findByUserId(userId, pageable);
		log.info("pathInfo: {}, contextPath: {} ", request.getPathInfo(), request.getContextPath());
		
		
		files.forEach(f->{
			
			String url = ((UriComponentsBuilder)uriBuilder.clone()).replaceQueryParams(null).path("/order/files").pathSegment(f.getThumbnailPath()).build().toString();
			f.setThumbnailPath(url);
			
		});
		
		
		return files.getContent();
		
	}
	
	
	
	
}
