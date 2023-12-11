package utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.function.BiFunction;
import java.util.function.Function;

public class DijkstraShortestPathFinder<T> {
	private Map<T, Long> distFromStart;
	private Map<T, T> prevInPath;
	private BiFunction<T, T, Long> weightProvider;
	private Function<T, ? extends Collection<T>> neighborProvider;

	public DijkstraShortestPathFinder(Function<T, ? extends Collection<T>> neighborProvider) {
		this.weightProvider = (x, y) -> 1L;
		this.neighborProvider = neighborProvider;
		initializeMaps();
	}

	public DijkstraShortestPathFinder(BiFunction<T, T, Long> weightProvider,
			Function<T, ? extends Collection<T>> neighborProvider) {
		this.weightProvider = weightProvider;
		this.neighborProvider = neighborProvider;
		initializeMaps();
	}

	private void initializeMaps() {
		this.distFromStart = new HashMap<>();
		this.prevInPath = new HashMap<>();
	}

	public long find(T startNode, T endNode) {
		this.find(startNode, Optional.of(endNode));
		return this.getDistFromStart(endNode);
	}

	public Map<T, Long> findAll(T startNode) {
		this.find(startNode, Optional.empty());
		return new HashMap<>(this.distFromStart);
	}

	protected void find(T startNode, Optional<T> endNode) {
		this.distFromStart.put(startNode, 0L);
		var queue = new PriorityQueue<T>(
				(n1, n2) -> Long.compare(this.getDistFromStart(n1), this.getDistFromStart(n2)));
		this.distFromStart.forEach((k, v) -> queue.add(k));

		while (!queue.isEmpty()) {
			var curMinDistNode = queue.remove();
			var curMinDistFromStart = this.getDistFromStart(curMinDistNode);

			if (endNode.isPresent() && endNode.get().equals(curMinDistNode))
				break;

			for (T neighbor : this.neighborProvider.apply(curMinDistNode)) {
				var altDistFromStart = curMinDistFromStart + this.weightProvider.apply(curMinDistNode, neighbor);

				if (altDistFromStart < this.getDistFromStart(neighbor)) {
					this.distFromStart.put(neighbor, altDistFromStart);
					this.prevInPath.put(neighbor, curMinDistNode);

					// make the queue update itself
					queue.remove(neighbor);
					queue.add(neighbor);
				}
			}
		}
	}

	public long getDistFromStart(T node) {
		return this.distFromStart.getOrDefault(node, Long.MAX_VALUE);
	}
}
