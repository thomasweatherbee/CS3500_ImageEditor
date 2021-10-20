package hw05.model.transforms;

import hw05.model.picture.IPicture;
import hw05.model.picture.IPixel;
import hw05.model.picture.RGBPixelImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Represents a transform that creates a mosaic, or stained glass, effect on an image.
 */
public class MosaicTransform extends ATransform {

  private final int seeds;
  private final List<int[]> seedColors;
  private final List<List<Integer>> seedNumber;

  /**
   * Creates a new mosaic transform.
   *
   * @param seeds number of seeds this mosaic transform should use (number of stained glass panes)
   */
  public MosaicTransform(int seeds) {
    if (seeds <= 0) {
      throw new IllegalArgumentException("Must have a positive number of seeds.");
    }
    this.seeds = seeds;
    this.seedColors = new ArrayList<>();
    this.seedNumber = new ArrayList<>();
  }

  @Override
  public IPicture apply(IPicture in) {
    if (seeds > in.getHeight() * in.getWidth()) {
      throw new IllegalArgumentException("Seeds cannot exceed number of pixels.");
    }
    initSeeds(in);
    return super.apply(in);
  }

  /**
   * Finds the list of colors corresponding to each seed (the average color of the seed's cluster).
   *
   * @param in picture to be operated on
   */
  protected void initSeeds(IPicture in) {
    seedColors.clear();
    Map<Integer, ArrayList<int[]>> seedMap = getSeedMap(in.getHeight(), in.getWidth());
    for (int i = 0; i < seeds; i++) {
      int[] sumColor = {0, 0, 0};
      for (int j = 0; j < seedMap.get(i).size(); j++) {
        int[] workingCoord = seedMap.get(i).get(j);
        for (int k = 0; k < 3; k++) {
          sumColor[k] += in.getRGBAt(workingCoord[0], workingCoord[1])[k];
        }
      }

      for (int j = 0; j < 3; j++) {
        sumColor[j] = sumColor[j] / seedMap.get(i).size();
      }

      seedColors.add(sumColor);
    }
  }

  /**
   * Randomly generates seed locations, initializes a list containing each pixel's corresponding
   * cluster number, and gets a map with all the pixels sorted by corresponding seed.
   *
   * @param height height of image to be processed
   * @param width  width of image to be processed
   * @return map of which pixels correspond to which seed
   */
  protected Map<Integer, ArrayList<int[]>> getSeedMap(int height, int width) {
    seedNumber.clear();
    List<int[]> seedLocations = generateSeeds(height, width);

    HashMap<Integer, ArrayList<int[]>> nearestSeedMap = new HashMap<>();

    for (int i = 0; i < width; i++) {
      seedNumber.add(new ArrayList<>());
      for (int j = 0; j < height; j++) {
        Map<Double, Integer> distances = new HashMap<>();
        for (int k = 0; k < seedLocations.size(); k++) {
          distances.put(Math.sqrt(
              Math.pow(seedLocations.get(k)[0] - i, 2) + Math.pow(seedLocations.get(k)[1] - j, 2)),
              k);
        }
        Double[] distanceArray = new Double[distances.keySet().size()];
        distances.keySet().toArray(distanceArray);

        Arrays.sort(distanceArray);
        seedNumber.get(i).add(distances.get(distanceArray[0]));
        nearestSeedMap.putIfAbsent(distances.get(distanceArray[0]), new ArrayList<>());
        nearestSeedMap.get(distances.get(distanceArray[0])).add(new int[]{i, j});
      }
    }

    return nearestSeedMap;
  }

  /**
   * Generates a random list of seed locations for a given height and width image.
   *
   * @param height height of image (max y value of a seed)
   * @param width  width of image (max x value of a seed)
   * @return list of seed locations
   */
  protected List<int[]> generateSeeds(int height, int width) {
    List<List<Boolean>> visited = new ArrayList<>();
    List<int[]> seedLocations = new ArrayList<>();
    Random r = new Random();

    for (int i = 0; i < width; i++) {
      visited.add(new ArrayList<>());
      for (int j = 0; j < height; j++) {
        visited.get(i).add(false);
      }
    }

    for (int i = 0; i < seeds; i++) {
      int randomX = r.nextInt(width);
      int randomY = r.nextInt(height);
      while (visited.get(randomX).get(randomY)) {
        randomX = r.nextInt(width);
        randomY = r.nextInt(height);
      }
      visited.get(randomX).set(randomY, true);
      seedLocations.add(new int[]{randomX, randomY});
    }

    return seedLocations;
  }

  @Override
  protected IPixel applyToPixel(IPicture in, int x, int y) {
    return new RGBPixelImpl(seedColors.get(seedNumber.get(x).get(y)));
  }
}