package za.co.discovery.assignment.samarpanBhattacharya.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import net.minidev.json.JSONObject;
import za.co.discovery.assignment.samarpanBhattacharya.model.Route;
import za.co.discovery.assignment.samarpanBhattacharya.service.FileService;
import za.co.discovery.assignment.samarpanBhattacharya.service.InterstellarTravelService;

@RestController
@RequestMapping("/api/{[v1|current]}")
@EnableAsync
public class InterstellarRestController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private FileService fileService;

	@Autowired
	private InterstellarTravelService interstellarTravelService;

	@PostMapping("/bulk-upload-excel")
	public DeferredResult<ResponseEntity<?>> uploadExcelFile(
		@RequestPart(required = true, value = "file") final MultipartFile file,
		HttpServletRequest request) {

		logger.info("Initiated new bulk file upload" + file.getName());
		final DeferredResult<ResponseEntity<?>> deferredResult;
		deferredResult = new DeferredResult<>();
		JSONObject responseObj = new JSONObject();

		deferredResult.onTimeout(new Runnable() {
			@Override
			public void run() { // Retry on timeout
				responseObj.put("response", "Request Timed Out");
				deferredResult.setErrorResult(ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(responseObj));
			}
		});

		ListenableFuture<JSONObject> future = (ListenableFuture<JSONObject>) fileService.uploadExcelFile(file, request.getRemoteAddr());
		Futures.addCallback(future, new FutureCallback<JSONObject>() {
			@Override
			public void onSuccess(JSONObject result) {
				deferredResult.setResult(ResponseEntity.ok(result));
			}

			@Override
			public void onFailure(Throwable t) {
				deferredResult.setErrorResult(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(t));
			}
		});

		return deferredResult;
	}

	@GetMapping("/shortest-path")
	public DeferredResult<ResponseEntity<?>> calculateShortestPath(
		@RequestParam(required = true, defaultValue = "") String src,
		@RequestParam(required = true, defaultValue = "") String dest,
		HttpServletRequest request) {

		final DeferredResult<ResponseEntity<?>> deferredResult;
		deferredResult = new DeferredResult<>();
		JSONObject responseObj = new JSONObject();

		deferredResult.onTimeout(new Runnable() {
			@Override
			public void run() { // Retry on timeout
				responseObj.put("response", "Request Timed Out");
				deferredResult.setErrorResult(ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(responseObj));
			}
		});

		ListenableFuture<List<Route>> future = interstellarTravelService.calculateShortestPathAsync(src, dest);
		Futures.addCallback(future, new FutureCallback<List<Route>>() {
			@Override
			public void onSuccess(List<Route> result) {
				deferredResult.setResult(ResponseEntity.ok(result));
			}

			@Override
			public void onFailure(Throwable t) {
				deferredResult.setErrorResult(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(t));
			}
		});

		return deferredResult;
	}
}
