package day08;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.AdventOfCodeUtils;
import utils.ListUtils;

public class Day08Main {

    public static void main(String[] args) {
        List<Integer> input = getInput();
        // TODO: switch from map to list? list idx = layer id anyway
        Map<Integer, List<List<Integer>>> layerIdToPixelMatrix = reassembleImage(input, 25, 6);

        int solutionA = solveA(layerIdToPixelMatrix);
        System.out.println("Solution A: " + solutionA);

    }

    public static int solveA(Map<Integer, List<List<Integer>>> layerIdToPixelMatrix) {
        Map<Integer, Integer> layerIdToCount0 = new HashMap<>(layerIdToPixelMatrix.size());
        for (Integer curLayerId : layerIdToPixelMatrix.keySet())
            layerIdToCount0.put(curLayerId, ListUtils.countInMatrix(layerIdToPixelMatrix.get(curLayerId), 0));

        int minCount0LayerId = layerIdToCount0.keySet().stream()
                .min(Comparator.comparing(layerId -> layerIdToCount0.get(layerId))).get();

        return ListUtils.countInMatrix(layerIdToPixelMatrix.get(minCount0LayerId), 1)
                * ListUtils.countInMatrix(layerIdToPixelMatrix.get(minCount0LayerId), 2);
    }

    protected static Map<Integer, List<List<Integer>>> reassembleImage(List<Integer> pixels, int imageWidth,
            int imageHeight) {
        int imageSize = imageWidth * imageHeight;
        Map<Integer, List<List<Integer>>> layerToPixelMatrix = new HashMap<>();
        int curLayerId = 0;
        int curLayerStartIdxInclusive = curLayerId;
        while (curLayerStartIdxInclusive < pixels.size()) {
            int curLayerEndIdxExclusive = curLayerStartIdxInclusive + imageSize;
            List<Integer> curLayerPixels = pixels.subList(curLayerStartIdxInclusive, curLayerEndIdxExclusive);
            layerToPixelMatrix.put(curLayerId, extractPixelMatrix(curLayerPixels, imageWidth, imageHeight));
            curLayerId++;
            curLayerStartIdxInclusive = curLayerEndIdxExclusive;
        }
        return layerToPixelMatrix;
    }

    protected static List<List<Integer>> extractPixelMatrix(List<Integer> pixels, int matrixWidth, int matrixHeight) {
        List<Integer> pixelsCopy = new ArrayList<>(pixels);
        List<List<Integer>> matrix = new ArrayList<>(matrixHeight);
        int curRowStartIdxInclusive = 0;
        while (curRowStartIdxInclusive < pixelsCopy.size()) {
            int curRowEndIdxExclusive = curRowStartIdxInclusive + matrixWidth;
            matrix.add(pixelsCopy.subList(curRowStartIdxInclusive, curRowEndIdxExclusive));
            curRowStartIdxInclusive = curRowEndIdxExclusive;
        }
        return matrix;
    }

    protected static List<Integer> getInput() {
        String[] pixelsString = AdventOfCodeUtils.readClasspathFileLines(Day08Main.class, "input.txt").get(0).split("");
        List<Integer> pixelsInt = new ArrayList(pixelsString.length);
        for (String pixelString : pixelsString)
            pixelsInt.add(Integer.parseInt(pixelString));
        return pixelsInt;
    }
}
