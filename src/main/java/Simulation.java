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
    private Course bestCourse;
    private Voiture bestVoiture;

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
        bonus = createData.getBonus();
    }

    /**
     * Methode qui lance la simulation en appellant la methode {@code trajet} puis affiche le resultat avec {@code afficherConsoleTrajets}.
     */
    public void lancerSimulation() {
        boolean trajet_avecBonus;
        while (etape < MAX_ETAPES && !courses.isEmpty()) {
            trajet_avecBonus = trajet_avecBonus();
            if(realiserTrajet(trajet_avecBonus)==-1)
                break;
        }
        afficherConsoleTrajets();
        System.out.println("Score : " + score);
        System.out.println("Etapes : " + etape);
    }

    private void trajet_Distance(int bestTime) {
        Course courseSelectionnee = null;
        Voiture voitureSelectionnee = null;

        for (Voiture voiture :
                voitures) {
            for (Course course :
                    courses) {
                // Si la course ne peut pas finir, on skip la course
                if (etape + distanceCourseVoiture(course, voiture) + course.distance() > MAX_ETAPES)
                    continue;

                if (distanceCourseVoiture(course, voiture) <= bestTime) {
                    bestTime = distanceCourseVoiture(course, voiture);
                    courseSelectionnee = course;
                    voitureSelectionnee = voiture;
                }
            }
        }
        bestCourse = courseSelectionnee;
        bestVoiture = voitureSelectionnee;
    }

    private boolean trajet_avecBonus() {
        // Le temps maximale + 1
        int bestTime = MAX_ETAPES + 1;
        Course courseSelectionnee = null;
        Voiture voitureSelectionnee = null;
        boolean trajet_avecBonus = false;

        for (Voiture voiture :
                voitures) {
            for (Course course :
                    courses) {
                // Si la course ne peut pas finir, on skip la course
                if (etape + distanceCourseVoiture(course, voiture) + course.distance() > MAX_ETAPES)
                    continue;
                // Si on ne peut pas jouer le bonus, on skip la course
                if (etape + distanceCourseVoiture(course, voiture) > course.getEarliest_start())
                    continue;
                // Si la distance + le temps d'attente < meilleur distance actuel, on realise cette courte
                // Note : "la distance + le temps d'attente" car si on arrive avant le debut de la course, il est malin
                // de voir si attendre le debut de la course vaut le coup ou bien commencer une autre course.
                // voici la formule avant simplification :
                // distanceCourseVoiture(course, voiture) + course.getEarliest_start()- (etape + distanceCourseVoiture(course, voiture))
                if(course.getEarliest_start() - etape == 0){
                    bestCourse = course;
                    bestVoiture = voiture;
                    return true;
                } else if (course.getEarliest_start() - etape < bestTime) {
                    bestTime = course.getEarliest_start() - etape;
                    courseSelectionnee = course;
                    voitureSelectionnee = voiture;
                    trajet_avecBonus = true;
                }
            }
        }
        // Aucun chemin avec Bonus
        if (!trajet_avecBonus) {
            trajet_Distance(bestTime);
            bestCourse = bestCourse;
            bestVoiture = bestVoiture;
        } else {
            bestCourse = courseSelectionnee;
            bestVoiture = voitureSelectionnee;
        }
//        if(bestCourse!=null)
//            System.out.printf("Course %d, Voiture %d, Etape  %d, Distance %d, Bonus %b\n",
//                    this.bestCourse.getNumeroCourse(), this.bestVoiture.getNumeroVoiture(), etape, distanceCourseVoiture(this.bestCourse,bestVoiture)
//                    , etape + distanceCourseVoiture(this.bestCourse, this.bestVoiture) <= this.bestCourse.getEarliest_start());
        return trajet_avecBonus;
    }

    private int realiserTrajet(boolean trajet_avecBonus) {
        if(bestCourse == null || bestVoiture == null)
            return -1;
        if (trajet_avecBonus){
            score += bonus;
        }
        etape += distanceCourseVoiture(bestCourse, bestVoiture) + bestCourse.distance();
        score += bestCourse.distance();
        bestVoiture.setCoordinates(bestCourse.getX_end(), bestCourse.getY_end());
        bestVoiture.addNumeroCourse(bestCourse.getNumeroCourse());
        courses.remove(bestCourse);
        return 0;
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