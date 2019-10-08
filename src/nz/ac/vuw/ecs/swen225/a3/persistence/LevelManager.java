package nz.ac.vuw.ecs.swen225.a3.persistence;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class LevelManager {

    static ArrayList<InputStream> levelDescriptions = new ArrayList<>();

    public static void loadLevels(){
        //Load strings
        File folder = new File("src/levels/");
        for(File f : folder.listFiles()){
            try {
                ZipFile zf = new ZipFile(f.getAbsolutePath());
                try {
                    zf.stream().filter(p -> p.getName().contains(".txt"))
                            .forEach(s -> {
                                try{
                                    levelDescriptions.add((zf.getInputStream(s)));
                                }catch (IOException e){
                                    throw new Error("Failed to load level from " + f.getAbsolutePath());
                                }});
                    levelDescriptions.forEach(s -> {
                        try {
                            System.out.println(new BufferedReader(new InputStreamReader(s)).readLine());
                        } catch (Exception e) {
                        }
                    });
                }finally {
                    zf.close();
                }
            }
            catch(Exception e){}
        }

    }
}
