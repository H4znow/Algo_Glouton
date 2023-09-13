import java.util.LinkedList;

/**
 * Class permettant de simuler l'algorithme glouton sur un exemple concret. On a plusieurs courses et plusieurs voitures.
 * Il faut realiser le plus gros score possible en choisissant les meilleurs chemins.
 */
public class Simulation {

    private final int maxEtapes;
    private final int bonus;
    private final CreateData createData;
    private final LinkedList<Course> courses;
    private final LinkedList<Voiture> voitures;
    private int etape;
    private int score;

    /**
     * Constructeur de la class.
     */
    public Simulation() {
        etape = 0;
        score = 0;
        createData = new CreateData();
        courses = createData.getCourses();
        voitures = createData.getVoitures();
        maxEtapes = createData.getMaxSteps();
        bonus = createData.getBonus();
    }

    /**
     * Methode qui lance la simulation en appellant la methode {@code trajet} puis affiche le resultat avec {@code afficherConsoleTrajets}.
     */
    public void lancerSimulation() {
        trajet();
        afficherConsoleTrajets();
        System.out.println("Score : " + score);
        System.out.println("Etapes : " + etape);
    }

    private void afficherConsoleTrajets() {
        for (Voiture voiture : voitures
        ) {
            voiture.recapCourses();
        }
    }

    private void trajet() {
        Object[] course_et_Voiture;
        Course course;
        Voiture voiture;
        while (etape < maxEtapes && !courses.isEmpty()) {
            if (avantageBonus()) {
                course_et_Voiture = slectionnerMeilleurCourse_Bonus();
            } else {
                course_et_Voiture = slectionnerMeilleurCourse_Distance();
            }
            if (course_et_Voiture[0] == null || course_et_Voiture[1] == null)
                return;
            course = (Course) course_et_Voiture[0];
            voiture = (Voiture) course_et_Voiture[1];
            executerTrajet(course, voiture);
        }
    }

    private void executerTrajet(Course course, Voiture voiture) {
        courses.remove(course);
        voiture.addNumeroCourse(course.getNumeroCourse());
        if (etape + distanceVoitureCourse(course, voiture) == course.getEarliest_start())
            score += bonus;
        score += course.distance();
        etape += distanceVoitureCourse(course, voiture) + course.distance();
        voiture.setCoordinates(course.getX_end(), course.getY_end());
    }

    private boolean avantageBonus() {
        return moyenneDistance() < bonus;
    }

    private int moyenneDistance() {
        int distanceCourses = 0;
        for (Course course :
                courses) {
            distanceCourses += course.distance();
        }
        return distanceCourses / courses.size();
    }

    private Object[] slectionnerMeilleurCourse_Bonus() {
        Course bestCourse = null;
        Voiture bestVoiture = null;

        int best_distance = maxEtapes + 1;

        for (Voiture voiture :
                voitures) {
            for (Course course :
                    courses) {
                if (distanceVoitureCourse(course, voiture) < best_distance
                        && bonusPossible(distanceVoitureCourse(course, voiture), course)) {
                    best_distance = distanceVoitureCourse(course, voiture);
                    bestCourse = course;
                    bestVoiture = voiture;
                }
            }
        }
        return new Object[]{bestCourse, bestVoiture};
    }

    private Object[] slectionnerMeilleurCourse_Distance() {
        Course bestCourse = null;
        Voiture bestVoiture = null;

        int best_distance = maxEtapes + 1;

        for (Voiture voiture :
                voitures) {

            for (Course course :
                    courses) {
                if (distanceVoitureCourse(course, voiture) < best_distance
                        && coursePeutFinir(distanceVoitureCourse(course, voiture), course)) {
                    best_distance = distanceVoitureCourse(course, voiture);
                    bestCourse = course;
                    bestVoiture = voiture;
                }
            }
        }
        return new Object[]{bestCourse, bestVoiture};
    }

    private boolean bonusPossible(int distanceVoitureCourse, Course course) {
        return distanceVoitureCourse + etape + course.distance() == course.getEarliest_start() &&
                distanceVoitureCourse + etape + course.distance() <= course.getLatest_finish();
    }

    private boolean coursePeutFinir(int distanceVoitureCourse, Course course) {
        if (distanceVoitureCourse + etape + course.distance() > maxEtapes ||
                distanceVoitureCourse + etape + course.distance() > course.getLatest_finish()) {
            return false;
        }
        return true;
    }

    private int distanceVoitureCourse(Course course, Voiture voiture) {
        int[] coordinatesVoiture = voiture.getCoordinates();
        return Math.abs(coordinatesVoiture[0] - course.getX_start()) + Math.abs(coordinatesVoiture[1] - course.getY_start());
    }

}