package za.co.discovery.assignment.samarpanBhattacharya.service;

import com.google.common.util.concurrent.ListenableFuture;
import java.util.List;
import za.co.discovery.assignment.samarpanBhattacharya.model.Route;

public interface InterstellarTravelService {

	/**
	 *
	 * @param source
	 * @param destination
	 * @return
	 */
	public List<Route> calculateShortestPath(final String source,
		final String destination);

	/**
	 *
	 * @param source
	 * @param destination
	 * @return
	 */
	public ListenableFuture<List<Route>> calculateShortestPathAsync(final String source,
		final String destination);
}
