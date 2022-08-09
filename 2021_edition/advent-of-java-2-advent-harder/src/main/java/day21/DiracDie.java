package day21;

import java.util.HashMap;
import java.util.Map;

public class DiracDie {

	public static Map<Integer, Integer> roll() {
		Map<Integer, Integer> outcomeToCount = new HashMap<>();
		for (int i = 1; i <= 3; i++)
			for (int j = 1; j <= 3; j++)
				for (int k = 1; k <= 3; k++) {
					int curOutcome = i + j + k;
					int curCount = outcomeToCount.getOrDefault(curOutcome, 0);
					outcomeToCount.put(curOutcome, curCount + 1);
				}
		
		return outcomeToCount;
	}
}
