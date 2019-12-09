package day08;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.AdventOfCodeUtils;

public class Day08Main {

    public static void main(String[] args) {
        List<Integer> input = getInput();
        // TODO: switch from map to list? list idx = layer id anyway
        List<List<Integer>> layers = reassembleImage(input, 25, 6);

        int solutionA = solveA(layers);
        System.out.println("Solution A: " + solutionA);

    }

    public static int solveA(List<List<Integer>> layers) {
        Map<Integer, Integer> layerIdToCount0 = new HashMap<>(layers.size());
        for (int i = 0; i < layers.size(); i++)
            layerIdToCount0.put(i, Collections.frequency(layers.get(i), 0));

        int minCount0LayerId = layerIdToCount0.keySet().stream()
                .min(Comparator.comparing(layerId -> layerIdToCount0.get(layerId))).get();

        return Collections.frequency(layers.get(minCount0LayerId), 1)
                * Collections.frequency(layers.get(minCount0LayerId), 2);
    }

    protected static List<List<Integer>> reassembleImage(List<Integer> pixels, int imageWidth, int imageHeight) {
        List<Integer> pixelsCopy = new ArrayList<>(pixels);
        int imageSize = imageWidth * imageHeight;
        List<List<Integer>> layerToPixelMatrix = new ArrayList<>();
        int curLayerStartIdxInclusive = 0;
        while (curLayerStartIdxInclusive < pixels.size()) {
            int curLayerEndIdxExclusive = curLayerStartIdxInclusive + imageSize;
            layerToPixelMatrix.add(pixelsCopy.subList(curLayerStartIdxInclusive, curLayerEndIdxExclusive));
            curLayerStartIdxInclusive = curLayerEndIdxExclusive;
        }
        return layerToPixelMatrix;
    }

    protected static List<Integer> getInput() {
        String[] pixelsString = AdventOfCodeUtils.readClasspathFileLines(Day08Main.class, "input.txt").get(0).split("");
        List<Integer> pixelsInt = new ArrayList<>(pixelsString.length);
        for (String pixelString : pixelsString)
            pixelsInt.add(Integer.parseInt(pixelString));
        return pixelsInt;
    }
}
