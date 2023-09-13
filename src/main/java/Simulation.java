import java.util.LinkedList;

/**
 * Class permettant de simuler l'algorithme glouton sur un exemple concret. On a plusieurs courses et plusieurs voitures.
 * Il faut realiser le plus gros score possible en choisissant les meilleurs chemins.
 */
public class Simulation {

    private int etape, score, maxEtapes, bonus;
    private CreateData createData;
    private LinkedList<Course> courses;
    private LinkedList<Voiture> voitures;

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
        //System.out.println("Score : " + score);
        //System.out.println("Etapes : " + etape);

    }

    private void afficherConsoleTrajets() {
        for (Voiture voiture : voitures
        ) {
            voiture.recapCourses();
        }
    }

    private void trajet() {
        while (etape < maxEtapes && !courses.isEmpty()) {
            Course[] meilleursCoursesParVoiture = new Course[voitures.size()];
            int[] meilleursScoreParVoiture = new int[voitures.size()];
            int index_MeilleurTrajet = selectionnerCourse(meilleursCoursesParVoiture, meilleursScoreParVoiture);
            if (meilleursScoreParVoiture[index_MeilleurTrajet] < 0) return;
            Course courseTrajet = meilleursCoursesParVoiture[index_MeilleurTrajet];
            Voiture voitureTrajet = voitures.get(index_MeilleurTrajet);
            courses.remove(courseTrajet);
            voitureTrajet.addNumeroCourse(courseTrajet.getNumeroCourse());
            realiserTrajet(voitureTrajet, courseTrajet);
        }
    }

    private void realiserTrajet(Voiture voiture, Course course) {
        etape += distanceVoitureCourse(course, voiture) + course.distance();
        score += course.distance();
        if (course.getEarliest_start() == etape) score += createData.getBonus();
        voiture.setCoordinates(course.getX_end(), course.getY_end());
    }

    private int selectionnerCourse(Course[] meilleursCoursesParVoiture, int[] meilleursScoreParVoiture) {
        int index = 0;
        for (Voiture voiture : voitures) {
            meilleursCoursesParVoiture[index] = (Course) meilleurCourseParVoiture(voiture)[0];
            meilleursScoreParVoiture[index] = Math.round((float) meilleurCourseParVoiture(voiture)[1]);
            index++;
        }
        return meilleurCourse(meilleursScoreParVoiture);
    }

    private int meilleurCourse(int[] meilleursScoreParVoiture) {
        int meilleurCourse = 0;
        for (int i = 1; i < meilleursScoreParVoiture.length; i++) {
            if (meilleursScoreParVoiture[i] > meilleursScoreParVoiture[i - 1]) meilleurCourse = i;
        }
        return meilleurCourse;
    }

    private Object[] meilleurCourseParVoiture(Voiture voiture) {
        Course meilleurCourse = courses.getFirst();
        float meilleurRapportScoreParEtape = rapportScoreParEtape(voiture, meilleurCourse);
        for (Course course : courses) {
            if (rapportScoreParEtape(voiture, course) > meilleurRapportScoreParEtape) {
                meilleurCourse = course;
                meilleurRapportScoreParEtape = rapportScoreParEtape(voiture, course);
            }
        }
        return new Object[]{meilleurCourse, meilleurRapportScoreParEtape};
    }

    private float rapportScoreParEtape(Voiture voiture, Course course) {
        return calculerApportScore(voiture, course) / calculerEtapesNecessaires(voiture, course);
    }

    private int calculerApportScore(Voiture voiture, Course course) {
        int scoreAttendu = 0;
        if (etape + distanceVoitureCourse(course, voiture) == course.getEarliest_start()) scoreAttendu += bonus;
        scoreAttendu += course.distance();
        return scoreAttendu;
    }

    private int calculerEtapesNecessaires(Voiture voiture, Course course) {
        int etapeNecessaire = distanceVoitureCourse(course, voiture) + course.distance();
        if (etapeNecessaire + etape > maxEtapes) return -1;
        return etapeNecessaire;
    }

    private int distanceVoitureCourse(Course course, Voiture voiture) {
        int[] coordinatesVoiture = voiture.getCoordinates();
        return Math.abs(coordinatesVoiture[0] - course.getX_start()) + Math.abs(coordinatesVoiture[1] - course.getY_start());
    }

}