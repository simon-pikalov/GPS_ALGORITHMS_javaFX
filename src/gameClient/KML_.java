package gameClient;

import Server.game_service;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class KML_ implements Runnable {

    private String fileName;
    private game_service server;
    private String date;
    private File OutputFile;
    private String content;

    //header file is the opening of the file required for every kml file
    private static final String headerFile =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
                    "  <kml xmlns=\"http://earth.google.com/kml/2.2\">\r\n" +
                    "    <Document>\r\n" +
                    "      <name> OPP Game </name>\n" +
                    "      <Style id=\"check-hide-children\">" +
                    "        <ListStyle>\r\n" +
                    "          <listItemType>checkHideChildren</listItemType>\r\n" +
                    "        </ListStyle>\r\n" +
                    "      </Style>\r\n" +
                    "      <styleUrl>#check-hide-children</styleUrl>";

    //footer file is the ending of the file required for every kml file
    private static final String footerFile = "</Document>\r\n" + "</kml>";

    // kml string representing a Terrorist object
    private static final String TerroristStyle =
            "      <Style id=\"Terrorist\">\r\n" +
                    "        <IconStyle>\r\n" +
                    "          <scale>1.0</scale>\r\n" +
                    "          <Icon>\r\n" +
                    "            <href>http://maps.google.com/mapfiles/kml/paddle/ylw-blank.png</href>\r\n" +
                    "          </Icon>\r\n" +
                    "        </IconStyle>\r\n" +
                    "      </Style>\n";

    // kml string representing a robot object
    private static final String robotStyle =
            "      <Style id=\"robot\">\r\n" +
                    "        <IconStyle>\r\n" +
                    "          <scale>1.0</scale>\r\n" +
                    "          <Icon>\r\n" +
                    "            <href>http://maps.google.com/mapfiles/kml/pal4/icon54.png</href>\r\n" +
                    "          </Icon>\r\n" +
                    "        </IconStyle>\r\n" +
                    "      </Style>\n";

    // kml string representing a place mark and a time stamp
    private static final String placeMark =
            "      <Placemark>\r\n" +
                    "        <TimeStamp>\r\n" +
                    "          <when>date</when>\r\n" +
                    "        </TimeStamp>" +
                    "        <styleUrl>#icon</styleUrl>\r\n" +
                    "        <Point>\r\n" +
                    "          <coordinates>(x,y)</coordinates>\r\n" +
                    "        </Point>\r\n" +
                    "      </Placemark>\n";

    /****************************************************************************************************************/
    // function implementation :

    /**
     * private constructor restricted to this class itself
     * building a kml file and insert the standard header kml and the style for every object
     *
     * @throws IOException
     */
    public KML_(game_service support, int stage) throws IOException {
        this.server = support;
        fileName = "data/" + stage + ".kml";
        OutputFile = new File(fileName);
        OutputFile.createNewFile();
        content = headerFile + TerroristStyle + robotStyle;
        writeToFile(content);
    }

    /**
     * this method updates the data to the current date in a kml format
     */
    private void getKmlFormatDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date date = new Date();
        this.date = formatter.format(date);
    }

    /**
     * Writes the log of the game in a KML format to a file
     * this method takes the string that the class built during the game and adds it to the kml file.
     * it also adds the footer of the kml file
     *
     * @param string
     * @throws IOException
     */
    public void writeToFile(String string) throws IOException {
        PrintWriter pw = new PrintWriter(OutputFile);
        pw.println(string);
        pw.close();
    }

    /**
     * add a place mark to the kml file
     *
     * @param p x and y coordinate of the place mark
     * @icon to pick the place mark style
     */
    private String addPlaceMark(utils.Point3D p, String icon) {
        String placeMark = KML_.placeMark;
        placeMark = placeMark.replace("(x,y)", p.x() + "," + p.y());
        placeMark = placeMark.replace("date", date); //replace date to the current data kml format
        placeMark = placeMark.replace("icon", icon); //replace the word "icon" to specific icon match the current object
        return placeMark;
    }

    /**
     * and at the end write all to file.
     */
    @Override
    public void run() {
        int ran=0;
        while (server.isRunning()&&ran<100) {
            try {
                content += screenShot();
                writeToFile(content);
                Thread.sleep(100);
                ran+=10;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        content += footerFile;
        try {
            writeToFile(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * convert the positions of all the robots and fruits in kml format to adds it to the content
     */
    private String screenShot() throws JSONException {
        String PlaceMark = "";
        int sum = 0;
        getKmlFormatDate(); //update current date
        //fruits
        String TEMP = server.getFruits().toString();
        JSONArray temp2 = new JSONArray(TEMP);
        sum = 0;
        while (sum < temp2.length()) {
            JSONObject Fruit = new JSONObject(temp2.get(sum).toString());
            JSONObject f = new JSONObject(Fruit.getJSONObject("Fruit").toString());
            utils.Point3D pos = new utils.Point3D(f.getString("pos").toString());
            PlaceMark += this.addPlaceMark(pos, "Terrorist");
            sum++;
        }
        //robots
        String TEMPR = server.getRobots().toString();
        JSONArray temp2R = new JSONArray(TEMPR);
        sum = 0;
        while (sum < temp2R.length()) {
            JSONObject RobotT = new JSONObject(temp2R.get(sum).toString());
            JSONObject Robot = new JSONObject(RobotT.getJSONObject("Robot").toString());
            utils.Point3D Robotpos = new utils.Point3D(Robot.getString("pos").toString());
            PlaceMark += this.addPlaceMark(Robotpos, "robot");
            sum++;
        }
        return PlaceMark;
    }
}

