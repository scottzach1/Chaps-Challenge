package nz.ac.vuw.ecs.swen225.a3.persistence;

import java.io.*;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class LevelManager {
    static ArrayList<String> levelDescriptions = new ArrayList<>();
    public static int currentLevel = 0;
    public static Set<Class> classSet = new HashSet<>();

    public static void loadLevels(){
        //Load level description and assets
        File folder = new File("src/levels/");
        List<File> files = Arrays.asList(folder.listFiles());
        Collections.sort(files);
        for(File f : files){
            try {
                ZipFile zf = new ZipFile(f.getAbsolutePath());
                try {
                    zf.stream().filter(p -> p.getName().contains(".txt"))
                            .forEach(s -> {
                                try{
                                    levelDescriptions.add(new BufferedReader(new InputStreamReader(zf.getInputStream(s))).readLine());
                                }catch (IOException e){
                                    throw new Error("Failed to load level from " + f.getAbsolutePath());
                                }});

                    // Load Assets
                    zf.stream().filter(p -> p.getName().contains(".png"))
                            .forEach(s -> {
                                try {
                                    AssetManager.loadAssetFromInputStream(zf.getInputStream(s),s.getName());
                                }catch (Exception e){}
                                });
                }finally {
                    zf.close();
                }
            }
            catch(Exception e){}
        }

        // Load classes
        try {

            folder = new File("src/levels/");
            for(File f : folder.listFiles()){
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
                    Class c = cl.loadClass(className);
                    classSet.add(c);
                }
            }

        }catch(Exception e){}

    }

    /**
     * Get current level counter.
     *
     * @return current level integer.
     */
    public static int getCurrentLevelInt() {
        return currentLevel;
    }


    public static String getCurrentLevelStream(int level) {
        return levelDescriptions.get(level);
    }

    public static int getNumLevels() {
        return levelDescriptions.size();
    }
}
