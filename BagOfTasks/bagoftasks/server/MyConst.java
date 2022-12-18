
package bagoftasks.server;

import java.math.MathContext;

public class MyConst {
    public static MathContext MC = new MathContext(15);
    public static int PRECISION_FRACTAL = 17;
    public static int MODE = 2;
    public static int MODE_4_SIZE = 10;
    /**************
     MODE =
       1-> un pixel par task
       2-> une colonne par task
       3-> toute l'image pour une task (=sans RMI)
       4-> paquet de pixel sans forme particuliere par task (taille = MODE_4_SIZE)
     **************/
}
