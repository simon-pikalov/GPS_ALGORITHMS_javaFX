public class test {


    public static void main(String[] args) {
        String a = "MouseEvent [source = Group@610b1fd9, target = Text[id=19, text=\"19\", x=412.8685524123076, y=518.5600000002182, alignment=LEFT, origin=BASELINE, boundsType=LOGICAL, font=Font[name=Verdana Bold, family=Verdana, style=Bold, size=20.0], fontSmoothingType=GRAY, fill=0xff0000ff], eventType = MOUSE_CLICKED, consumed = false, x = 429.0, y = 504.0, z = 2.2737367544323206E-13, button = PRIMARY, pickResult = PickResult [node = Text[id=19, text=\"19\", x=412.8685524123076, y=518.5600000002182, alignment=LEFT, origin=BASELINE, boundsType=LOGICAL, font=Font[name=Verdana Bold, family=Verdana, style=Bold, size=20.0], fontSmoothingType=GRAY, fill=0xff0000ff], point = Point3D [x = 429.0, y = 509.1856000000022, z = 2.2737367544323206E-13], distance = 2015.3074360871942]\n";
        System.out.println(a.indexOf('[',13));
        int v = a.indexOf('[',13);
        System.out.println(a.indexOf('=',v));
        System.out.println(a.indexOf(',',v));
    }
}
