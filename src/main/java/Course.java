/**
 * Class qui represente une course avec ses coordonnees de depart, ses coordonnees d'arrivee, le temps (etape) de depart le plus tot,
 * le temps d'arrivee le plus tard et le numero de la course pour l'identifier a la fin de la simulation.
 */
public class Course {
    private int[] coordinatesStart;
    private int[] coordinatesEnd;
    private int earliest_start;
    private int latest_finish;
    private int numeroCourse;

    /**
     * Constructeur de la class.
     *
     * @param args         un tableau d'{@link Integer}, de dimension 1, contenant les donnees sur les coordonnees de depart, les coordonnees d'arrivee,
     *                     le temps (etape) de depart le plus tot et le temps d'arrivee le plus tard
     * @param numeroCourse un {@link Integer} identifiant la course.
     */
    public Course(int[] args, int numeroCourse) {
        if (args.length != 6)
            throw new IllegalArgumentException("args provided to create a `Course` object has not 6 elements.");
        coordinatesStart = new int[2];
        coordinatesStart[0] = args[0];
        coordinatesStart[1] = args[1];
        coordinatesEnd = new int[2];
        coordinatesEnd[0] = args[2];
        coordinatesEnd[1] = args[3];
        this.earliest_start = args[4];
        this.latest_finish = args[5];
        this.numeroCourse = numeroCourse;
    }

    /**
     * Methode qui renvoie la x-coordonnees de depart de la course.
     *
     * @return un {@link Integer} representant la x-coordonnees de depart de la course.
     */
    public int getX_start() {
        return coordinatesStart[0];
    }

    /**
     * Methode qui renvoie la y-coordonnees de depart de la course.
     *
     * @return un {@link Integer} representant la y-coordonnees de depart de la course.
     */
    public int getY_start() {
        return coordinatesStart[1];
    }

    /**
     * Methode qui renvoie la x-coordonnees de fin de la course.
     *
     * @return un {@link Integer} representant la x-coordonnees de fin de la course.
     */
    public int getX_end() {
        return coordinatesEnd[0];
    }

    /**
     * Methode qui renvoie la y-coordonnees de fin de la course.
     *
     * @return un {@link Integer} representant la y-coordonnees de fin de la course.
     */
    public int getY_end() {
        return coordinatesEnd[1];
    }

    /**
     * Methode qui renvoie le plus tot temps (etape) de depart de la course.
     *
     * @return un {@link Integer} representant le plus tot temps (etape) de depart de la course.
     */
    public int getEarliest_start() {
        return earliest_start;
    }

    /**
     * Methode qui renvoie le numero de la course afin de pouvoir l'identifier.
     *
     * @return un {@link Integer} permettant d'identifier la course.
     */
    public int getNumeroCourse() {
        return numeroCourse;
    }

    /**
     * Methode qui calcule la distance de la course.
     *
     * @return un {@link Integer} egale a la distance entre les coordonnees de depart et de fin de la course.
     */
    public int distance() {
        return Math.abs(coordinatesStart[0] - coordinatesStart[1]) + Math.abs(coordinatesEnd[0] - coordinatesEnd[1]);
    }
}