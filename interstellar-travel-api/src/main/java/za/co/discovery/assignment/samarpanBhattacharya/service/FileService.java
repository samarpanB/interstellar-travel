package za.co.discovery.assignment.samarpanBhattacharya.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.util.concurrent.ListenableFuture;

import net.minidev.json.JSONObject;

@Service
public interface FileService {

	/**
	 *
	 * @param file
	 * @param ipAddress
	 * @return
	 */
	public ListenableFuture<JSONObject> uploadExcelFile(final MultipartFile file,
		final String ipAddress);
}
