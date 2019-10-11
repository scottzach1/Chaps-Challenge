package nz.ac.vuw.ecs.swen225.a3.persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipFile;

public class LevelManager {

  private static ArrayList<String> levelDescriptions = new ArrayList<>();
  public static int currentLevel = 0;
  @SuppressWarnings("rawtypes")
  static Set<Class> classSet = new HashSet<>();

  /**
   * For each zip file in the levels/ directory: load the JSON string description of the level, the
   * classes and the assets.
   *
   * @param assetManager the current asset manager.
   */
  public static void loadLevels(AssetManager assetManager) {
    //Load level description and assets
    File[] folder = new File("src/levels/").listFiles();
    List<File> files = new ArrayList<>();
    if (folder == null) {
      return;
    }
    for (File file : folder) {
      files.add(file);
    }
    Collections.sort(files);
    for (File f : files) {
      try {
        try (ZipFile zf = new ZipFile(f.getAbsolutePath())) {
          zf.stream().filter(p -> p.getName().contains(".txt"))
              .forEach(s -> {
                try {
                  levelDescriptions.add(
                      new BufferedReader(new InputStreamReader(zf.getInputStream(s))).readLine());
                } catch (IOException e) {
                  throw new Error("Failed to load level from " + f.getAbsolutePath());
                }
              });

          // Load Assets
          zf.stream().filter(p -> p.getName().contains(".png"))
              .forEach(s -> {
                try {
                  assetManager.loadAssetFromInputStream(zf.getInputStream(s), s.getName());
                } catch (Exception e) {
                  System.out.println("Error loading assets from file: " + e);
                }
              });
        }
      } catch (Exception e) {
        System.out.println("Error opening Zip: " + e);
      }
    }

    // Load classes
    try {

      folder = new File("src/levels/").listFiles();
      files.clear();
      for (File file : files) {
        files.add(file);
      }
      for (File f : files) {
        JarFile jarFile = new JarFile(f);
        Enumeration<JarEntry> e = jarFile.entries();

        URL[] urls = {new URL("jar:file:" + jarFile.getName() + "!/")};
        URLClassLoader cl = URLClassLoader.newInstance(urls);

        while (e.hasMoreElements()) {
          JarEntry je = e.nextElement();
          if (je.isDirectory() || !je.getName().endsWith(".class")) {
            continue;
          }
          // -6 because of .class
          String className = je.getName().substring(0, je.getName().length() - 6);
          className = className.replace('/', '.');
          Class<?> c = cl.loadClass(className);
          classSet.add(c);
        }
        jarFile.close();
      }

    } catch (Exception e) {
      System.out.println("Error loading classes from zip: " + e);
    }

  }

  /**
   * Get current level counter.
   *
   * @return current level integer.
   */
  public static int getCurrentLevelInt() {
    return currentLevel;
  }

  /**
   * Gets the current level as a string.
   *
   * @param level the level number.
   * @return the level as a string.
   */
  public static String getCurrentLevelStream(int level) {
    return levelDescriptions.get(level);
  }

  /**
   * Gets the total number of levels.
   *
   * @return the number of levels.
   */
  public static int getNumLevels() {
    return levelDescriptions.size();
  }
}
