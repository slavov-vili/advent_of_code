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
        List<List<Integer>> layers = reassembleImage(input, 25, 6);

        int solutionA = solveA(layers);
        System.out.println("Solution A: " + solutionA);

        List<Integer> solutionB = solveB(layers);
        System.out.println("Solution B: ");
        convertLayerToImage(solutionB, 25, 6).forEach(row -> System.out.println(row));
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

    public static List<Integer> solveB(List<List<Integer>> layers) {
        List<Integer> decryptedImage = new ArrayList<>();
        for (int pixelIdx = 0; pixelIdx < layers.get(0).size(); pixelIdx++) {
            int curPixelValue = 2;
            for (List<Integer> layer : layers) {
                if (layer.get(pixelIdx) != 2) {
                    curPixelValue = layer.get(pixelIdx);
                    break;
                }
            }
            decryptedImage.add(curPixelValue);
        }
        return decryptedImage;
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

    protected static List<List<Integer>> convertLayerToImage(List<Integer> layer, int imageWidth, int imageHeight) {
        List<Integer> layerCopy = new ArrayList<>(layer);
        List<List<Integer>> image = new ArrayList<>(imageHeight);
        int curLayerStartIdxInclusive = 0;
        while (curLayerStartIdxInclusive < layerCopy.size()) {
            int curLayerEndIdxExclusive = curLayerStartIdxInclusive + imageWidth;
            image.add(layerCopy.subList(curLayerStartIdxInclusive, curLayerEndIdxExclusive));
            curLayerStartIdxInclusive = curLayerEndIdxExclusive;
        }
        return image;
    }

    protected static List<Integer> getInput() {
        String[] pixelsString = AdventOfCodeUtils.readInputLines(Day08Main.class).get(0).split("");
        List<Integer> pixelsInt = new ArrayList<>(pixelsString.length);
        for (String pixelString : pixelsString)
            pixelsInt.add(Integer.parseInt(pixelString));
        return pixelsInt;
    }
}
