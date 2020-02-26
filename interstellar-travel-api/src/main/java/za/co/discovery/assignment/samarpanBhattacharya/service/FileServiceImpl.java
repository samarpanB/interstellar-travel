package za.co.discovery.assignment.samarpanBhattacharya.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.google.common.net.UrlEscapers;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import net.minidev.json.JSONObject;
import za.co.discovery.assignment.samarpanBhattacharya.dao.PlanetDao;
import za.co.discovery.assignment.samarpanBhattacharya.dao.RouteDao;
import za.co.discovery.assignment.samarpanBhattacharya.model.Planet;
import za.co.discovery.assignment.samarpanBhattacharya.model.Route;

@Service("fileService")
class FileServiceImpl implements FileService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final ListeningExecutorService service;
	private static final File TEMP_DIRECTORY = new File(System.getProperty("java.io.tmpdir"));

	private enum SheetIndex {
		PLANET, ROUTE, TRAFFIC
	}

	@Autowired
	private PlanetService planetService;

	@Autowired
	private RouteService routeService;

	@Autowired
	private PlanetDao planetDao;

	@Autowired
	private RouteDao routeDao;

	public FileServiceImpl() {
		service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
	}

	@Override
	public ListenableFuture<JSONObject> uploadExcelFile(final MultipartFile file, final String ipAddress) {
		ListenableFuture<JSONObject> result;
		result = (ListenableFuture<JSONObject>) service.submit(new Callable<JSONObject>() {
			@Override
			public JSONObject call() throws Exception {
				String result = null;
				if (file != null) {
					String name = UrlEscapers.urlFragmentEscaper().escape(file.getOriginalFilename());
					logger.info("Name of file submitted is: " + name);

					String srcLocation = "interstellar-transport";
					String dirId = java.util.UUID.randomUUID().toString();
					// create folder on server
					File serverBaseDir = new File(TEMP_DIRECTORY + "/" + srcLocation + "/" + dirId);

					if (!serverBaseDir.exists()) {
						serverBaseDir.mkdirs();
					}

					if (!file.isEmpty()) {
						try {
							String tmpFilePath = serverBaseDir + "/" + file.getOriginalFilename();
							file.transferTo(new File(tmpFilePath));
							logger.info("You successfully uploaded " + name + " into " + name + "-uploaded !");
							result = name + "uploaded successfully to: " + serverBaseDir + "/" + file.getOriginalFilename();
							routeDao.deleteAll();
							planetDao.deleteAll();
							importDataFromExcel(tmpFilePath);
						} catch (Exception e) {
							logger.info("You failed to upload " + name + " => " + e.getMessage());
						}
					} else {
						logger.info("You failed to upload " + name);
					}
				}
				JSONObject response = new JSONObject();
				response.put("response", result);
				return response;
			}
		});

		return result;
	}

	private void importDataFromExcel(final String filePath) {
		try {
			File file = new File(filePath);
			FileInputStream inputStream = new FileInputStream(file);
			@SuppressWarnings("resource")
			Workbook workbook = new XSSFWorkbook(inputStream);

			Sheet planetData = workbook.getSheetAt(SheetIndex.PLANET.ordinal());
			importPlanetData(planetData);

			Sheet routeData = workbook.getSheetAt(SheetIndex.ROUTE.ordinal());
			Sheet trafficData = workbook.getSheetAt(SheetIndex.TRAFFIC.ordinal());
			importRouteAndTrafficData(routeData, trafficData);
		} catch (FileNotFoundException e) {
			logger.info(e.getMessage());
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
		} catch (IOException e) {
			logger.info(e.getMessage());
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
		} catch (Exception e) {
			logger.info("Failed to import excel data " + " => " + e.getMessage());
		}
	}

	private void importPlanetData(Sheet planetData) {

		List<Planet> planetList = new ArrayList<Planet>();

		for (int i = 1; i < planetData.getPhysicalNumberOfRows(); i++) {

			Planet planetName = new Planet();
			XSSFRow row = (XSSFRow) planetData.getRow(i);
			planetName.setNode(row.getCell(0).getStringCellValue());
			planetName.setName(row.getCell(1).getStringCellValue());
			planetList.add(planetName);
		}
		planetService.addPlanets(planetList);
		logger.info("Planet Names imported in DB");
	}

	private void importRouteAndTrafficData(Sheet routeData, Sheet trafficData) {
		List<Route> routeList = new ArrayList<Route>();
		for (int i = 1; i < routeData.getPhysicalNumberOfRows(); i++) {

			Route route = new Route();
			XSSFRow row = (XSSFRow) routeData.getRow(i);
			XSSFRow trafficRow = (XSSFRow) trafficData.getRow(i);
			Planet planetSource = planetService.getPlanetByNodeName(row.getCell(1).getStringCellValue());
			if (planetSource == null) // to handle the case where there is no entry in planet table
			{
				continue;
			}
			route.setSource(planetSource);
			Planet planetDestination = planetService.getPlanetByNodeName(row.getCell(2).getStringCellValue());
			if (planetDestination == null) {
				continue;
			}
			route.setDestination(planetDestination);
			route.setDistance((float) row.getCell(3).getNumericCellValue());
			route.setTraffic((float) trafficRow.getCell(3).getNumericCellValue());
			routeList.add(route);
		}
		routeService.addRoutes(routeList);
		logger.info("Routes imported in DB");
	}

}
