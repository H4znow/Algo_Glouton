import java.util.LinkedList;

/**
 * Class qui represente les voitures dans la simulation. On peut considerer la voiture comme un taxi.
 */
public class Voiture {

    private int[] coordinates;
    private int nombreDeCourse;
    private LinkedList<Integer> numeroDesCourse;
    private int numeroVoiture;

    /**
     * Constructeur qui creer une voiture avec un numero unique (matricule).
     *
     * @param numeroVoiture un {@link Integer} permettant de differencier les differentes voitures.
     */
    public Voiture(int numeroVoiture) {
        coordinates = new int[2];
        coordinates[0] = 0;
        coordinates[1] = 0;
        nombreDeCourse = 0;
        numeroDesCourse = new LinkedList<>();
        this.numeroVoiture = numeroVoiture;
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
     * @param numeroCourse un {@link Integer} qui represente le numero de la course que la voiture vient de realiser.
     */
    public void addNumeroCourse(int numeroCourse) {
        numeroDesCourse.add(numeroCourse);
        nombreDeCourse++;
    }

    /**
     * Methode permettant d'imprimer sur la console un recapitulatif des courses realisees par la voiture lors de la "journee".
     */
    public void recapCourses() {
        System.out.print(nombreDeCourse);
        afficherLesCourses();
    }

    private void afficherLesCourses() {
        for (Integer numero : numeroDesCourse
        ) {
            System.out.print(" " + numero);
        }
        System.out.print("\n");
    }
}