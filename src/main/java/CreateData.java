import java.util.LinkedList;
import java.util.Scanner;

/**
 * Class permettant de lire les donnees du fichier en entree puis de creer les objets {@link Course} et {@link Voiture}.
 *
 * @author haddo
 */
public class CreateData {

    private final int LENGTH_ARGS = 6;
    private LinkedList<Course> courses;
    private LinkedList<Voiture> voitures;
    private int[] params;

    /**
     * Constructeur de la classe.
     */
    public CreateData() {
        courses = new LinkedList<>();
        voitures = new LinkedList<>();
        params = new int[LENGTH_ARGS];

        Scanner in = new Scanner(System.in);
        this.readInput(in);
        this.creerVoitures(params[2]);
        this.creerLesCourses(in);
    }

    private void readInput(Scanner in) {
        for (int i = 0; i < params.length; i++) {
            params[i] = in.nextInt();
        }
    }

    private void creerVoitures(int nombreDeVoiture) {
        for (int i = 0; i < nombreDeVoiture; i++) {
            voitures.add(new Voiture(i));
        }
    }

    private void creerLesCourses(Scanner in) {
        int nombreDeCoursesARealiser = params[3];
        for (int i = 0; i < nombreDeCoursesARealiser; i++) {
            creerUneCourse(in, i);
        }
    }

    private void creerUneCourse(Scanner in, int numeroCourse) {
        int[] args = new int[LENGTH_ARGS];
        for (int i = 0; i < args.length; i++) {
            args[i] = in.nextInt();
        }
        courses.add(new Course(args, numeroCourse));
    }

    /**
     * Methode qui retourne les courses a realiser.
     *
     * @return une {@link LinkedList} de type {@link Course} representant toute les courses a realiser.
     */
    public LinkedList<Course> getCourses() {
        return courses;
    }

    /**
     * Methode qui retourne les voitures qui realiseront les courses.
     *
     * @return une {@link LinkedList} de type {@link Voiture} representant les voitures qui auront a realiser les courses.
     */
    public LinkedList<Voiture> getVoitures() {
        return voitures;
    }

    /**
     * Methode qui retourne le maximum d'etapes pour terminer la "journee".
     *
     * @return un {@link Integer} representant le maximum d'etapes a ne pas depasser lors de la simulation.
     */
    public int getMaxSteps() {
        return params[5];
    }

    /**
     * Methode qui retourne la valeur d'un bonus. On gagne un bonus lorsque que le chauffeur debute la course {@code x}
     * lorsque {@code steps = Course.earliest_start}.
     *
     * @return un {@link Integer} egale a la valeur des bonus.
     */
    public int getBonus() {
        return params[4];
    }

}