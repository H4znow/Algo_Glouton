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
    private int etape;
    private int score;

    // Le temps maximale + 1
    int bestEtapes;
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
        bestEtapes = MAX_ETAPES+1;
        bonus = createData.getBonus();
    }

    /**
     * Methode qui lance la simulation en appellant la methode {@code trajets} puis affiche le resultat avec {@code afficherConsoleTrajets}.
     */
    public void lancerSimulation() {
        System.out.println("Hello");
        while (etape < MAX_ETAPES && !courses.isEmpty()) {
            trajets();
            System.out.println("hhhhh");
        }
        afficherConsoleTrajets();
        System.out.println("Score : " + score);
        System.out.println("Etapes : " + etape);
    }

    private void trajets() {
        Course courseSelectionnee;
        for (Voiture voiture :
                voitures) {
            // Changement de strategie importante.
            // Pour chaque voiture, on va realiser la course la plus proche.

            // BestTime reinitialise pour chaque voiture
            bestEtapes = MAX_ETAPES+1;

            courseSelectionnee = Course_bonus(voiture);
            if(courseSelectionnee != null)
                realiserTrajet(voiture, courseSelectionnee, true);

            // S'il n'y a pas de course avec bonus, realiser la course la plus proche
            else if(courseSelectionnee == null){
                courseSelectionnee = Course_distance(voiture);
                // Si aucune course n'est encore realisable. Allons etudier la voiture suivante.
                if(courseSelectionnee == null)
                    continue;
                realiserTrajet(voiture, courseSelectionnee, false);
            }
        }
    }
    private Course Course_bonus(Voiture voiture) {
        Course courseSelectionnee = null;
        for (Course course :
                courses) {
            // Si la course ne peut pas finir, on skip la course
            if (etape + distanceCourseVoiture(course, voiture) + course.distance() > MAX_ETAPES)
                continue;
            // Si on ne peut pas jouer le bonus, on skip la course
            if (etape + distanceCourseVoiture(course, voiture) > course.getEarliest_start())
                continue;
            // Si la distance + le temps d'attente < meilleur distance actuel, on realise cette course
            // Note : "la distance + le temps d'attente" car si on arrive avant le debut de la course, il est malin
            // de voir si attendre le debut de la course vaut le coup ou bien commencer une autre course.
            // voici la formule avant simplification (l.90) :
            // distanceCourseVoiture(course, voiture) + course.getEarliest_start()- (etape + distanceCourseVoiture(course, voiture))
            if(course.getEarliest_start() - etape == 0){
                courseSelectionnee = course;
            } else if (course.getEarliest_start() - etape < bestEtapes) {
                bestEtapes = course.getEarliest_start() - etape;
                courseSelectionnee = course;
            }
        }
        return courseSelectionnee;
    }

    private Course Course_distance(Voiture voiture) {
        Course courseSelectionnee = null;
        for (Course course :
                courses) {
            // Si la course ne peut pas finir, on skip la course
            if (etape + distanceCourseVoiture(course, voiture) + course.distance() > MAX_ETAPES)
                continue;

            if (course.getEarliest_start() - etape < bestEtapes) {
                bestEtapes = course.getEarliest_start() - etape;
                courseSelectionnee = course;
            }
        }
        return courseSelectionnee;
    }

    private void realiserTrajet(Voiture voiture, Course course, boolean bonus_bool) {
        if (bonus_bool){
            score += bonus;
        }
        etape += distanceCourseVoiture(course, voiture) + course.distance();
        score += course.distance();
        voiture.realiserCourse(course);
        courses.remove(course);
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