import java.util.LinkedList;

/**
 * Class permettant de simuler l'algorithme glouton sur un exemple concret. On a plusieurs courses et plusieurs voitures.
 * Il faut realiser le plus gros score possible en choisissant les meilleurs chemins.
 */
public class Simulation {

    private final int MAX_ETAPES;
    private final int bonus;
    private final CreateData createData;
    private final LinkedList<Course> courses;
    private final LinkedList<Voiture> voitures;
    // Le temps maximale + 1
    private double bestEtapes;
    private int etape;
    private int score;
//    private Course bestCourse;
//    private Voiture bestVoiture;

    /**
     * Constructeur de la class.
     */
    public Simulation() {
        etape = 0;
        score = 0;
        createData = new CreateData();
        courses = createData.getCourses();
        voitures = createData.getVoitures();
        MAX_ETAPES = createData.getMaxSteps();
        bestEtapes = Double.POSITIVE_INFINITY;
        bonus = createData.getBonus();
    }

    /**
     * Methode qui lance la simulation en appellant la methode {@code trajets} puis affiche le resultat avec {@code afficherConsoleTrajets}.
     */
    public void lancerSimulation() {
        while (!voitures.isEmpty() && !courses.isEmpty()) {
            if (trajets() == 0)
                break;
        }
        afficherConsoleTrajets();
        System.out.println("Score : " + score);
    }

    private int trajets() {
        Course courseSelectionnee = null;
        for (Voiture voiture :
                voitures) {
            // Changement de strategie importante.
            // Pour chaque voiture, on va realiser la course la plus proche.

            // BestTime reinitialise pour chaque voiture
            bestEtapes = Double.POSITIVE_INFINITY;

            courseSelectionnee = choix_Course(voiture);
            if (courseSelectionnee == null)
                return 0;
            realiserTrajet(voiture, courseSelectionnee);
        }
        return 1;
    }

    private Course choix_Course(Voiture voiture) {
        Course courseSelectionnee = null;
        int etapeNecessairePourDebuterCourse;
        for (Course course :
                courses) {
            etapeNecessairePourDebuterCourse = voiture.getEtapes() + distanceCourseVoiture(course, voiture);
            // Si la course ne peut pas finir, on skip la course
            if (etapeNecessairePourDebuterCourse + course.distance() > course.getLatest_finish())
                continue;

            // Si la course ne peut pas commencer, voir si attendre vaut la paine
            if (etapeNecessairePourDebuterCourse < course.getEarliest_start()) {
                if (course.getEarliest_start() < bestEtapes) {
                    bestEtapes = course.getEarliest_start() + course.distance();
                    score += 2;
                    voiture.addEtapes(course.getEarliest_start() - etapeNecessairePourDebuterCourse);
                    courseSelectionnee = course;
                }
            } else if (etapeNecessairePourDebuterCourse + course.distance() < bestEtapes) {
                bestEtapes = etapeNecessairePourDebuterCourse + course.distance();
                courseSelectionnee = course;
            }
        }
        return courseSelectionnee;
    }

    private void realiserTrajet(Voiture voiture, Course course) {
        score += course.distance();
        voiture.realiserCourse(course, distanceCourseVoiture(course, voiture) + course.distance());
        courses.remove(course);
        if(voiture.maxEtapesReached())
            voitures.remove(voiture);
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