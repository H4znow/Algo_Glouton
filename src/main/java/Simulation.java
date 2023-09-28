import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Class permettant de simuler l'algorithme glouton sur un exemple concret. On a plusieurs courses et plusieurs voitures.
 * Il faut realiser le plus gros score possible en choisissant les meilleurs chemins.
 */
public class Simulation {

    private final CreateData createData;
    private final int bonus;
    private final int maxEtapes;
    private final LinkedList<Course> courses;
    private final LinkedList<Voiture> voitures;
    private int score;
    private boolean finSimulation;


    /**
     * Constructeur de la class.
     */
    public Simulation() {
        score = 0;
        createData = new CreateData();
        maxEtapes = createData.getMaxSteps();
        courses = createData.getCourses();
        voitures = createData.getVoitures();
        bonus = createData.getBonus();
        finSimulation = false;
    }

    /**
     * Methode qui lance la simulation en appellant la methode {@code trajets} puis affiche le resultat avec {@code afficherConsoleTrajets}.
     */
    public void lancerSimulation() {
        while (!finSimulation) {
            trajet();
        }
        afficherConsoleTrajets();
        System.out.println("Score : " + score);
    }


    private void trajet() {
        ArrayList<Voiture> voituresServiceFinis = new ArrayList<>();
        for (Voiture voiture :
                voitures) {
            //Si la voiture n'est plus en service, on ignore la voiture
            if (voituresServiceFinis.contains(voiture))
                continue;

            Course bestCourse = null;
            int etapesNecessaireAtteindreCourse;
            int bestEtapes = maxEtapes + 1;
            int bonus = 0;


            for (Course course :
                    courses) {
                etapesNecessaireAtteindreCourse = voiture.getEtapes() + distanceCourseVoiture(course, voiture);
                // Si la course ne peut pas finir, skip
                if (etapesNecessaireAtteindreCourse + course.distance() > course.getLatest_finish())
                    continue;

                // Si le bonus est jouable et la course est la plus rentable, on la selectionne
                if ((etapesNecessaireAtteindreCourse <= course.getEarliest_start()) && (course.getEarliest_start() < bestEtapes)) {
                    bestEtapes = etapesNecessaireAtteindreCourse;
                    bestCourse = course;
                    bonus = this.bonus;
                }
            }

            // Si aucune course avec bonus n'est realisable, il faut chercher alors seulement la plus proche course
            if (bestCourse == null) {

                for (Course course :
                        courses) {
                    etapesNecessaireAtteindreCourse = voiture.getEtapes() + distanceCourseVoiture(course, voiture);
                    // Si la course ne peut pas finir, skip
                    if (etapesNecessaireAtteindreCourse + course.distance() > course.getLatest_finish())
                        continue;

                    if (etapesNecessaireAtteindreCourse < bestEtapes) {
                        bestEtapes = etapesNecessaireAtteindreCourse;
                        bestCourse = course;
                    }
                }
            }

            // Si a nouveau aucune course n'est realisable, alors la simulation termine.
            if (bestCourse == null)
                finSimulation = true;
            else
                realiserTrajet(voituresServiceFinis, voiture, bestCourse, bonus);
        }
    }


    private void realiserTrajet(ArrayList<Voiture> voituresServicefini, Voiture voiture, Course course, int bonus) {
        score += bonus;
        score += course.distance();
        voiture.realiserCourse(course, distanceCourseVoiture(course, voiture));
        courses.remove(course);
        if (voiture.maxEtapesReached())
            voituresServicefini.add(voiture);
    }

    private void afficherConsoleTrajets() {
        for (Voiture voiture : voitures
        ) {
            voiture.recapCourses();
        }
    }

    private int distanceCourseVoiture(Course course, Voiture voiture) {
        int[] coordinatesVoiture = voiture.getCoordinates();
        return Math.abs(coordinatesVoiture[0] - course.getX_start()) + Math.abs(coordinatesVoiture[1] - course.getY_start());
    }

}