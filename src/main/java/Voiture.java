import java.util.LinkedList;

/**
 * Class qui represente les voitures dans la simulation. On peut considerer la voiture comme un taxi.
 */
public class Voiture {

    private final int[] coordinates;
    private final LinkedList<Integer> numeroDesCourse;
    private final int maxEtapes;
    private int etapes;
    private int nombreDeCourse;

    /**
     * Constructeur qui creer une voiture avec un numero unique (matricule).
     *
     * @param maxEtapes permet de definir le maximum d'etapes que la voitures peut parcourir.
     */
    public Voiture(int maxEtapes) {
        coordinates = new int[2];
        coordinates[0] = 0;
        coordinates[1] = 0;
        nombreDeCourse = 0;
        numeroDesCourse = new LinkedList<>();
        this.maxEtapes = maxEtapes;
        etapes = 0;
    }

    /**
     * Methode qui retourne les coordonnees de la voitures.
     *
     * @return un tableau d'{@link Integer}, de 1 dimension et de taille 2, permettant de determiner la position de la voiture au long de la simulation.
     */
    public int[] getCoordinates() {
        return coordinates;
    }

    /**
     * Methode permettant de modifier les coordonnees de la voiture. Utile pour mettre a jour la position de la voiture
     *
     * @param x un {@link Integer} contenant la x-coordonnee de la position actuel de la voiture.
     * @param y un {@link Integer} contenant la y-coordonnee de la position actuel de la voiture.
     */
    public void setCoordinates(int x, int y) {
        coordinates[0] = x;
        coordinates[1] = y;
    }

    /**
     * Methode permettant de sauvegarder le numero d'une course dans une {@link LinkedList}
     *
     * @param course                  un {@link Course} qui represente la course qui sera realisee.
     * @param distance_voiture_course fournit la distance a parcourir par la voiture pour atteindre le point de
     *                                depart de la course.
     */
    public void realiserCourse(Course course, int distance_voiture_course) {
        this.numeroDesCourse.add(course.getNumeroCourse());
        this.nombreDeCourse++;
        // Si il y a un temps d'attente avant de realiser la course, alors ajoute ce temps
        if (course.getEarliest_start() - (etapes + distance_voiture_course) > 0)
            etapes += course.getEarliest_start() - (etapes + distance_voiture_course);
        this.etapes += distance_voiture_course + course.distance();
        this.setCoordinates(course.getX_end(), course.getY_end());
    }

    /**
     * Methode fournissant le nombre de courses que la voiture a realise.
     *
     * @return un {@link Integer} qui represente le nombre de course realisee par la voiture.
     */
    public int getNombreDeCourse() {
        return nombreDeCourse;
    }

    /**
     * Methode permettant d'imprimer sur la console un recapitulatif des courses realisees par la voiture lors de la "journee".
     */
    public void recapCourses() {
        System.out.print(this.getNombreDeCourse());
        afficherLesCourses();
    }

    private void afficherLesCourses() {
        for (Integer numero : numeroDesCourse
        ) {
            System.out.print(" " + numero);
        }
        System.out.print("\n");
    }

    /**
     * Methode qui permet de savoir si la voiture a fini son service (atteint le max d'etapes possible) ou pas.
     *
     * @return {@code True} si la voiture a atteint le max d'etape autorisee;sinon renvoie {@code False}
     */
    public boolean maxEtapesReached() {
        return etapes >= maxEtapes;
    }


    /**
     * Methode qui permet de recuperer le nombre d'etapes que la voiture a realise a tout moment de la simulation.
     *
     * @return le nombre d'etapes parcourues par la voiture.
     */
    public int getEtapes() {
        return etapes;
    }

}