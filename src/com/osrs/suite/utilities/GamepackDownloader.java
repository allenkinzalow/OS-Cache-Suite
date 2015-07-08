package com.osrs.suite.utilities;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

/**
 * Created by Allen Kinzalow on 5/13/2015.
 */
public class GamepackDownloader {

    static int[] worldList = new int[]{1,8,16,26,81,82,83,84};
    private static int WORLD = 1;
    private static final String DIR = "./data/client/";
    private static String IP = "oldschool" + WORLD + ".runescape.com";
    private static final String LOAD_URL = "http://oldschool" + WORLD + ".runescape.com/j1";

    private HashMap<String,String> parameters = new HashMap<>();

    public GamepackDownloader() {
        WORLD = worldList[(int)(Math.random() * worldList.length)];
    }

    public void download() {
        try {
            String gamePackUrl = fetchPageParameters();
            if (gamePackUrl != "") {
                int gamepackID = Integer.valueOf(gamePackUrl.substring(gamePackUrl.indexOf("gamepack_") + "gamepack_".length(), gamePackUrl.indexOf(".jar")));
                writeParameters(gamepackID);
                downloadGamePack(gamePackUrl);
                System.out.println("Done downloading gamepack!");
            } else
                System.out.println("Error downloading gamepack!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Fetch the page parameters and populate a hashmap.
     * @return  Gamepack url.
     */
    private String fetchPageParameters() {
        try {
            String packUrl = LOAD_URL;
            String gamePackUrl = "";
            IP = packUrl.substring(packUrl.indexOf("http://") + "http://".length(), packUrl.indexOf("/j1"));
            for (String line : fetchPageDetails(packUrl)) {
                if (line.contains("<param name=")) {
                    String key = line.split("<param name=\"")[1].split("\" ")[0];
                    String value = line.split("value=\"")[1].split("\">'")[0];
                    value = value.replaceAll("\">", "");
                    if (value.isEmpty())
                        value = "";
                    //System.out.println("[" + key + ", " + value + "]");
                    System.out.println("parameters.put(" + key + ", " + value + ");");
                    parameters.put(key, value);
                }
                if (line.contains("archive=")) {
                    System.out.println(line);
                    gamePackUrl = line.substring(line.indexOf("archive=") + "archive=".length()).replaceAll("\'\\);", "").trim();
                    gamePackUrl = gamePackUrl.substring(0, gamePackUrl.indexOf(".jar") + ".jar".length());
                    gamePackUrl = "http://" + IP + "/" + gamePackUrl;
                    System.out.println("GamePack: " + gamePackUrl);
                }
            }
            return gamePackUrl;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Fetches the current parameters from the specified address
     * @return
     * @throws java.net.MalformedURLException
     * @throws java.io.IOException
     */
    private ArrayList<String> fetchPageDetails(String packUrl) throws MalformedURLException, IOException {
        ArrayList<String> pageSource = new ArrayList<String>();
        URL urlToLoad = new URL(packUrl);
        HttpURLConnection urlConn = (HttpURLConnection)urlToLoad.openConnection();
        urlConn.setRequestMethod("GET");
        urlConn.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        urlConn.addRequestProperty("Cookie", "rs_splash_count=1; loggedIn=true; slangpref=0; JXDOB=19960320; rememberme=YOQhL2Hdub9pbOWBi*ofEg; _vis_opt_s=1%7C; _vwo_uuid=28CF2F0A7E344DD4AC9AFEB2F4176CFC; _vis_opt_exp_230_combi=1; _vis_opt_exp_230_goal_2=1; _vis_opt_exp_213_goal_1=1; settings=Ymi9Liy-EKD*Cxh6XInci7jdO6eoWyjw-qztQX1ZNds; serverlist_order=WMLPA; JXFLOWCONTROL=4671986212872515780; JXTRACKING=01182680500000014BF4998D1F; JXWEBUID=3F110CFC079B70004382D8DE70FEF4F1208739713A3A4F3273C4602AF81E26AE9E0A902AE82803AFB4EBE75DC2137027; JXADDINFO=DBXPZaBPotHnzeZldoHBTwfnZ2q0w*hCiUVkcOs; _ga=GA1.2.1528983156.1420342684; _dc_gtm_UA-2058817-2=1; __utmt_~1=1; __utmt=1; __utma=263125903.1528983156.1420342684.1425740350.1425740350.1; __utmb=263125903.4.10.1425740350; __utmc=263125903; __utmz=263125903.1425740350.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none)");
        urlConn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.76 Safari/537.36");
        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null)
            pageSource.add(line);
        reader.close();
        return pageSource;
    }

    /**
     * Write the parameters.
     */
    private void writeParameters(int gamepackID) {
        try {
            FileWriter writer = new FileWriter(new File(DIR + "parameters_" + gamepackID + ".txt"));
            for(String key : parameters.keySet())
                writer.write("[" + key + ", " + parameters.get(key) + "]\n");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Download the gamepack.
     * @param packUrl
     */
    private void downloadGamePack(String packUrl) {
        String packName = packUrl.substring(packUrl.indexOf("gamepack"));
        try {
            System.out.println("Downloading GamePack: " + packUrl);
            URL url = new URL(packUrl);
            InputStream is = new BufferedInputStream(url.openStream());
            OutputStream os = new BufferedOutputStream(new FileOutputStream(new File(DIR + packName)));
            int read1;
            while ((read1 = is.read()) != -1) {
                os.write(read1);
            }
            os.close();


            Map<String, ByteBuffer> classes = new HashMap<>();
            JarInputStream jis = new JarInputStream(new FileInputStream(new File(DIR + packName)));
            byte[] buffer = new byte[5 * 1024 * 1024];
            int read = 0, in = 0;
            for (JarEntry entry = jis.getNextJarEntry(); entry != null; entry = jis.getNextJarEntry()) {
                String name = entry.getName();
                if (!name.endsWith(".class")) {
                    continue;
                }

                read = in = 0;
                while (read < buffer.length && (in = jis.read(buffer, read, buffer.length - read)) != -1) {
                    read += in;
                }

                ByteBuffer data = ByteBuffer.allocate(read);
                data.put(buffer, 0, read).flip();
                classes.put(name, data);
            }
            writeJar(classes, DIR + packName);
        } catch (Exception e) {
            System.err.println("Error retrieving gamepack(" + packName + ") ... try again.");
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Write the jar file to a file.
     * @param classes
     * @param loc
     * @throws IOException
     */
    private void writeJar(Map<String, ByteBuffer> classes, String loc) throws IOException {

        try (JarOutputStream jos = new JarOutputStream(new BufferedOutputStream(new FileOutputStream(new File(loc))))) {
            for (Map.Entry<String, ByteBuffer> clazz : classes.entrySet()) {
                ZipEntry zip = new ZipEntry(clazz.getKey());

                jos.putNextEntry(zip);
                jos.write(clazz.getValue().array());
            }
        } catch (IOException e) {
            System.err.println("Error writing classes to jar - please ensure this program has write permissions.");
            throw new IOException(e);
        }
    }

}
